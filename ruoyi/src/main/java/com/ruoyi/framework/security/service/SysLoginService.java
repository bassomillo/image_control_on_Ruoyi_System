package com.ruoyi.framework.security.service;

import javax.annotation.Resource;

import com.ruoyi.common.exception.BaseException;
import com.ruoyi.project.tool.MD5Utils;
import com.ruoyi.project.tool.RSAUtil;
import com.ruoyi.project.union.entity.LoginForm;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.security.LoginUser;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserDao userDao;

    /**
     * 基于user表的登录验证
     * @param loginForm
     * @return
     */
    public User newLogin(LoginForm loginForm)
    {
        // 验证码校检
        String verifyKey = Constants.CAPTCHA_CODE_KEY + loginForm.getUuid();
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!loginForm.getCode().equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }

        if (StringUtils.isAnyBlank(loginForm.getUsername(), loginForm.getPassword()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new BaseException("用户不存在/密码错误");
        }

        // 公钥私钥密码校检
        String realPwd = getRealPwd(loginForm.getPublicKey(), loginForm.getPassword());
        User user = userDao.getLoginUser(loginForm.getUsername());
        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new BaseException("用户不存在");
        }
        if (1 == user.getLocked())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.locked")));
            throw new BaseException("用户已禁用");
        }
        String md5Pwd = MD5Utils.calc(realPwd);
        if(!user.getPassword().equals(md5Pwd))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new BaseException("密码错误");
        }

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginForm.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        user.setPassword(realPwd);
        return user;
    }

    public String getRealPwd(String publicKey, String password) {
        String privateKey = redisCache.getCacheObject(publicKey);
        redisCache.deleteObject(publicKey);
        try {
            return RSAUtil.decryptRSADefault(privateKey, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}

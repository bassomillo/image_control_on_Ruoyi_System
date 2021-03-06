package com.ruoyi.project.union.controller;


import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.security.LoginBody;
import com.ruoyi.framework.security.service.SysLoginService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.service.ISysMenuService;
import com.ruoyi.project.tool.CheckUtil;
import com.ruoyi.project.tool.MD5Utils;
import com.ruoyi.project.tool.RSAUtil;
import com.ruoyi.project.union.entity.LoginForm;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.service.IUserProfileService;
import com.ruoyi.project.union.service.IUserService;
import com.ruoyi.project.union.service.LoginTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  ???????????????
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/login")
@Api(tags = "????????????   zjy")
@Slf4j
public class LoginController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "??????????????????", httpMethod = "GET")
    @GetMapping("/publicKey")
    public AjaxResult getPublicKey() throws NoSuchAlgorithmException {
        // ??????RSA??????????????????
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // ?????????????????????
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        log.info(publicKeyStr);
        // ???redis???????????????
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
        log.info(privateKeyStr);
        // ????????????key???????????????value
        redisCache.setCacheObject(publicKeyStr, privateKeyStr, 10, TimeUnit.MINUTES);

        return AjaxResult.success(publicKeyStr);
    }

    @ApiOperation(value = "????????????", httpMethod = "POST")
    @Log(title = "????????????", businessType = BusinessType.OTHER)
    @PostMapping("/loginCheck")
    public AjaxResult loginCheck(@RequestBody LoginForm loginForm) {
        User user = sysLoginService.newLogin(loginForm);
        return AjaxResult.success(loginTokenService.createToken(user));
    }



    @ApiOperation(value = "??????????????????", httpMethod = "GET")
    @Log(title = "????????????", businessType = BusinessType.OTHER)
    @GetMapping("/getUserInfo")
    public AjaxResult getUserInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // ????????????????????????
        User user = loginTokenService.getLoginUser(request);
        if(user == null)
            return AjaxResult.error(401, MessageUtils.message("user.token.expire"));
        User u = userService.getById(user.getId());
        user.setPassword(u.getPassword());
        map.put("user", user);

        // ????????????????????????
        UserProfile userProfile = userProfileService.searchUserProfileById(user.getId());
        map.put("userProfile", userProfile);

        // ????????????????????????
        List<SysMenu> sysMenus = sysMenuService.selectMenuTreeByUserId((long) user.getId());
        map.put("permission", sysMenus);

        return AjaxResult.success(map);
    }

    @ApiOperation(value = "??????", httpMethod = "POST")
    @PostMapping("/logout")
    public AjaxResult logout(HttpServletRequest request)
    {
        loginTokenService.delLoginUser(request);
        return AjaxResult.success(MessageUtils.message("user.logout.success"));
    }

    @RequestMapping(value = "weChat", method=RequestMethod.GET)
    public void weChatLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("success");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(CheckUtil.checkSignature(signature, timestamp, nonce)){
                out.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}


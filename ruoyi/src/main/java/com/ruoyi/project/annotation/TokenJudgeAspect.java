package com.ruoyi.project.annotation;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.tool.Str;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by zhaojy201 on 2020/09/24.
 */
@Slf4j
@Aspect
@Component
public class TokenJudgeAspect {

//    @Autowired
//    private RedisTool redisTool;

    /**
     * 切面 获取所有加有TokenJudge的方法
     */
    @Pointcut("@annotation(com.ruoyi.project.annotation.TokenJudge)")
    public void controllerAspect(){
        log.info("\n");
    }

    /**
     * 环绕通知 环绕截取登陆参数
     * @param joinPoint
     */
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        HttpSession session = request.getSession();

        String token = request.getHeader("Authorization");
//        Map<String, Object> map = TokenTool.parseToken(token);
//        Identity identity = null;
//        if (map.get("code").equals(200)) {
//            identity = (Identity) TokenTool.parseToken(token).get("identity");
//        } else {
//            return AjaxResult.error(Str.ERROR_MSG);
//        }
//
//        String userCodeByToken = identity.getEmpCode();
//        String userCodeByRedis = redisTool.getLoginCode(request);
//
//        if(null == userCodeByRedis) {
//            return AjaxResult.error(Str.NOT_EXIST_MSG);
//        } else if(!userCodeByToken.equals(userCodeByRedis)) {
//            return AjaxResult.error(Str.NOT_EQUAL_MSG);
//        }

        // joinPoint.proceed()应该是让其正常进入controller
        Object returnValue = null;
        String rtv = "";
        // 返回值
        returnValue = joinPoint.proceed();
        if(null != returnValue) {
            rtv = returnValue.toString();
        }
        return returnValue;
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public String getIPAddr(HttpServletRequest request){

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}

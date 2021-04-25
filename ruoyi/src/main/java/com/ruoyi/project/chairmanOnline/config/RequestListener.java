package com.ruoyi.project.chairmanOnline.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @param
 * @Date 2021/4/23
 * @Author weide
 * @description
 **/
@Configuration
@WebListener
public class RequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre)  {

        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {}

    @Override
    public void requestDestroyed(ServletRequestEvent arg0)  {}
}

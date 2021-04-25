package com.ruoyi.project.chairmanOnline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @param
 * @Date 2021/4/13
 * @Author weide
 * @description
 **/
@Configuration
public class WebSocketConfig   {


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }



}

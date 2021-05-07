package com.ruoyi.project.chairmanOnline.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @Date 2021/4/23
 * @Author weide
 * @description
 **/
public class GetHttpSessionConfigurator extends Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        Map<String, List<String>> headers = request.getHeaders();

        HttpSession httpSession=(HttpSession) request.getHttpSession();

        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);

        sec.getUserProperties().put("weiderequest",request);

    }
}
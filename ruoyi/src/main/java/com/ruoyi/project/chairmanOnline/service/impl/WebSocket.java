package com.ruoyi.project.chairmanOnline.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.chairmanOnline.config.GetHttpSessionConfigurator;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.entity.VO.WebSocketSystemMessageVO;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @param
 * @Author weide
 * @description websocket 通讯
 **/

@Component
@ServerEndpoint(value = "/connectWebSocket/{token}/{userId}", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {

    private static SocketChatRecordService socketChatRecordService;

    private static SocketChatConversationService socketChatConversationService;

    @Autowired
    public void setSocketChatRecordService(SocketChatRecordService socketChatRecordService) {
        WebSocket.socketChatRecordService = socketChatRecordService;
    }

    @Autowired
    public void setSocketChatConversationService(SocketChatConversationService socketChatConversationService) {
        WebSocket.socketChatConversationService = socketChatConversationService;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 在线人数
     */
    public static AtomicInteger onlineNumber = new AtomicInteger(0);
    ;
    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static Map<Integer, WebSocket> clients = new ConcurrentHashMap<Integer, WebSocket>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private Integer userId;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId, Session session, EndpointConfig conf,@PathParam("token") String token) {
        HandshakeRequest req = (HandshakeRequest) conf.getUserProperties().get("sessionKey");
        logger.info("header信息：" + conf.getUserProperties().get("weideheaders"));
        logger.info("token:"+token);
        onlineNumber.incrementAndGet();
        logger.info("现在来连接的客户id：" + session.getId() + "用户名：" + userId);
        this.userId = userId;
        this.session = session;
        try {
            clients.put(userId, this);
            logger.info("有连接加入！ 当前在线人数" + clients.size());
            //上线通知
            Set<Integer> set = clients.keySet();
            WebSocketSystemMessageVO webSocketSystemMessageVO = new WebSocketSystemMessageVO();
            webSocketSystemMessageVO.setCurrentOnlineIds(set);
            webSocketSystemMessageVO.setOnlineId(userId);
            sendMessageAll(webSocketSystemMessageVO);
            //查看是否有未接收的数据，有的话全部补推
            List<SocketChatRecord> socketChatRecords = socketChatRecordService.queryUnsentRecord(userId);
            if (socketChatRecords.size() > 0) {
                for (SocketChatRecord record : socketChatRecords) {
                    this.sendMessageTo(record);
                }
            }
        } catch (IOException e) {
            logger.info(userId + "上线的时候通知所有人发生了错误");
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
        error.printStackTrace();
    }


    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        Set<Integer> set = clients.keySet();
        WebSocketSystemMessageVO webSocketSystemMessageVO = new WebSocketSystemMessageVO();
        webSocketSystemMessageVO.setCurrentOnlineIds(set);
        webSocketSystemMessageVO.setOfflineId(userId);
        try {
            sendMessageAll(webSocketSystemMessageVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(userId + "下线");
        logger.info("有连接关闭！ 当前在线人数" + clients.size());
    }

    /**
     * 收到客户端的消息
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            System.out.println("--------收到消息--------------  :" + message);
            SocketChatRecord socketChatRecord = JSON.parseObject(message, SocketChatRecord.class);
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId(),"token是："+socketChatRecord.getToken());
            System.out.println("开始推送消息给" + socketChatRecord.getReceiverid());
            socketChatRecord.setCreatedtime(new Date());
            socketChatRecord.setToken("");
            sendMessageTo(socketChatRecord);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("发生了错误了");
        }
    }


    private void sendMessageTo(SocketChatRecord socketChatRecord) throws IOException {
        //创建对话，对话发生时记录信息,对话如果已存在则更新
        int conversationId = socketChatConversationService.createConversation(socketChatRecord);
        //更新对话中的一些统计数值
        socketChatConversationService.conversationStatistics(conversationId);
        //每条聊天信息都记录到数据库。补推时聊天记录已存在则为更新。写入数据库,可能有并发问题
        if (null == socketChatRecord.getId()) {
            logger.info("将首次收到的聊天记录写入数据库");
            socketChatRecord.setConversationid(conversationId);
            socketChatRecordService.insertOrUpdateRecord(socketChatRecord);
        }
        for (WebSocket item : clients.values()) {
            if (item.userId.equals(socketChatRecord.getReceiverid())) {
                item.session.getAsyncRemote().sendText(JSON.toJSON(socketChatRecord).toString());
                //成功发送到对方客户端，更改
                socketChatRecord.setIssent(1);
                socketChatRecord.setCreatedtime(new Date());
                socketChatRecordService.update(socketChatRecord);
                break;
            }
        }
    }


    private void sendMessageAll(WebSocketSystemMessageVO webSocketSystemMessageVO) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(JSON.toJSON(webSocketSystemMessageVO).toString());
        }
    }

    public static int getOnlineCount() {
        return onlineNumber.get();
    }

}

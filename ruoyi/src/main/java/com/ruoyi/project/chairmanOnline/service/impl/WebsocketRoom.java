package com.ruoyi.project.chairmanOnline.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatroomRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 群聊
 *
 * @param
 * @Date 2021/5/6
 * @Author weide
 * @description
 **/

@Component
@ServerEndpoint(value = "/connectWebSocketRoom/{token}/{userId}/{tagId}")
public class WebsocketRoom {

    private static SocketChatroomRecordService socketChatroomRecordService;

    @Autowired
    public void setSocketChatroomRecordService(SocketChatroomRecordService socketChatroomRecordService) {
        WebsocketRoom.socketChatroomRecordService = socketChatroomRecordService;
    }

    /**
     * 群聊
     */
    private static Map<Integer, List<WebsocketRoom>> roomClients = new ConcurrentHashMap<Integer, List<WebsocketRoom>>(){{

        put(1,new CopyOnWriteArrayList<>());
        put(2,new CopyOnWriteArrayList<>());
        put(3,new CopyOnWriteArrayList<>());
        put(4,new CopyOnWriteArrayList<>());
        put(5,new CopyOnWriteArrayList<>());

    }};

    private Session session = null;

    private Integer userId =null;

    /**
     * 连接成功
     * @param session 会话信息
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Integer userId,@PathParam("tagId") Integer tagId) {
        this.session =session;
        this.userId = userId;
        roomClients.get(tagId).add(this);
        System.out.println("用户:"+userId+" 加入群聊"+ tagId + " 此群聊当前人数："+ roomClients.get(tagId).size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(@PathParam("tagId") Integer tagId) {
        roomClients.get(tagId).remove(this);
        System.out.println("用户:"+userId+" 退出群聊"+ tagId +" 此群聊当前人数："+ roomClients.get(tagId).size());
    }

    /**
     * 连接错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("发生错误！");
        error.printStackTrace();
    }

    /**
     * 发送消息,不加注解，自己选择实现
     * @param msg
     * @throws IOException
     */
    public void onSend(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    /**
     * 收到客户端消息回调方法
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        SocketChatroomRecord socketChatroomRecord = JSON.parseObject(message, SocketChatroomRecord.class);
        //token验证
        //群聊信息
        socketChatroomRecord.setCreatedtime(new Date());
        socketChatroomRecord.setToken("");
        socketChatroomRecordService.insert(socketChatroomRecord);
        SocketChatroomRecord record = socketChatroomRecordService.getUserInfo(socketChatroomRecord);
        List<WebsocketRoom> arraySet = roomClients.get(record.getTagid());
        for (WebsocketRoom webScoketServer : arraySet) {
            try {
                webScoketServer.onSend(JSON.toJSON(record).toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
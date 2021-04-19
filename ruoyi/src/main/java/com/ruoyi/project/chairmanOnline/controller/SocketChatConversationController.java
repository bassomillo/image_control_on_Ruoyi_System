package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.project.chairmanOnline.dao.SocketChatConversationDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天会话表(SocketChatConversation)表控制层
 *
 * @author weide
 * @since 2021-04-15 15:50:12
 */
@Api(tags = "web端-民主管理-总经理在线-hwd")
@RestController
@RequestMapping("socketChatConversation")
public class SocketChatConversationController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatConversationService socketChatConversationService;



    @ApiOperation("获取当前用户的所有对话")
    @GetMapping("selectConversation")
    public List<SocketChatConversation> queryConversation(Integer userId) {
        return this.socketChatConversationService.queryConversation(userId);
    }


    @ApiOperation("删除对话")
    @GetMapping("deleteConversation")
    public Boolean deleteConversation(Integer conversationid) {
        return this.socketChatConversationService.deleteById(conversationid);
    }

}

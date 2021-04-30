package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatConversationQO;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


    @ApiOperation("获取当前用户的所有对话,按时间降序")
    @PostMapping("selectConversation")
    public AjaxResult queryConversation(
            @RequestParam int userId,
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestBody(required = false) SocketChatConversationQO socketChatConversationQO) {
        return this.socketChatConversationService.queryConversation(userId, pageNum, pageSize, socketChatConversationQO);
    }


    @ApiOperation("删除对话")
    @PostMapping("deleteConversation")
    public AjaxResult deleteConversation(Integer conversationid) {
        return AjaxResult.success(this.socketChatConversationService.deleteById(conversationid));
    }

}

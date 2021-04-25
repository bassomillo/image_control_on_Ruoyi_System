package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
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


    @ApiOperation("获取当前用户的所有对话,按时间降序")
    @PostMapping("selectConversation")
    public AjaxResult queryConversation(
            Integer userId,
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return AjaxResult.success(this.socketChatConversationService.queryConversation(userId,pageNum,pageSize));

    }


    @ApiOperation("删除对话")
    @PostMapping("deleteConversation")
    public AjaxResult deleteConversation(Integer conversationid) {
        return AjaxResult.success(this.socketChatConversationService.deleteById(conversationid));
    }

}

package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatRecordQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天记录(SocketChatRecord)表控制层
 *
 * @author weide
 * @since 2021-04-15 15:50:32
 */
@Api(tags = "web端-民主管理-总经理在线-hwd")
@RestController
@RequestMapping("socketChatRecord")
public class SocketChatRecordController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatRecordService socketChatRecordService;



    @ApiOperation("根据会话id查询对话的聊天记录，总经理、咨询师可用")
    @PostMapping("selectChatRecords")
    public AjaxResult selectChatRecords(Integer conversionId,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        SocketChatRecord socketChatRecordQM = new SocketChatRecord();
        socketChatRecordQM.setConversationid(conversionId);
        return AjaxResult.success(this.socketChatRecordService.queryAll(socketChatRecordQM,pageNum,pageSize));
    }

    @ApiOperation("用户查询对话聊天记录")
    @PostMapping("selectChatRecordsWithChairmanId")
    public AjaxResult selectChatRecordsWithChairmanId(Integer userId, int chairmanIdOrcounselorId,
                                                      @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        int chairmanId = 111111;
        return AjaxResult.success(this.socketChatRecordService.queryChatRecord(userId, chairmanIdOrcounselorId,pageNum,pageSize));
    }


    @ApiOperation("聊天记录搜索")
    @PostMapping("selectChatRecordsByCondition")
    public AjaxResult selectChatRecordsByCondition(@RequestBody SocketChatRecordQO socketChatRecordQO,
                                                   @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return AjaxResult.success(this.socketChatRecordService.selectChatRecordsByCondition(socketChatRecordQO,pageNum,pageSize));
    }

}

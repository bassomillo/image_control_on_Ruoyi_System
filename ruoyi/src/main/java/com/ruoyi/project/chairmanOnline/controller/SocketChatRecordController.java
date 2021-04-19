package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
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

    @Resource
    SocketChatRecordDao socketChatRecordDao;



    @ApiOperation("查询对话的聊天记录")
    @GetMapping("selectChatRecords")
    public List<SocketChatRecord> selectChatRecords(Integer conversionId) {
        SocketChatRecord socketChatRecordQM = new SocketChatRecord();
        socketChatRecordQM.setConversationid(conversionId);
        return this.socketChatRecordDao.queryAll(socketChatRecordQM);
    }

}

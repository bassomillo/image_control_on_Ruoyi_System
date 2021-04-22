package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatRecordQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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


    @ApiOperation("总经理、咨询师查询聊天记录,按时间降序")
    @PostMapping("selectChatRecords")
    public AjaxResult selectChatRecords(Integer conversionId,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        SocketChatRecord socketChatRecordQM = new SocketChatRecord();
        socketChatRecordQM.setConversationid(conversionId);
        return AjaxResult.success(this.socketChatRecordService.queryAll(socketChatRecordQM, pageNum, pageSize));
    }

    @ApiOperation("普通用户查询聊天记录,按时间降序")
    @PostMapping("selectChatRecordsWithChairmanId")
    public AjaxResult selectChatRecordsWithChairmanId(@RequestParam Integer userId, @RequestParam(name = "总经理或者咨询师id") int chairmanIdOrcounselorId,
                                                      @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        int chairmanId = 111111;
        return AjaxResult.success(this.socketChatRecordService.queryChatRecord(userId, chairmanIdOrcounselorId, pageNum, pageSize));
    }


    @ApiOperation("聊天记录搜索,按时间降序")
    @PostMapping("selectChatRecordsByCondition")
    public AjaxResult selectChatRecordsByCondition(@RequestBody SocketChatRecordQO socketChatRecordQO,
                                                   @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return AjaxResult.success(this.socketChatRecordService.selectChatRecordsByCondition(socketChatRecordQO, pageNum, pageSize));
    }

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public AjaxResult upload(MultipartFile file) throws IOException {
        return AjaxResult.success(FileUploadUtils.upload(file));
    }

}

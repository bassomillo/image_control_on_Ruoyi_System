package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.dao.SocketChatroomRecordDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatroomRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天记录(SocketChatroomRecord)表控制层
 *
 * @author weide
 * @since 2021-05-06 15:53:02
 */
@RestController
@RequestMapping("socketChatroomRecord")
@Api(tags = "web端-民主管理-总经理在线-hwd")
public class SocketChatroomRecordController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatroomRecordService socketChatroomRecordService;

    @Resource
    private SocketChatroomRecordDao socketChatroomRecordDao;
    /**
     * 全部查询
     *
     * @param
     * @return
     */
    @ApiOperation("群聊记录")
    @PostMapping("queryRecordBytagId")
    public AjaxResult queryRecordBytagId(Integer tagId) {
        return AjaxResult.success(this.socketChatroomRecordService.queryRecordBytagId(tagId));
    }


}

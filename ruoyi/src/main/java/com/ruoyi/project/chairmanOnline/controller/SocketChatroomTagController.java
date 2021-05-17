package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.dao.SocketChatroomTagDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomTag;
import com.ruoyi.project.chairmanOnline.service.SocketChatroomTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天室标签表(SocketChatroomTag)表控制层
 *
 * @author weide
 * @since 2021-05-06 16:09:24
 */
@RestController
@RequestMapping("socketChatroomTag")
@Api(tags = "web端-民主管理-总经理在线-hwd")
public class SocketChatroomTagController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatroomTagService socketChatroomTagService;

    @Resource
    SocketChatroomTagDao socketChatroomTagDao;
    /**
     * 通过主键查询单条数据
     *
     * @param
     * @return 单条数据
     */
    @ApiOperation("群组列表")
    @PostMapping("selectAll")
    public AjaxResult selectAll() {
        return AjaxResult.success(this.socketChatroomTagDao.selectList(null));
    }

}

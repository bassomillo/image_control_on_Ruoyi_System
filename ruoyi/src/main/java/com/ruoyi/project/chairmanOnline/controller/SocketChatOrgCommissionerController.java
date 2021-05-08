package com.ruoyi.project.chairmanOnline.controller;


import com.ruoyi.project.chairmanOnline.entity.SocketChatOrgCommissioner;
import com.ruoyi.project.chairmanOnline.service.SocketChatOrgComService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (OrgCommissioner)表控制层
 *
 * @author weide
 * @since 2021-05-07 15:44:17
 */
@RestController
@RequestMapping("orgCommissioner")
@Api(tags = "web端-民主管理-总经理在线-hwd")
public class SocketChatOrgCommissionerController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatOrgComService socketChatOrgComService;




    @ApiOperation("a")
    @PostMapping("a")
    public Integer getCommissionerByUserId(int userId) {



        return socketChatOrgComService.getCommissionerByUserId(userId);
    }


}

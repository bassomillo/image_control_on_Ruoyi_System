package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.service.SocketUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author weide
 * @since 2021-04-29 11:22:39
 */
@RestController
@RequestMapping("socketuser")
@Api(tags = "web端-民主管理-总经理在线-hwd")
public class SocketUserController {
    /**
     * 服务对象
     */
    @Resource
    private SocketUserService userService;

    /**
     * 心理咨询师列表
     *
     * @param
     * @return 单条数据
     */
    @ApiOperation("心理咨询师列表")
    @PostMapping("selectPsychologicalCounselors")
    public AjaxResult selectPsychologicalCounselors() {
        return AjaxResult.success(this.userService.selectPsychologicalCounselors());
    }

}

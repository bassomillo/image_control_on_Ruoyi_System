package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.VO.GeneralManagerVO;
import com.ruoyi.project.chairmanOnline.service.SocketUserService;
import com.ruoyi.project.democratic.tool.ToolUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author weide
 * @since 2021-04-29 11:22:39
 */
@RestController
@RequestMapping("socketuser")
@Api(tags = "web端-心理咨询-hwd")
public class SocketUserController {
    /**
     * 服务对象
     */
    @Resource
    private SocketUserService userService;

    @Resource
    private ToolUtils toolUtils;

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

    @ApiOperation("通过用户的组织orgid，查询省总经理和市总经理")
    @PostMapping("selectGeneralManager")
    public AjaxResult selectGeneralManager1(int orgId) {
        return AjaxResult.success(toolUtils.getManagerId(orgId)) ;
    }
}

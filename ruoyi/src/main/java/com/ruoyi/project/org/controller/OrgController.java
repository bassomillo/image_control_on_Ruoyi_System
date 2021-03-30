package com.ruoyi.project.org.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.org.service.impl.OrgServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/org")
@Api(tags = "工会管理 - 机构管理   zjy")
@Slf4j
public class OrgController {

    @Autowired
    private IOrgService orgService;

    @ApiOperation(value = "获取组织机构树", httpMethod = "GET")
    @GetMapping("/searchOrgTree")
    public AjaxResult searchOrgTree() {
        return AjaxResult.success(orgService.searchOrgTree());
    }
}


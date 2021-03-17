package com.ruoyi.project.auth.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.entity.Role;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.auth.pojo.RoleSearchPojo;
import com.ruoyi.project.auth.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/role")
@Api(tags = "系统管理 - 角色管理")
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "角色管理页面数据显示 + 搜索", httpMethod = "POST")
    @ApiImplicitParam(name = "RoleSearchPojo", value = "查询条件实体", paramType = "body", dataType = "String")
    @PostMapping("/searchRole")
    public AjaxResult searchRole(@RequestBody RoleSearchPojo roleSearchPojo) {
        return roleService.searchRole(roleSearchPojo);
    }

    @ApiOperation(value = "新建角色", httpMethod = "POST")
    @ApiImplicitParam(name = "Role", value = "查询条件实体", paramType = "body", dataType = "String")
    @PostMapping("/createRole")
    public AjaxResult createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
}


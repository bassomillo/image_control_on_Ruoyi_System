package com.ruoyi.project.auth.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.entity.Role;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.auth.pojo.RoleSearchPojo;
import com.ruoyi.project.auth.service.IRoleService;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "系统管理 - 角色权限相关   zjy")
@Slf4j
public class AuthController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "根据用户id获取菜单列表（userId=0时查询所有菜单列表）", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "body", dataType = "Long")
    @GetMapping("/searchMenu")
    public AjaxResult searchMenu(@RequestParam("userId") Long userId) {
        return AjaxResult.success(sysMenuService.selectMenuTreeByUserId(userId));
    }

    /*****************************************************角色*********************************************************/
    @ApiOperation(value = "角色管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchRole")
    public AjaxResult searchRole(@RequestBody RoleSearchPojo roleSearchPojo) {
        return roleService.searchRole(roleSearchPojo);
    }

    @ApiOperation(value = "新建角色", httpMethod = "POST")
    @PostMapping("/createRole")
    public AjaxResult createRole(@RequestBody RoleCreatePojo roleCreatePojo) {
        return roleService.createRole(roleCreatePojo);
    }

    @ApiOperation(value = "删除角色", httpMethod = "POST")
    @ApiImplicitParam(name = "RoleId", value = "角色id", paramType = "body", dataType = "Integer")
    @PostMapping("/delRole")
    public AjaxResult delRole(@RequestParam("roleId") Long roleId) {
        return roleService.del(roleId);
    }

    @ApiOperation(value = "更新角色", httpMethod = "POST")
    @PostMapping("/updateRole")
    public AjaxResult updateRole(@RequestBody SysRole sysRoles) {
        return roleService.updateRole(sysRoles);
    }

    @ApiOperation(value = "查询角色详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "RoleId", value = "角色id", paramType = "body", dataType = "Integer")
    @GetMapping("/searchRoleById")
    public AjaxResult searchRoleById(@RequestParam("roleId") Long roleId) {
        return roleService.searchRoleById(roleId);
    }
}


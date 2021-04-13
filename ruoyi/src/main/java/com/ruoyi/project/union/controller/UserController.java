package com.ruoyi.project.union.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.pojo.DisableUserPojo;
import com.ruoyi.project.union.pojo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/user")
@Api(tags = "会员管理 - 账号user   zjy")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "会员管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchUser")
    public AjaxResult searchUser(@RequestBody UserSearchPojo userSearchPojo) {
        return userService.searchUser(userSearchPojo);
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @PostMapping("/createUser")
    public AjaxResult createUser(@RequestBody UserProfile userProfile) {
        userService.createUser(userProfile);
        return AjaxResult.success();
    }

    @ApiOperation(value = "禁用账号，密码校检等待登录模块完成", httpMethod = "POST")
    @ApiImplicitParam(name = "disableUser", value = "角色id", paramType = "body", dataType = "Integer")
    @PostMapping("/disableUser")
    public AjaxResult disableUser(@RequestBody DisableUserPojo disableUserPojo) {
        return userService.disableUser(disableUserPojo);
    }

    @ApiOperation(value = "编辑用户", httpMethod = "POST")
    @PostMapping("/updateUser")
    public AjaxResult updateUser(@RequestBody UserProfile userProfile) {
        userService.updateUser(userProfile);
        return AjaxResult.success();
    }

    @ApiOperation(value = "查询用户详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "body", dataType = "Integer")
    @GetMapping("/searchUserById")
    public AjaxResult searchUserById(@RequestParam("userId") Integer userId) {
        return userService.searchUserById(userId);
    }

    @ApiOperation(value = "设置用户头像，等文件上传确认", httpMethod = "POST")
    @PostMapping("/setAvatar")
    public AjaxResult setAvatar(@RequestBody UserProfile userProfile) {
        return AjaxResult.success();
    }

    @ApiOperation(value = "重置用户密码，等加密方式确认", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "managePassword", value = "管理员密码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "重置的新密码", paramType = "query", dataType = "String")
    })
    @PostMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestParam("managePassword") String managePassword, @RequestParam("newPassword") String newPassword) {
        return AjaxResult.success();
    }

    /**************************************************信息审核相关接口**************************************************/

}


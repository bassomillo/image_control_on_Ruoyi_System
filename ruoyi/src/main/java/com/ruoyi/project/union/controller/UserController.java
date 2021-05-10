package com.ruoyi.project.union.controller;


import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.vo.DisableUserVo;
import com.ruoyi.project.union.entity.vo.ResetPasswordVo;
import com.ruoyi.project.union.entity.vo.UserRoleVo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@Api(tags = "会员管理/账号管理  zjy")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /*************************************************账号管理**********************************************************/
    @ApiOperation(value = "账号管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchAccount")
    public AjaxResult searchAccount(@RequestBody AccountSearchVo accountSearchVo) {
        return userService.searchAccount(accountSearchVo);
    }

    @ApiOperation(value = "更新用户 - 角色", httpMethod = "POST")
    @Log(title = "更新用户角色", businessType = BusinessType.UPDATE)
    @PostMapping("/updateUserRole")
    public AjaxResult updateUserRole(@RequestBody UserRoleVo userRoleVo) {
        userService.updateUserRole(userRoleVo);
        return AjaxResult.success();
    }
    /*************************************************会员管理**********************************************************/
    @ApiOperation(value = "会员管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchUser")
    public AjaxResult searchUser(@RequestBody UserSearchPojo userSearchPojo) {
        return userService.searchUser(userSearchPojo);
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
    @PostMapping("/createUser")
    public AjaxResult createUser(@RequestBody UserProfile userProfile) {
        if(userService.isExistMobile(userProfile.getMobile()))
            return AjaxResult.error("新增用户'" + userProfile.getTruename() + "'失败，手机号码已存在");
        if(userService.isExistEmail(userProfile.getEmail()))
            return AjaxResult.error("新增用户'" + userProfile.getTruename() + "'失败，邮箱已存在");
        userService.createUser(userProfile);
        return AjaxResult.success();
    }

    @ApiOperation(value = "禁用账号，管理员密码需经过公钥加密，另外需要定时器解禁还没写", httpMethod = "POST")
    @Log(title = "禁用账号", businessType = BusinessType.OTHER)
    @PostMapping("/disableUser")
    public AjaxResult disableUser(@RequestBody DisableUserVo disableUserVo, HttpServletRequest request) {
        return userService.disableUser(disableUserVo, request);
    }

    @ApiOperation(value = "编辑用户（个人设置-个人信息）", httpMethod = "POST")
    @Log(title = "编辑用户", businessType = BusinessType.UPDATE)
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
    @Log(title = "设置头像", businessType = BusinessType.UPDATE)
    @PostMapping("/setAvatar")
    public AjaxResult setAvatar(@RequestBody UserProfile userProfile) {
        return AjaxResult.success();
    }

    @ApiOperation(value = "重置用户密码，管理员密码需经过公钥加密（个人设置-安全设置）", httpMethod = "POST")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestBody ResetPasswordVo resetPasswordVo, HttpServletRequest request) {
        return userService.resetPassword(resetPasswordVo, request);
    }

    /**************************************************信息审核相关接口**************************************************/

}


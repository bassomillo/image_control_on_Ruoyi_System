package com.ruoyi.project.union.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.pojo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    @ApiImplicitParam(name = "userSearchPojo", value = "查询条件实体", paramType = "body", dataType = "String")
    @PostMapping("/searchUser")
    public AjaxResult searchUser(@RequestBody UserSearchPojo userSearchPojo) {
        return userService.searchUser(userSearchPojo);
    }
}


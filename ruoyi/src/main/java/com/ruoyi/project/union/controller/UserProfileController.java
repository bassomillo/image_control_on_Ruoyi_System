package com.ruoyi.project.union.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.service.IUserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Controller
@RequestMapping("/userProfile")
@Api(tags = "会员管理 - 用户userProfile  zjy")
@Slf4j
public class UserProfileController {

    @Autowired
    private IUserProfileService userProfileService;

    @ApiOperation(value = "查询指定用户详细信息", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", dataType = "Integer")
    @PostMapping("/searchUserProfileById")
    public AjaxResult searchUserProfileById(@RequestParam("id") Integer id) {
        UserProfile userProfile = userProfileService.searchUserProfileById(id);
        return AjaxResult.success();
    }
}


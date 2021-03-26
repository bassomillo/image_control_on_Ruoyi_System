package com.ruoyi.project.union.controller;


import com.ruoyi.framework.security.LoginBody;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.service.ISysMenuService;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.pojo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserProfileService;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录控制   zjy")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "登录验证", httpMethod = "GET")
    @ApiImplicitParam(name = "LoginBody", value = "登录信息实体", paramType = "body", dataType = "String")
    @GetMapping("/loginCheck")
    public AjaxResult loginCheck(@RequestBody LoginBody loginBody) {
        Map<String, Object> map = new HashMap<>();
        // 获取对应账号信息
        User user = userService.searchUserByAccount(loginBody.getUsername());
        if(user == null)
            return AjaxResult.error("用户名或密码错误！");
        map.put("user", user);

        // 获取对应用户信息
        UserProfile userProfile = userProfileService.searchUserProfileById(user.getId());
        map.put("userProfile", userProfile);

        // 获取对应账号权限
        List<SysMenu> sysMenus = sysMenuService.selectMenuTreeByUserId((long) user.getId());
        map.put("permission", sysMenus);

        return AjaxResult.success(map);
    }
}


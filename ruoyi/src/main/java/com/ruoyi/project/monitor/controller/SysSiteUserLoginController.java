package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteEmailSetting;
import com.ruoyi.project.monitor.domain.SysSiteUserLogin;
import com.ruoyi.project.monitor.service.SysSiteEmailSettingService;
import com.ruoyi.project.monitor.service.SysSiteUserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/site/user/login")

public class SysSiteUserLoginController {
    @Autowired
    private SysSiteUserLoginService sysSiteUserLoginService;
    @ApiOperation(value = "系统管理-用户设置-登录设置")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody SysSiteUserLogin sysSiteUserLogin)
    {


        //写insert逻辑
        AjaxResult result = sysSiteUserLoginService.SysSiteUserLogin(sysSiteUserLogin);
        //返回插入成功数据
        return result;
    }
    /**
     * 回显页面操作
     */
    @ApiOperation(value = "系统管理-用户设置-登录设置-回显")
    @PostMapping("/getSite")
    public AjaxResult getSiteSetting(){
        AjaxResult result = sysSiteUserLoginService.SysSiteUserLoginGet();
        return result;

    }
}

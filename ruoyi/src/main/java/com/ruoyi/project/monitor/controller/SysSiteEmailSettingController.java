package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteEmailSetting;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.service.SysSiteEmailSettingService;
import com.ruoyi.project.monitor.service.SysSiteSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/site/email")

public class SysSiteEmailSettingController {
    @Autowired
    private SysSiteEmailSettingService sysSiteEmailSettingService;
    @ApiOperation(value = "系统管理-站点设置-邮件服务设置")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody SysSiteEmailSetting sysSiteEmailSetting)
    {


        //写insert逻辑
        AjaxResult result = sysSiteEmailSettingService.SysSiteEmailSetting(sysSiteEmailSetting);
        //返回插入成功数据
        return result;
    }

}

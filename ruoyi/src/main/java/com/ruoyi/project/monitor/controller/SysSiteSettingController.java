package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
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
@RequestMapping("/admin/setting/site")
public class SysSiteSettingController {
    @Autowired
    private SysSiteSettingService sysSiteSettingService;

    @Autowired
    private SysLogoUploadController fastdfsController;

    @ApiOperation(value = "新增网页基础数据")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody SysSiteSetting sysSiteSetting)
    {


        //写insert逻辑
        AjaxResult result = sysSiteSettingService.SysSiteSetting(sysSiteSetting);
        //返回插入成功数据
        return result;
    }
}


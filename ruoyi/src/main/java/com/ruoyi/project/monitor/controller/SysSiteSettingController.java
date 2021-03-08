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
/**
 * 系统管理站点设置操作
 *
 * @author ruoyi
 */


@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/site")
public class SysSiteSettingController {
    @Autowired
    private SysSiteSettingService sysSiteSettingService;

//    @Autowired
//    private SysLogoUploadController fastdfsController;
    /**
     * 输入基础信息
     */
    @ApiOperation(value = "系统管理-站点设置-基础信息")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody SysSiteSetting sysSiteSetting)
    {


        //写insert逻辑
        AjaxResult result = sysSiteSettingService.SysSiteSetting(sysSiteSetting);
        //返回插入成功数据
        return result;
    }
    /**
     * 回显页面操作
     */
    @ApiOperation(value = "系统管理-站点设置-基础信息-回显")
    @PostMapping("/getSite")
    public AjaxResult getSiteSetting(){
        AjaxResult result = sysSiteSettingService.SysSiteGetting();
        return result;

    }
}


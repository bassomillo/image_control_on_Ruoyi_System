package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysAdvertisement;
import com.ruoyi.project.monitor.service.SysAdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "广告管理")
@RestController
@RequestMapping(value = "/websitemanagement/")
public class SysAdvertisementController {
    @Autowired
    SysAdvertisementService sysAdvertisementService;

    @ApiOperation(value = "网站管理-广告管理-展示")
    @PostMapping(value = "getadvertisement")
    public AjaxResult Get(@RequestBody Integer advid){
        AjaxResult result = sysAdvertisementService.AdvertisementGetting(advid);
        return result;
    }

    @ApiOperation(value = "网站管理-广告管理-保存设置")
    @PostMapping(value = "updateadvertisement")
    public AjaxResult Update(@RequestBody SysAdvertisement sysAdvertisement){
        AjaxResult result = sysAdvertisementService.AdvertisementUpdate(sysAdvertisement);
        return result;
    }
}

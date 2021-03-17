package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysCarouselMap;
import com.ruoyi.project.monitor.service.SysCarouselMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "首页轮播图")
@RestController
@RequestMapping(value = "/websitemanagement/")
public class SysCarouselMapController {

    @Autowired
    private SysCarouselMapService sysCarouselMapService;

    @ApiOperation(value = "网站管理-首页轮播图管理-保存设置")
    @PostMapping(value = "updateCarouselMap")
    public AjaxResult Update(@RequestBody SysCarouselMap sysCarouselMap){
        AjaxResult result = sysCarouselMapService.CarouselMapUpdate(sysCarouselMap);
        return result;

    }

    @ApiOperation(value = "网站管理-首页轮播图管理-展示")
    @PostMapping(value = "getCarouselMap")
    public AjaxResult Get(@RequestBody Integer billid){
        AjaxResult result = sysCarouselMapService.CarouselMapGetting(billid);
        return result;

    }

}

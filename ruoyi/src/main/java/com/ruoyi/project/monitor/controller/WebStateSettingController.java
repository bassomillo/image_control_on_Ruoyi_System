package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.service.WebLinkSettingService;
import com.ruoyi.project.monitor.service.WebStateSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/webState")
public class WebStateSettingController {
    @Autowired
    private WebStateSettingService webStateSettingService;

    /**
     * 输入公告信息
     */
    @ApiOperation(value = "网站管理-站点公告管理-新增公告")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody WebStateSetting webStateSetting)
    {

        AjaxResult result = webStateSettingService.WebStateSetting(webStateSetting);
        //返回插入成功数据
        return result;

    }
    /**
     * 回显页面操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-回显")
    @PostMapping("/getSite")
    public AjaxResult getSiteSetting(){
        AjaxResult result = webStateSettingService.WebStateGetting();
        return result;

    }

    /**
     * 搜索操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-搜索")
    @PostMapping("/searchSite")
    public AjaxResult searchSiteSetting(@RequestBody WebStateSearch webStateSearch){
        AjaxResult result = webStateSettingService.WebStateSearch(webStateSearch);
        return result;

    }

    /**
     * 编辑公告操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-编辑公告")
    @PostMapping("/updateSite")
    public AjaxResult updateSiteSetting(@RequestBody WebStateSetting webStateSetting){
        AjaxResult result = webStateSettingService.WebStateUpdate(webStateSetting);
        return result;
    }

    /**
     * 全选删除操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-全选删除")
    @PostMapping("/deleteAllSite")
    public AjaxResult deleteAllSiteSetting(){
        AjaxResult result = webStateSettingService.WebStateDeleteAll();
        return result;
    }

    /**
     * 单个公告删除操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-单个公告删除")
    @PostMapping("/deleteSite")
    public AjaxResult deleteSiteSetting(int id){
        AjaxResult result = webStateSettingService.WebStateDelete(id);
        return result;
    }

    /**
     * 选择多个公告删除操作
     */
    @ApiOperation(value = "网站管理-站点公告管理-选择多个公告删除")
    @PostMapping("/selectdeleteSite")
    public AjaxResult deleteSiteSetting(@RequestParam List<Integer> listId){
        AjaxResult result = webStateSettingService.WebStateSelectDelete(listId);
        return result;
    }

}

package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebStateManageRequire;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import com.ruoyi.project.monitor.service.WebStateManageService;
import com.ruoyi.project.monitor.service.WebStateSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/webStateManage")
public class WebStateManageController {
    @Autowired
    private WebStateManageService webStateManageService;

    /**
     * 输入公告信息
     */
    @ApiOperation(value = "网站管理-公告管理-新增公告")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody WebStateManageRequire webStateManageRequire)
    {

        AjaxResult result = webStateManageService.WebStateManageSetting(webStateManageRequire);
        //返回插入成功数据
        return result;

    }

    /**
     * 搜索和回显操作
     */
    @ApiOperation(value = "网站管理-公告管理-搜索和回显")
    @PostMapping("/searchSite")
    public AjaxResult searchSiteSetting(@RequestParam(required = false) String title, @RequestParam(required = false) Integer status){

        AjaxResult result = webStateManageService.WebStateManageSearch(title, status);
        return result;



    }

    /**
     * 编辑公告操作
     */
    @ApiOperation(value = "网站管理-公告管理-编辑公告")
    @PostMapping("/updateSite")
    public AjaxResult updateSiteSetting(@RequestBody WebStateManageRequire webStateManageRequire){
        AjaxResult result = webStateManageService.WebStateManageUpdate(webStateManageRequire);
        return result;
    }


    /**
     * 选择多个公告删除操作
     */
    @ApiOperation(value = "网站管理-公告管理-选择多个公告删除")
    @PostMapping("/selectdeleteSite")
    public AjaxResult deleteSiteSetting(@RequestParam List<Integer> listId){
        AjaxResult result = webStateManageService.WebStateSelectManageDelete(listId);
        return result;
    }

    /**
     * 发布公告操作
     */
    @ApiOperation(value = "网站管理-公告管理-发布公告")
    @PostMapping("/publish")
    public AjaxResult publishSiteSetting(@RequestParam int id){
        AjaxResult result = webStateManageService.WebStateManagePublish(id);
        return result;
    }
}

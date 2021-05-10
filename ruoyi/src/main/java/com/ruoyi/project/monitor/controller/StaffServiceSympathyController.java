package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.service.StaffServiceSympathyService;
import com.ruoyi.project.monitor.service.WebStateSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/unionSympathyRecord")

public class StaffServiceSympathyController {
    @Autowired
    private StaffServiceSympathyService staffServiceSympathyService;

    /**
     * 输入公告信息
     */
    @ApiOperation(value = "工会管理-慰问记录-新增记录")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody StaffServiceSympathyRequire staffServiceSympathyRequire)
    {

        AjaxResult result = staffServiceSympathyService.StaffServiceSympathySetting(staffServiceSympathyRequire);
        //返回插入成功数据
        return result;

    }

    /**
     * 搜索和回显操作
     */
    @ApiOperation(value = "工会管理-慰问记录-搜索和回显")
    @PostMapping("/searchSite")
    public AjaxResult searchSiteSetting(@RequestBody StaffServiceSympathySearch staffServiceSympathySearch){
        AjaxResult result1 = staffServiceSympathyService.StaffServiceSympathySearch(staffServiceSympathySearch);
        return result1;
    }

    /**
     * 编辑公告操作
     */
    @ApiOperation(value = "工会管理-慰问记录-编辑记录")
    @PostMapping("/updateSite")
    public AjaxResult updateSiteSetting(@RequestBody StaffServiceSympathyRequire staffServiceSympathyRequire){
        AjaxResult result = staffServiceSympathyService.StaffServiceSympathyUpdate(staffServiceSympathyRequire);
        return result;
    }

    /**
     * 选择多个公告删除操作
     */
    @ApiOperation(value = "工会管理-慰问记录-选择多个公告删除")
    @PostMapping("/selectdeleteSite")
    public AjaxResult deleteSiteSetting(@RequestBody List<Integer> listId){
        AjaxResult result = staffServiceSympathyService.StaffServiceSympathyDelete(listId);
        return result;
    }

    /**
     * 提交记录
     */
    @ApiOperation(value = "工会管理-慰问记录-提交记录")
    @PostMapping("/submitSite")
    public AjaxResult submitSiteSetting(int id){
        AjaxResult result = staffServiceSympathyService.StaffServiceSympathySubmit(id);
        return result;
    }

    /**
     * 批量导出
     */
    @ApiOperation(value = "工会管理-慰问记录-批量导出")
    @PostMapping("/outputSite")
    public AjaxResult outputSiteSetting(@RequestBody StaffServiceSympathySearch1 staffServiceSympathySearch1, HttpServletResponse response, HttpServletRequest request){
        AjaxResult result = staffServiceSympathyService.export(staffServiceSympathySearch1,response,request);
        return result;
    }
}

package com.ruoyi.project.activityreport.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.activityreport.entity.ActivityReport;
import com.ruoyi.project.activityreport.entity.ActivityReportRequire;
import com.ruoyi.project.activityreport.service.IActivityReportService;
import com.ruoyi.project.monitor.mapper.StaffServiceSympathyMapper;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.ruoyi.project.unionhelp.service.IDifficultEmployeesHelpRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础活动 前端控制器
 * </p>
 *
 * @author crl
 * @since 2021-05-10
 */
@Api(tags = "网站管理-基层活动管理")
@RestController
@RequestMapping("//activityReport")
public class ActivityReportController {
    @Autowired
    private IActivityReportService iActivityReportService;
    @Autowired
    private StaffServiceSympathyMapper staffServiceSympathyMapper;

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static Integer getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    @ApiOperation(value = "网站管理-基层活动管理-新增活动")
    @PutMapping("/insert")
    private AjaxResult insertUnionHelpInsert(@RequestBody ActivityReportRequire activityReportRequire) {
        Date time = new Date();
        Integer createTime = getSecondTimestamp(time);
        Integer createdUserId = activityReportRequire.getCreatedUserId();
        String createdUserName = staffServiceSympathyMapper.StaffServiceSympathyFindCreaterName(createdUserId);
        ActivityReport activityReport = new ActivityReport();
        activityReport.setAuditReason(activityReportRequire.getAuditReason());
        activityReport.setContent(activityReportRequire.getContent());
        activityReport.setAuditStatus(activityReportRequire.getAuditStatus());
        activityReport.setBudget(activityReportRequire.getBudget());
        activityReport.setCreatedUserId(activityReportRequire.getCreatedUserId());
        activityReport.setCreateUser(createdUserName);
        activityReport.setStartTime(getSecondTimestamp(activityReportRequire.getStartTime()));
        activityReport.setEndTime(getSecondTimestamp(activityReportRequire.getEndTime()));
        activityReport.setMobile(activityReportRequire.getMobile());
        activityReport.setTitle(activityReportRequire.getTitle());
        activityReport.setUpdatedTime(createTime);
        activityReport.setCreatedTime(createTime);
        activityReport.setUnitGroup(activityReportRequire.getUnitGroup());
        activityReport.setOrgId(activityReportRequire.getOrgId());
        iActivityReportService.save(activityReport);
        return AjaxResult.success("提交成功");
//        if () {
//
//        } else {
//            return AjaxResult.error("提交失败");
//        }
    }

    @ApiOperation(value = "网站管理-基层活动管理-编辑")
    @PutMapping("/update")
    private AjaxResult updateUnionHelpUpdate(@RequestBody ActivityReportRequire activityReportRequire) {

        Date time = new Date();
        Integer createTime = getSecondTimestamp(time);
        Integer createdUserId = activityReportRequire.getCreatedUserId();
        String createdUserName = staffServiceSympathyMapper.StaffServiceSympathyFindCreaterName(createdUserId);
        ActivityReport activityReport = new ActivityReport();
        activityReport.setId(activityReportRequire.getId());
        activityReport.setAuditReason(activityReportRequire.getAuditReason());
        activityReport.setContent(activityReportRequire.getContent());
        activityReport.setAuditStatus(activityReportRequire.getAuditStatus());
        activityReport.setBudget(activityReportRequire.getBudget());
        activityReport.setCreatedUserId(activityReportRequire.getCreatedUserId());
        activityReport.setCreateUser(createdUserName);
        activityReport.setStartTime(getSecondTimestamp(activityReportRequire.getStartTime()));
        activityReport.setEndTime(getSecondTimestamp(activityReportRequire.getEndTime()));
        activityReport.setMobile(activityReportRequire.getMobile());
        activityReport.setTitle(activityReportRequire.getTitle());
        activityReport.setUpdatedTime(createTime);
        activityReport.setUnitGroup(activityReportRequire.getUnitGroup());
        activityReport.setOrgId(activityReportRequire.getOrgId());

        if (iActivityReportService.updateById(activityReport)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }

    }

    @ApiOperation(value = "网站管理-基层活动管理-删除单个或多个接口")
    @PostMapping("/delete")
    private AjaxResult deleteUnionHelpUpdate(@RequestBody List<Integer> idList) {
        for(Integer id : idList){
            iActivityReportService.removeById(id);
        }
        return AjaxResult.success("提交成功");
    }


    @ApiOperation(value = "网站管理-基层活动管理-活动管理查询")
    @PostMapping("/activitymanage/search")
    private AjaxResult activityManageSearch(@RequestBody List<Integer> idList) {
        for(Integer id : idList){
            iActivityReportService.removeById(id);
        }
        return AjaxResult.success("提交成功");
    }
}


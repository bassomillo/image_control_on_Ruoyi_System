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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 基础活动 前端控制器
 * </p>
 *
 * @author crl
 * @since 2021-05-10
 */
@Api(tags = "网站管理-基层活动管理")
@Controller
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

        if (iActivityReportService.save(activityReport)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-操作-编辑")
    @PutMapping("/updateUnionHelp/{id}")
    private AjaxResult updateUnionHelpUpdate(@RequestBody ActivityReportRequire activityReportRequire) {

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
        activityReport.setUnitGroup(activityReportRequire.getUnitGroup());
        activityReport.setOrgId(activityReportRequire.getOrgId());

        if (iActivityReportService.save(activityReport)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }

    }
}


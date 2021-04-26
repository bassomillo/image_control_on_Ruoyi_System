package com.ruoyi.project.unionhelp.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.ruoyi.project.unionhelp.service.IDifficultEmployeesHelpRecordService;
import com.ruoyi.project.unionhelp.service.IUserProfileService1;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 困难员工帮扶记录表 前端控制器
 * </p>
 *
 * @author crl
 * @since 2021-04-14
 */
@Api(tags = "困难员工")
@RestController
@RequestMapping("//difficultEmployeesHelpRecord")
public class DifficultEmployeesHelpRecordController {
    @Autowired
    private IDifficultEmployeesHelpRecordService helpRecordService;
    @Autowired
    private IUserProfileService1 iUserProfileService;

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-新增记录")
    @PutMapping("/insert")
    private AjaxResult insertUnionHelpInsert(@RequestBody DifficultEmployeesHelpRecord difficultEmployeesHelpRecord){
        Date time = new Date();
        int createTime = getSecondTimestamp(time);
        difficultEmployeesHelpRecord.setCreatedTime(createTime);
        if(helpRecordService.save(difficultEmployeesHelpRecord)){
            return AjaxResult.success("提交成功");
        }
        else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-操作-编辑")
    @PutMapping("/update/{id}")
    private AjaxResult updateUnionHelpUpdate(@RequestBody DifficultEmployeesHelpRecord difficultEmployeesHelpRecord) {

        Date time = new Date();
        int updateTime = getSecondTimestamp(time);
        difficultEmployeesHelpRecord.setUpdatedTime(updateTime);
        if (helpRecordService.updateById(difficultEmployeesHelpRecord)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-新增记录-帮扶对象查询接口")
    @GetMapping("/getUserInfo/{CurrentPage}/{CurrentSize}")
    private AjaxResult findAllPage(@RequestParam("CurrentSize") int pageSize, @RequestParam("CurrremtPage") int index, @RequestParam(required = false) String searchContent){
        String searchContent1= searchContent.replace("%", "/%");
        List<Userinfo> userinfoList = iUserProfileService.findAllPage(pageSize, index-1, searchContent1);
        return AjaxResult.success("搜索成功",userinfoList);
        }
}




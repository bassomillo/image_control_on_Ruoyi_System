package com.ruoyi.project.unionhelp.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearch;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearchRequire;
import com.ruoyi.project.unionhelp.service.IDifficultEmployeesHelpRecordService;
import com.ruoyi.project.unionhelp.service.IUserProfileService1;
import com.ruoyi.project.unionhelp.vo.SearchUser;
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
    private IDifficultEmployeesHelpRecordService iDifficultEmployeesHelpRecordService;
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
    @PutMapping("/insertUnionHelp")
    private AjaxResult insertUnionHelpInsert(@RequestBody DifficultEmployeesHelpRecord difficultEmployeesHelpRecord){
        Date time = new Date();
        int createTime = getSecondTimestamp(time);
        difficultEmployeesHelpRecord.setCreatedTime(createTime);
        difficultEmployeesHelpRecord.setUpdatedTime(createTime);
        if(iDifficultEmployeesHelpRecordService.save(difficultEmployeesHelpRecord)){
            return AjaxResult.success("提交成功");
        }
        else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-操作-编辑")
    @PutMapping("/updateUnionHelp/{id}")
    private AjaxResult updateUnionHelpUpdate(@RequestBody DifficultEmployeesHelpRecord difficultEmployeesHelpRecord) {

        Date time = new Date();
        int updateTime = getSecondTimestamp(time);
        difficultEmployeesHelpRecord.setUpdatedTime(updateTime);
        if (iDifficultEmployeesHelpRecordService.updateById(difficultEmployeesHelpRecord)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-新增记录-帮扶对象查询接口")
    @PostMapping("/getUserInfo")
    private AjaxResult searchUser(@RequestBody SearchUser searchUser){

        return AjaxResult.success("搜索成功",iUserProfileService.search(searchUser));
        }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-删除接口")
    @GetMapping("/deleteUnionHelp/{id}")
    private AjaxResult deleteUnionHelpUpdate(@RequestParam("id") int id) {
        if (iDifficultEmployeesHelpRecordService.removeById(id)) {
            return AjaxResult.success("提交成功");
        } else {
            return AjaxResult.error("提交失败");
        }
    }

    @ApiOperation(value = "工会管理-困难员工-困难员工帮扶救助-查询")
    @PostMapping("/searchUnionHelp")
    private AjaxResult search(@RequestBody DifficultEmployeesHelpRecordSearchRequire difficultEmployeesHelpRecordSearchRequire){
        return iDifficultEmployeesHelpRecordService.searchHelper(difficultEmployeesHelpRecordSearchRequire);
    }



}



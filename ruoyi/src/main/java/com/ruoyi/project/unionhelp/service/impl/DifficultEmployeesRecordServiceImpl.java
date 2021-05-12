package com.ruoyi.project.unionhelp.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesRecord;
import com.ruoyi.project.unionhelp.mapper.DifficultEmployeesHelpRecordDao;
import com.ruoyi.project.unionhelp.mapper.DifficultEmployeesRecordDao;
import com.ruoyi.project.unionhelp.service.IDifficultEmployeesRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 困难员工记录表 服务实现类
 * </p>
 *
 * @author crl
 * @since 2021-04-13
 */
@Service
public class DifficultEmployeesRecordServiceImpl extends ServiceImpl<DifficultEmployeesRecordDao, DifficultEmployeesRecord> implements IDifficultEmployeesRecordService {

    @Autowired
    private DifficultEmployeesHelpRecordDao difficultEmployeesHelpRecordDao;

    @Override
    public int getSecondTimestamp(Date date){
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

    @Override
    public AjaxResult insertDifficultEmployeesRecord(DifficultEmployeesRecord difficultEmployeesRecord){
        Date time = new Date();
        int createTime = getSecondTimestamp(time);
        difficultEmployeesRecord.setCreatedTime(createTime);
        difficultEmployeesRecord.setUpdatedTime(createTime);
        if(this.save(difficultEmployeesRecord)){
            return AjaxResult.success("提交成功");
        }
        else {
            return AjaxResult.error("提交失败");
        }
    }

    @Override
    public AjaxResult updateDifficultEmployeesRecord(DifficultEmployeesRecord difficultEmployeesRecord){
        Date time = new Date();
        int updateTime = getSecondTimestamp(time);
        difficultEmployeesRecord.setUpdatedTime(updateTime);
        if(this.save(difficultEmployeesRecord)){
            return AjaxResult.success("提交成功");
        }
        else {
            return AjaxResult.error("提交失败");
        }
    }

}

package com.ruoyi.project.unionhelp.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 困难员工记录表 服务类
 * </p>
 *
 * @author crl
 * @since 2021-04-13
 */
public interface IDifficultEmployeesRecordService extends IService<DifficultEmployeesRecord> {

    AjaxResult insertDifficultEmployeesRecord(DifficultEmployeesRecord difficultEmployeesRecord);
    AjaxResult updateDifficultEmployeesRecord(DifficultEmployeesRecord difficultEmployeesRecord);
    int getSecondTimestamp(Date date);
}

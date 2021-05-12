package com.ruoyi.project.unionhelp.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearch;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearchRequire;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 困难员工帮扶记录表 服务类
 * </p>
 *
 * @author crl
 * @since 2021-04-14
 */
public interface IDifficultEmployeesHelpRecordService extends IService<DifficultEmployeesHelpRecord> {

    AjaxResult searchHelper(DifficultEmployeesHelpRecordSearchRequire difficultEmployeesHelpRecordSearchRequire);


}

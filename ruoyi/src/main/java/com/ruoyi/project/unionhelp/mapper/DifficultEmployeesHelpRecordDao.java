package com.ruoyi.project.unionhelp.mapper;

import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordGet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 困难员工帮扶记录表 Mapper 接口
 * </p>
 *
 * @author crl
 * @since 2021-04-14
 */
public interface DifficultEmployeesHelpRecordDao extends BaseMapper<DifficultEmployeesHelpRecord> {

    List<DifficultEmployeesHelpRecordGet> searchHelper(@Param("pageSize") Integer pageSize, @Param("index") Integer index, @Param("searchUser") String searchUser, @Param("searchHelper") String searchHelper);
}

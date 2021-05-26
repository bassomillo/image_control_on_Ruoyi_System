package com.ruoyi.project.activityreport.mapper;

import com.ruoyi.project.activityreport.entity.ActivityReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.activityreport.entity.ActivitySearch;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 * 基础活动 Mapper 接口
 * </p>
 *
 * @author crl
 * @since 2021-05-10
 */
public interface ActivityReportDao extends BaseMapper<ActivityReport> {
    public List<ActivityReport> activityReportSearch(@Param("orgId") Integer orgId, @Param("unitGroup") String unitGroup, @Param("title") String title);

}

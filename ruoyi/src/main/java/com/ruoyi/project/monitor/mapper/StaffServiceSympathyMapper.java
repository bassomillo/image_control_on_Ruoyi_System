package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.StaffServiceSympathy;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

public interface StaffServiceSympathyMapper {
    public boolean StaffServiceSympathySetting(StaffServiceSympathy staffServiceSympathy);
    public boolean StaffServiceSympathyUpdate(StaffServiceSympathy staffServiceSympathy);
    public boolean StaffServiceSympathyDelete(@Param("id") int id);
    public int StaffServiceSympathyCount(@Param("expenseOrgId") int expenseOrgId, @Param("orgId") int orgId, @Param("createdUserOrg") int createdUserOrg, @Param("submitStatus") int submitStatus);
    public List<StaffServiceSympathy> StaffServiceSympathySearch(@Param("expenseOrgId") int expenseOrgId, @Param("orgId") int orgId, @Param("createdUserOrg") int createdUserOrg, @Param("submitStatus") int submitStatus);
    public boolean StaffServiceSympathySubmit(@Param("id") int id, @Param("updateTime") Date updateTime, @Param("submitTime") Date submitTime);
    public int StaffServiceSympathySearchOrg(@Param("id") int id);
    public String StaffServiceSympathyGetOrg(@Param("orgId") int orgId);
}

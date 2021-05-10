package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.StaffServiceSympathy;
import com.ruoyi.project.monitor.domain.StaffServiceSympathyFindSon;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

public interface StaffServiceSympathyMapper<integer> {
    public boolean StaffServiceSympathySetting(StaffServiceSympathy staffServiceSympathy);
    public boolean StaffServiceSympathyUpdate(StaffServiceSympathy staffServiceSympathy);
    public boolean StaffServiceSympathyDelete(@Param("id") int id);
    public int StaffServiceSympathyCount(@Param("expenseOrgId") int expenseOrgId, @Param("orgId") int orgId, @Param("createdUserOrg") int createdUserOrg, @Param("submitStatus") Integer submitStatus, @Param("status") Integer status, @Param("type") String type, @Param("sons") List<Integer> sons);
    public List<StaffServiceSympathy> StaffServiceSympathySearch(@Param("expenseOrgId") int expenseOrgId, @Param("orgId") int orgId, @Param("createdUserOrg") int createdUserOrg, @Param("submitStatus") Integer submitStatus, @Param("status") Integer status, @Param("type") String type, @Param("sons") List<Integer> sons, @Param("pageSize") Integer pageSize, @Param("index") Integer index);
    public boolean StaffServiceSympathySubmit(@Param("id") int id, @Param("updateTime") Date updateTime, @Param("submitTime") Date submitTime);
    public int StaffServiceSympathySearchOrg(@Param("id") int id);
    public String StaffServiceSympathyGetOrg(@Param("orgId") int orgId);
    public List<StaffServiceSympathy> StaffServiceSympathySearch2(@Param("expenseOrgId") int expenseOrgId, @Param("orgId") int orgId, @Param("Org") int createdUserOrg, @Param("submitStatus") int submitStatus);
    public List<Integer> StaffServiceSympathySearchBelowOrg(@Param("id") int id);
    public List<StaffServiceSympathyFindSon> StaffServiceSympathyFindSon();
    public String StaffServiceSympathyFindCreaterName(@Param("createdUserId") int createdUserId);
}

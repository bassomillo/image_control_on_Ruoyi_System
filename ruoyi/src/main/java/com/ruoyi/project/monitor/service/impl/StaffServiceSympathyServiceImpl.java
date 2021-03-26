package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.StaffServiceSympathy;
import com.ruoyi.project.monitor.domain.StaffServiceSympathySearch;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import com.ruoyi.project.monitor.mapper.StaffServiceSympathyMapper;
import com.ruoyi.project.monitor.service.StaffServiceSympathyService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceSympathyServiceImpl implements StaffServiceSympathyService {
    @Autowired
    private StaffServiceSympathyMapper staffServiceSympathyMapper;

    @Override
    public AjaxResult StaffServiceSympathySetting(StaffServiceSympathy staffServiceSympathy) {
        try {
            Date time = new Date();
            staffServiceSympathy.setUpdatedTime(time);
            staffServiceSympathyMapper.StaffServiceSympathySetting(staffServiceSympathy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathyUpdate(StaffServiceSympathy staffServiceSympathy) {
        try {
            Date time = new Date();
            staffServiceSympathy.setUpdatedTime(time);
            staffServiceSympathyMapper.StaffServiceSympathyUpdate(staffServiceSympathy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathySearch(StaffServiceSympathySearch staffServiceSympathySearch) {

        int submitStatus = staffServiceSympathySearch.getSubmitStatus();
        int createdUserOrg = staffServiceSympathySearch.getCreatedUserOrg();
        int expenseOrgId = staffServiceSympathySearch.getExpenseOrgId();
        int orgId = staffServiceSympathySearch.getOrgId();
        List<StaffServiceSympathy> staffServiceSympathyList = staffServiceSympathyMapper.StaffServiceSympathySearch(expenseOrgId, orgId, createdUserOrg, submitStatus);
        int count = staffServiceSympathyMapper.StaffServiceSympathyCount(expenseOrgId, orgId, createdUserOrg, submitStatus);
        Map<String, Object> map = new HashMap<>();
        map.put("list", staffServiceSympathyList);
        map.put("count", count);

        return AjaxResult.success("提交成功", map);
    }

    @Override
    public AjaxResult StaffServiceSympathyDelete(List<Integer> listId) {
        try {
            for (int id : listId) {
                staffServiceSympathyMapper.StaffServiceSympathyDelete(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathySubmit(int id) {
        try {
            Date time = new Date();
            staffServiceSympathyMapper.StaffServiceSympathySubmit(id, time, time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("提交成功");
    }
}

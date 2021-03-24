package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.StaffServiceSympathy;
import com.ruoyi.project.monitor.domain.StaffServiceSympathySearch;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;

import java.util.List;

public interface StaffServiceSympathyService {
    public AjaxResult StaffServiceSympathySetting(StaffServiceSympathy staffServiceSympathy);
    public AjaxResult StaffServiceSympathyUpdate(StaffServiceSympathy staffServiceSympathy);
    public AjaxResult StaffServiceSympathySearch(StaffServiceSympathySearch staffServiceSympathySearch);
    public AjaxResult StaffServiceSympathyDelete(List<Integer> listId);
    public AjaxResult StaffServiceSympathySubmit(int id);
}

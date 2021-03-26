package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;

import java.util.List;

public interface WebStateSettingService {

    public AjaxResult WebStateSetting(WebStateSetting webStateSetting);
    public AjaxResult WebStateUpdate(WebStateSetting webStateSetting);
    public AjaxResult WebStateSearch(WebStateSearch webStateSearch);
    public AjaxResult WebStateDelete(int id);
    public AjaxResult WebStateDeleteAll();
    public AjaxResult WebStateSelectDelete(List<Integer> listId);
    //public AjaxResult WebStateGetting();
}

package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import lombok.NonNull;

import java.util.List;

public interface StaffServiceSympathyService {
    public AjaxResult StaffServiceSympathySetting(StaffServiceSympathyRequire staffServiceSympathyRequire);
    public AjaxResult StaffServiceSympathyUpdate(StaffServiceSympathyRequire staffServiceSympathyRequire);
    public AjaxResult StaffServiceSympathySearch(StaffServiceSympathySearch staffServiceSympathySearch);
    public AjaxResult StaffServiceSympathyDelete(List<Integer> listId);
    public AjaxResult StaffServiceSympathySubmit(int id);
    public  List<String> StringToList(String strs);
    public  String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens);
}

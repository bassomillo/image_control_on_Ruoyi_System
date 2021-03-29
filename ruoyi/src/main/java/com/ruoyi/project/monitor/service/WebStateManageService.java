package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebStateManage;
import com.ruoyi.project.monitor.domain.WebStateManageRequire;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.NonNull;

import java.util.List;

public interface WebStateManageService {
    public AjaxResult WebStateManageSetting(WebStateManageRequire webStateManageRequire);
    public AjaxResult WebStateManageUpdate(WebStateManageRequire webStateManageRequire);
    public AjaxResult WebStateManageSearch(String title, Integer status);
    public AjaxResult WebStateSelectManageDelete(List<Integer> listId);
    public AjaxResult WebStateManagePublish(int id);
    public  List<String> StringToList(String strs);
    public  String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens);

}

package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.mapper.WebStateManageMapper;
import com.ruoyi.project.monitor.mapper.WebStateSettingMapper;
import com.ruoyi.project.monitor.service.WebStateManageService;
import com.ruoyi.project.monitor.service.WebStateSettingService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WebStateManageServiceImpl implements WebStateManageService{
    @Autowired
    private WebStateManageMapper webStateManageMapper;

    /*
     * String to List
     */
    @Override
    public List<String> StringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }
    /*
     * List to String
     */
    @Override
    public  String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens) {
        final Iterator<?> it = tokens.iterator();
        if (!it.hasNext()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    @Override
    public AjaxResult WebStateManageSetting(WebStateManageRequire webStateManageRequire) {
        try {
            WebStateManage webStateManage = new WebStateManage();
            Date time = new Date();
            webStateManage.setUpdateTime(time);
            webStateManage.setTitle(webStateManageRequire.getTitle());
            webStateManage.setStatement(webStateManageRequire.getStatement());
            String workRange = ListToString(",", webStateManageRequire.getWorkRangeRequire());
            webStateManage.setWorkRange(workRange);
            webStateManageMapper.WebStateManageSetting(webStateManage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateManageUpdate(WebStateManageRequire webStateManageRequire) {
        try {
            WebStateManage webStateManage = new WebStateManage();
            Date time = new Date();
            webStateManage.setUpdateTime(time);
            webStateManage.setId(webStateManageRequire.getId());
            webStateManage.setTitle(webStateManageRequire.getTitle());
            webStateManage.setStatement(webStateManageRequire.getStatement());
            String workRange = ListToString(",", webStateManageRequire.getWorkRangeRequire());
            webStateManage.setWorkRange(workRange);
            webStateManageMapper.WebStateManageUpdate(webStateManage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateManageSearch(String title, Integer status) {
        String title1 = new String();
        if(title != null) {
            title1 = title.replace("%", "/%");
        }
        List<WebStateManage> webStateManageList = webStateManageMapper.WebStateManageSearch(title1, status);
        List<WebStateManageRequire> webStateManageRequireList = new ArrayList<>();
        for(WebStateManage webStateManage: webStateManageList){
            WebStateManageRequire webStateManageRequire = new WebStateManageRequire();
            webStateManageRequire.setId(webStateManage.getId());
            webStateManageRequire.setStatus(webStateManage.getStatus());
            webStateManageRequire.setStatement(webStateManage.getStatement());
            webStateManageRequire.setUpdateTime(webStateManage.getUpdateTime());
            webStateManageRequire.setTitle(webStateManage.getTitle());
            webStateManageRequire.setPublishTime(webStateManage.getPublishTime());
            List<String> workRangeRequire = StringToList(webStateManage.getWorkRange());
            webStateManageRequire.setWorkRangeRequire(workRangeRequire);
            webStateManageRequireList.add(webStateManageRequire);
        }
        int count = webStateManageMapper.WebStateManageCounting(title1, status);
        Map<String, Object> map = new HashMap<>();
        map.put("list", webStateManageRequireList);
        map.put("count", count);

        return AjaxResult.success("提交成功", map);
    }

    @Override
    public AjaxResult WebStateSelectManageDelete(List<Integer> listId) {
        try {
            for(int id : listId){
                webStateManageMapper.WebStateManageDelete(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateManagePublish(int id){
        try {
            Date time = new Date();
            webStateManageMapper.WebStateManagePublish(id, time, time);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateManageGetting(int id){
        WebStateManage webStateManage = webStateManageMapper.WebStateManageGetting(id);
        WebStateManageRequire webStateManageRequire = new WebStateManageRequire();
        webStateManageRequire.setId(webStateManage.getId());
        webStateManageRequire.setStatus(webStateManage.getStatus());
        webStateManageRequire.setStatement(webStateManage.getStatement());
        webStateManageRequire.setUpdateTime(webStateManage.getUpdateTime());
        webStateManageRequire.setTitle(webStateManage.getTitle());
        webStateManageRequire.setPublishTime(webStateManage.getPublishTime());
        List<String> workRangeRequire = StringToList(webStateManage.getWorkRange());
        webStateManageRequire.setWorkRangeRequire(workRangeRequire);
        return AjaxResult.success("提交成功", webStateManageRequire);
    }
}

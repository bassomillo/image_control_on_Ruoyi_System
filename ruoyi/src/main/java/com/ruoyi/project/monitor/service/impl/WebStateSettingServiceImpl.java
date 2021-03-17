package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebLinkSetting3;
import com.ruoyi.project.monitor.domain.WebStateSearch;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import com.ruoyi.project.monitor.mapper.WebStateSettingMapper;
import com.ruoyi.project.monitor.service.WebStateSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WebStateSettingServiceImpl implements WebStateSettingService {
    @Autowired
    private WebStateSettingMapper webStateSettingMapper;

    @Override
    public AjaxResult WebStateSetting(WebStateSetting webStateSetting) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            webStateSetting.setTime(time);
            boolean before = time.before(webStateSetting.getStartDate());
            boolean after = time.after(webStateSetting.getEndDate());
            if(before){
                webStateSetting.setStatus("未显示");
            }
            if(after){
                webStateSetting.setStatus("已过期");
            }
            if(!(before||after)){
                webStateSetting.setStatus("显示中");
            }
            webStateSettingMapper.WebStateSetting(webStateSetting);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }
    @Override
    public AjaxResult WebStateUpdate(WebStateSetting webStateSetting) {
        try {
            Date time = new Date();
            webStateSetting.setTime(time);
            boolean before = time.before(webStateSetting.getStartDate());
            boolean after = time.after(webStateSetting.getEndDate());
            if(before){
                webStateSetting.setStatus("未显示");
            }
            if(after){
                webStateSetting.setStatus("已过期");
            }
            if(!(before||after)){
                webStateSetting.setStatus("显示中");
            }
            webStateSettingMapper.WebStateUpdate(webStateSetting);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }
    @Override
    public AjaxResult WebStateSearch(WebStateSearch webStateSearch) {

            String status = webStateSearch.getStatus();
            String searchContent = webStateSearch.getSearchContent();
            String searchContent1= searchContent.replace("%", "/%");
            List<WebStateSetting> webStateSettingList = webStateSettingMapper.WebStateSearch(status, searchContent1);
            return AjaxResult.success("提交成功", webStateSettingList);
    }

    @Override
    public AjaxResult WebStateDelete(int id) {
        try {
            webStateSettingMapper.WebStateDelete(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateDeleteAll() {
        try {
            webStateSettingMapper.WebStateDeleteAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateSelectDelete(List<Integer> listId) {
        try {
            for(int id : listId){
                webStateSettingMapper.WebStateDelete(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult WebStateGetting() {

        List<WebStateSetting> webStateSettingList = webStateSettingMapper.WebStateGetting();

        return AjaxResult.success("提交成功", webStateSettingList);
    }
}

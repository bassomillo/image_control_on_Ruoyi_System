package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.WebLinkSetting1;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface WebStateSettingMapper {
    public boolean WebStateSetting(WebStateSetting webStateSetting);
    public boolean WebStateUpdate(WebStateSetting webStateSetting);
    public boolean WebStateDelete(@Param("id") int id);
    public boolean WebStateDeleteAll();
    public int WebStateGetting1(@Param("status") String status, @Param("searchContent") String searchContent);
    public List<WebStateSetting> WebStateGetting();
    public List<WebStateSetting> WebStateSearch(@Param("status") String status, @Param("searchContent") String searchContent);
}

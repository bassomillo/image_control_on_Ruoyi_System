package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.WebStateManage;
import com.ruoyi.project.monitor.domain.WebStateSetting;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

public interface WebStateManageMapper {
    public boolean WebStateManageSetting(WebStateManage webStateManage);
    public boolean WebStateManageUpdate(WebStateManage webStateManage);
    public boolean WebStateManageDelete(@Param("id") int id);
    public int WebStateManageGetting(@Param("title") String title, @Param("status") Integer status);
    public List<WebStateManage> WebStateManageSearch(@Param("title") String title, @Param("status") Integer status);
    public boolean WebStateManagePublish(@Param("id") int id, @Param("updateTime") Date updateTime, @Param("publishTime") Date publishTime);
    public WebStateManage WebStateManageGetting(@Param("id") int id);
    public int WebStateManageCounting(@Param("title") String title, @Param("status") Integer status);
}

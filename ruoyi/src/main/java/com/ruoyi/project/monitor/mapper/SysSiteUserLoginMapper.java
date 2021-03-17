package com.ruoyi.project.monitor.mapper;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.domain.SysSiteUserLogin;
public interface SysSiteUserLoginMapper {

    public boolean SysSiteUserLogin(SysSiteUserLogin sysSiteUserLogin);
    public boolean SysSiteUserLoginUpdate(SysSiteUserLogin sysSiteUserLogin);
    public SysSiteUserLogin SysSiteUserLoginGet();
}

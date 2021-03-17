package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteEmailSetting;
import com.ruoyi.project.monitor.domain.SysSiteUserLogin;


public interface SysSiteUserLoginService {
    public AjaxResult SysSiteUserLogin(SysSiteUserLogin sysSiteUserLogin);
    public AjaxResult SysSiteUserLoginGet();
}
package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteSetting;

public interface SysSiteSettingService {

    public AjaxResult SysSiteSetting(SysSiteSetting sysSiteSetting);
    public AjaxResult SysSiteGetting();
    public String SysSiteGettinglogo();
    public String SysSiteGettingFavicon();
}

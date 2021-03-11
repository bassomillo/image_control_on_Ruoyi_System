package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysAdvertisement;

public interface SysAdvertisementService {
    public AjaxResult AdvertisementGetting(Integer advid);
    public AjaxResult AdvertisementUpdate(SysAdvertisement sysAdvertisement);
}

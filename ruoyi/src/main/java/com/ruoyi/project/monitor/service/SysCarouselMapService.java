package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysCarouselMap;

public interface SysCarouselMapService {
    public AjaxResult CarouselMapUpdate(SysCarouselMap sysCarouselMap);

    public AjaxResult CarouselMapGetting(Integer posterid);
}

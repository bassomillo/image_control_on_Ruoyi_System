package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.SysCarouselMap;

public interface SysCarouselMapMapper {
    public Boolean CarouselMapUpdate(SysCarouselMap sysCarouselMap);

    public SysCarouselMap CarouselMapGetting(Integer billid);

}

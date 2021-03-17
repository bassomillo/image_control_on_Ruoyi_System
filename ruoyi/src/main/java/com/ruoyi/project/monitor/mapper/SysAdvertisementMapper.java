package com.ruoyi.project.monitor.mapper;

import com.ruoyi.project.monitor.domain.SysAdvertisement;

public interface SysAdvertisementMapper {
    public Boolean UpdateAdvertisement(SysAdvertisement sysAdvertisement);

    public SysAdvertisement GetAdvertisement(Integer advid);
}

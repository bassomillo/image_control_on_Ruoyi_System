package com.ruoyi.project.monitor.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebLinkSetting0;
import com.ruoyi.project.monitor.domain.WebLinkSetting1;
import com.ruoyi.project.monitor.domain.WebLinkSetting2;
import com.ruoyi.project.monitor.domain.WebLinkSetting3;

import java.util.List;

public interface WebLinkSettingService {

    public String WebLinkSetting0(WebLinkSetting0 webLinkSetting0);
    public String WebLinkSetting1(List<WebLinkSetting1> webLinkSetting1);
    public String WebLinkSetting2(List<WebLinkSetting2> webLinkSetting2);
    public String WebLinkSetting3(List<WebLinkSetting3> webLinkSetting3);

    public AjaxResult WebLinkGetting();
//    public AjaxResult WebLinkGetting1();
//    public AjaxResult WebLinkGetting2();
//    public AjaxResult WebLinkGetting3();

    public String WebLinkGettingLogo();
    public String WebLinkGettingWeixinQrcode();
    public String WebLinkGettingIosQrcode();
    public String WebLinkGettingAndroidQrcode();


}

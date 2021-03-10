package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.service.WebLinkSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/webLink")
public class WebLinkSettingController {
    @Autowired
    private WebLinkSettingService webLinkSettingService;
    /**
     * 输入友链信息
     */
    @ApiOperation(value = "网站管理-友情链接管理")
    @PostMapping("/insertSite")
    public AjaxResult insertSiteSetting(@RequestBody WebLinkSetting webLinkSetting)
    {

        WebLinkSetting0 webLinkSetting0 = webLinkSetting.getWebLinkSetting0();
        List<WebLinkSetting1> webLinkSetting1List = webLinkSetting.getWebLinkSetting1();
        List<WebLinkSetting2> webLinkSetting2List = webLinkSetting.getWebLinkSetting2();
        List<WebLinkSetting3> webLinkSetting3List = webLinkSetting.getWebLinkSetting3();
        String msg0 = webLinkSettingService.WebLinkSetting0(webLinkSetting0);
        String msg1 = webLinkSettingService.WebLinkSetting1(webLinkSetting1List);
        String msg2 = webLinkSettingService.WebLinkSetting2(webLinkSetting2List);
        String msg3 = webLinkSettingService.WebLinkSetting3(webLinkSetting3List);

        if(msg0.equals("")&&msg1.equals("")&&msg2.equals("")&&msg3.equals("")){
            return AjaxResult.success("设置成功");
        }
        else{
            return AjaxResult.error("设置失败:"+msg0+msg1+msg2+msg3);
        }

    }
    /**
     * 回显页面操作
     */
    @ApiOperation(value = "网站管理-友情链接管理-回显")
    @PostMapping("/getSite")
    public AjaxResult getSiteSetting(){
        AjaxResult result = webLinkSettingService.WebLinkGetting();
        return result;

    }
}

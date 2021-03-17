package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.mapper.WebLinkSetting0Mapper;
import com.ruoyi.project.monitor.mapper.WebLinkSetting1Mapper;
import com.ruoyi.project.monitor.mapper.WebLinkSetting2Mapper;
import com.ruoyi.project.monitor.mapper.WebLinkSetting3Mapper;
import com.ruoyi.project.monitor.service.WebLinkSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WebLinkSettingServiceImpl implements WebLinkSettingService {

    @Autowired
    private WebLinkSetting0Mapper webLinkSetting0Mapper;
    @Autowired
    private WebLinkSetting1Mapper webLinkSetting1Mapper;
    @Autowired
    private WebLinkSetting2Mapper webLinkSetting2Mapper;
    @Autowired
    private WebLinkSetting3Mapper webLinkSetting3Mapper;
    @Resource
    FastdfsClientUtil fastdfsClientUtil;

    @Override
    public String WebLinkSetting0(WebLinkSetting0 webLinkSetting0){
        try {
            String msg = "";
            String regex = "^[0-9]*$";
            String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
            if (webLinkSetting0.getWeiboAddress() != null) {
                if (!webLinkSetting0.getWeiboAddress().matches(regex1)) {
                    msg += "微博链接格式不符合要求，请以http://开头; ";
                }
            }
            if (webLinkSetting0.getLogoStatus() != null) {
                if (!webLinkSetting0.getLogoStatus().matches(regex)) {
                    msg += "Logo状态格式不对，值只能为0或1的字符串，开启为1关闭为0";
                }
            }
            if (webLinkSetting0.getWeiboStatus() != null) {
                if (!webLinkSetting0.getWeiboStatus().matches(regex)) {
                    msg += "微博状态格式不对，值只能为0或1的字符串，开启为1关闭为0";
                }
            }
            if (!"".equals(msg)) {
                return msg;
            }
            if (webLinkSetting0.getId() == null || webLinkSetting0.getId().equals("")) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
                String id = simpleDateFormat.format(new Date());
                webLinkSetting0.setId(id);
                webLinkSetting0.setTime(new Date());



                webLinkSetting0Mapper.WebLinkSetting0(webLinkSetting0);
            }
            else {
                // 判断前端传回的图片路径与数据库中之前的路径是否一致，不一致的话删除数据库的图片路径下的图片
//                WebLinkSetting0 webLinkSetting01 = webLinkSetting0Mapper.WebLinkGetting0();
//                if(webLinkSetting0.getLogoAddress() != webLinkSetting01.getLogoAddress()){
//                    fastdfsClientUtil.deleteFile(webLinkSetting01.getLogoAddress());
//                }
//                if(webLinkSetting0.getWeixinQrcodeAddress() != webLinkSetting01.getWeixinQrcodeAddress()){
//                    fastdfsClientUtil.deleteFile(webLinkSetting01.getWeixinQrcodeAddress());
//                }
//                if(webLinkSetting0.getIosQrcodeAddress() != webLinkSetting01.getIosQrcodeAddress()){
//                    fastdfsClientUtil.deleteFile(webLinkSetting01.getIosQrcodeAddress());
//                }
//                if(webLinkSetting0.getAndrodQrcodeAddress() != webLinkSetting01.getAndrodQrcodeAddress()){
//                    fastdfsClientUtil.deleteFile(webLinkSetting01.getAndrodQrcodeAddress());
//                }
                webLinkSetting0Mapper.WebLinkUpdate0(webLinkSetting0);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return "";

    }

    @Override
    public String WebLinkSetting1(List<WebLinkSetting1> webLinkSetting1) {
        try{
            webLinkSetting1Mapper.WebLinkDelete1();
            for(WebLinkSetting1 webLinkSetting11 : webLinkSetting1){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
                String id = simpleDateFormat.format(new Date());
                webLinkSetting11.setId(id);
                webLinkSetting11.setTime(new Date());
                String msg = "";
                String regex = "^[0-9]*$";
                String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
                if (webLinkSetting11.getFirstColumnAddress() != null) {
                    if (!webLinkSetting11.getFirstColumnAddress().matches(regex1)) {
                        msg += "第一列链接地址格式不符合要求，请以http://开头; ";
                    }
                }
                if (webLinkSetting11.getFirstColumnStatus() != null) {
                    if (!webLinkSetting11.getFirstColumnStatus().matches(regex)) {
                        msg += "第一列链接状态格式不对，值只能为0或1的字符串，开启为1关闭为0";
                    }
                }
                if (!"".equals(msg)) {
                    return msg;
                }
                webLinkSetting1Mapper.WebLinkSetting1(webLinkSetting11);
                }

        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    @Override
    public String WebLinkSetting2(List<WebLinkSetting2> webLinkSetting2) {
        try{
            webLinkSetting2Mapper.WebLinkDelete2();
            for(WebLinkSetting2 webLinkSetting21 : webLinkSetting2){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
                String id = simpleDateFormat.format(new Date());
                webLinkSetting21.setId(id);
                webLinkSetting21.setTime(new Date());
                String msg = "";
                String regex = "^[0-9]*$";
                String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
                if (webLinkSetting21.getSecondColumnAddress() != null) {
                    if (!webLinkSetting21.getSecondColumnAddress().matches(regex1)) {
                        msg += "第二列链接地址格式不符合要求，请以http://开头; ";
                    }
                }
                if (webLinkSetting21.getSecondColumnStatus() != null) {
                    if (!webLinkSetting21.getSecondColumnStatus().matches(regex)) {
                        msg += "第二列链接状态格式不对，值只能为0或1的字符串，开启为1关闭为0";
                    }
                }
                if (!"".equals(msg)) {
                    return msg;
                }
                webLinkSetting2Mapper.WebLinkSetting2(webLinkSetting21);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    @Override
    public String WebLinkSetting3(List<WebLinkSetting3> webLinkSetting3) {
        try{
            webLinkSetting3Mapper.WebLinkDelete3();
            for(WebLinkSetting3 webLinkSetting31 : webLinkSetting3){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
                String id = simpleDateFormat.format(new Date());
                webLinkSetting31.setId(id);
                webLinkSetting31.setTime(new Date());
                String msg = "";
                String regex = "^[0-9]*$";
                String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
                if (webLinkSetting31.getThirdColumnAddress() != null) {
                    if (!webLinkSetting31.getThirdColumnAddress().matches(regex1)) {
                        msg += "第三列链接地址格式不符合要求，请以http://开头; ";
                    }
                }
                if (webLinkSetting31.getThirdColumnStatus() != null) {
                    if (!webLinkSetting31.getThirdColumnStatus().matches(regex)) {
                        msg += "第三列链接状态格式不对，值只能为0或1的字符串，开启为1关闭为0";
                    }
                }
                if (!"".equals(msg)) {
                    return msg;
                }
                webLinkSetting3Mapper.WebLinkSetting3(webLinkSetting31);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    @Override
    public AjaxResult WebLinkGetting() {
        WebLinkSetting0 webLinkSetting0 = webLinkSetting0Mapper.WebLinkGetting0();
        List<WebLinkSetting1> webLinkSetting1List = webLinkSetting1Mapper.WebLinkGetting1();
        List<WebLinkSetting2> webLinkSetting2List = webLinkSetting2Mapper.WebLinkGetting2();
        List<WebLinkSetting3> webLinkSetting3List = webLinkSetting3Mapper.WebLinkGetting3();
        WebLinkSetting webLinkSetting = new WebLinkSetting();
        webLinkSetting.setWebLinkSetting0(webLinkSetting0);
        webLinkSetting.setWebLinkSetting1(webLinkSetting1List);
        webLinkSetting.setWebLinkSetting2(webLinkSetting2List);
        webLinkSetting.setWebLinkSetting3(webLinkSetting3List);

        return AjaxResult.success("提交成功", webLinkSetting);
    }

//    @Override
//    public AjaxResult WebLinkGetting1() {
//        List<WebLinkSetting1> webLinkSetting1List = webLinkSetting1Mapper.WebLinkGetting1();
//        return AjaxResult.success("提交成功", webLinkSetting1List);
//    }
//
//    @Override
//    public AjaxResult WebLinkGetting2() {
//        List<WebLinkSetting2> webLinkSetting2List = webLinkSetting2Mapper.WebLinkGetting2();
//        return AjaxResult.success("提交成功", webLinkSetting2List);
//    }
//
//    @Override
//    public AjaxResult WebLinkGetting3() {
//        List<WebLinkSetting3> webLinkSetting3List = webLinkSetting3Mapper.WebLinkGetting3();
//        return AjaxResult.success("提交成功", webLinkSetting3List);
//    }

    @Override
    public String WebLinkGettingLogo() {
        WebLinkSetting0 webLinkSetting0 = webLinkSetting0Mapper.WebLinkGetting0();
        return webLinkSetting0.getLogoAddress();
    }

    @Override
    public String WebLinkGettingWeixinQrcode() {
        WebLinkSetting0 webLinkSetting0 = webLinkSetting0Mapper.WebLinkGetting0();
        return webLinkSetting0.getWeixinQrcodeAddress();
    }

    @Override
    public String WebLinkGettingIosQrcode() {
        WebLinkSetting0 webLinkSetting0 = webLinkSetting0Mapper.WebLinkGetting0();
        return webLinkSetting0.getIosQrcodeAddress();
    }

    @Override
    public String WebLinkGettingAndroidQrcode() {
        WebLinkSetting0 webLinkSetting0 = webLinkSetting0Mapper.WebLinkGetting0();
        return webLinkSetting0.getAndroidQrcodeAddress();
    }
}

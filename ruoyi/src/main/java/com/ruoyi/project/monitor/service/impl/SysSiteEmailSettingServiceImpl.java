package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteEmailSetting;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.mapper.SysSiteEmailSettingMapper;
import com.ruoyi.project.monitor.mapper.SysSiteSettingMapper;
import com.ruoyi.project.monitor.service.SysSiteEmailSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SysSiteEmailSettingServiceImpl implements SysSiteEmailSettingService {
    @Autowired
    private SysSiteEmailSettingMapper sysSiteEmailSettingMapper;
    @Override
    public AjaxResult SysSiteEmailSetting(SysSiteEmailSetting sysSiteEmailSetting) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
            String id = simpleDateFormat.format(new Date());
            sysSiteEmailSetting.setId(id);
            sysSiteEmailSetting.setTime(new Date());


            String msg = "";
            String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
//            String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
            String regex1 = "^[0-9]*$";
            if(sysSiteEmailSetting.getEmailOpenStatus() == null){
                msg += "邮件发送状态不能为空; ";
            }
            if(sysSiteEmailSetting.getEmailOpenStatus() != null){
                if(!("0".toString().equals(sysSiteEmailSetting.getEmailOpenStatus()) ||"1".toString().equals(sysSiteEmailSetting.getEmailOpenStatus()))){
                    msg += "邮箱发送状态不符合要求，邮箱状态值只能为0或1的字符串，开启为1关闭为0; ";

                }
            }
            if(sysSiteEmailSetting.getSmtpServerAddress() == null){
                msg += "服务器地址不能为空; ";
            }
            if (sysSiteEmailSetting.getSmtpServerAddress() != null) {
                if (!sysSiteEmailSetting.getSmtpServerAddress().matches(regex)) {
                    msg += "smpt服务器地址不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入; ";
//                    return AjaxResult.error("邮箱格式不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入");
                }
            }
            if (sysSiteEmailSetting.getSmptPortNumber() == null) {
                msg += "smpt端口号不能为空; ";
            }
            if (sysSiteEmailSetting.getSmptPortNumber() != null){
                if(!sysSiteEmailSetting.getSmptPortNumber().matches(regex1)){
                    msg += "smpt端口号只能填入0-9的数字字符串; ";
                }
            }
            if(sysSiteEmailSetting.getSmptUserName() == null){
                msg += "用户名不能为空; ";
            }
            if (sysSiteEmailSetting.getSmptUserName() != null) {
                if (!sysSiteEmailSetting.getSmptUserName().matches(regex)) {
                    msg += "用户名不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入; ";
//                    return AjaxResult.error("邮箱格式不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入");
                }
            }
            if(sysSiteEmailSetting.getSmptAuthorizationCode() == null){
                msg += "授权码不能为空; ";
            }
            if(sysSiteEmailSetting.getSmptEmailAddress() == null){
                msg += "发件人地址不能为空; ";
            }
            if (sysSiteEmailSetting.getSmptEmailAddress() != null) {
                if (!sysSiteEmailSetting.getSmptEmailAddress().matches(regex)) {
                    msg += "发件人地址不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入; ";
//                    return AjaxResult.error("邮箱格式不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入");
                }
            }
            if(sysSiteEmailSetting.getEmailSenderName() == null){
                msg += "发件人名称不能为空";
            }
            if (!"".equals(msg)){
                return AjaxResult.error(msg);
            }
            sysSiteEmailSettingMapper.SysSiteEmailSetting(sysSiteEmailSetting);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }
}

package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.domain.SysSiteUserLogin;
import com.ruoyi.project.monitor.mapper.SysSiteUserLoginMapper;
import com.ruoyi.project.monitor.service.SysSiteUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SysSiteUserLoginServiceImpl implements SysSiteUserLoginService{
    @Autowired
    private SysSiteUserLoginMapper sysSiteUserLoginMapper;

    @Override
    public AjaxResult SysSiteUserLogin(SysSiteUserLogin sysSiteUserLogin) {
        try {

            String msg = "";
//            String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
//            String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
            String regex1 = "^[0-9]*$";
            if (sysSiteUserLogin.getRestrictForUserLogin() == null) {
                msg += "用户登录限制状态不能为空; ";
            }
            if (sysSiteUserLogin.getRestrictForUserLogin() != null) {
                if (!("0".toString().equals(sysSiteUserLogin.getRestrictForUserLogin()) || "1".toString().equals(sysSiteUserLogin.getRestrictForUserLogin()))) {
                    msg += "用户登录限制状态不符合要求，值只能为0或1的字符串，开启为1关闭为0; ";

                }
            }
            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }
            if(sysSiteUserLogin.getId() == null || sysSiteUserLogin.getId().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
            String id = simpleDateFormat.format(new Date());
            sysSiteUserLogin.setId(id);
            sysSiteUserLogin.setTime(new Date());
            sysSiteUserLoginMapper.SysSiteUserLogin(sysSiteUserLogin);
            }
            else {
                sysSiteUserLoginMapper.SysSiteUserLoginUpdate(sysSiteUserLogin);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("提交成功");
    }
    @Override
    public AjaxResult SysSiteUserLoginGet() {
        SysSiteUserLogin sysSiteUserLogin = sysSiteUserLoginMapper.SysSiteUserLoginGet();
        return AjaxResult.success("提交成功", sysSiteUserLogin);
    }
}
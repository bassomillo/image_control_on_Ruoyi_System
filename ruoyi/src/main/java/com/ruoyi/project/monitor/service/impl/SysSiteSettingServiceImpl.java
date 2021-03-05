package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.mapper.SysSiteSettingMapper;
import com.ruoyi.project.monitor.service.ISysJobLogService;
import com.ruoyi.project.monitor.service.SysSiteSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SysSiteSettingServiceImpl implements SysSiteSettingService {
    @Autowired
    private SysSiteSettingMapper sysSiteSettingMapper;

    @Override
    public AjaxResult SysSiteSetting(SysSiteSetting sysSiteSetting) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
            String id = simpleDateFormat.format(new Date());
            sysSiteSetting.setId(id);
            sysSiteSetting.setTime(new Date());


            String msg = "";
            String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
            String regex1 = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
            if (sysSiteSetting.getSysSiteMasterEmail() != null) {
                if (!sysSiteSetting.getSysSiteMasterEmail().matches(regex)) {
                    msg += "邮箱格式不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入; ";
//                    return AjaxResult.error("邮箱格式不符合要求，请以xx@xx.xx或xx@xx.xx.xx格式输入");
                }
            }
            if (sysSiteSetting.getSysSiteUrl() != null) {
                if (!sysSiteSetting.getSysSiteUrl().matches(regex1)) {
                    msg += "url格式不正确，请以http://开头";
//                    return AjaxResult.error("url格式不正确，请以http://开头");
                }
            }

            if (!"".equals(msg)){
                return AjaxResult.error(msg);
            }
            sysSiteSettingMapper.SysSiteSetting(sysSiteSetting);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }
}


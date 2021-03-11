package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.mapper.SysSiteSettingMapper;
import com.ruoyi.project.monitor.service.ISysJobLogService;
import com.ruoyi.project.monitor.service.SysSiteSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SysSiteSettingServiceImpl implements SysSiteSettingService {
    @Autowired
    private SysSiteSettingMapper sysSiteSettingMapper;
    @Resource
    FastdfsClientUtil fastdfsClientUtil;
    @Override
    public AjaxResult SysSiteSetting(SysSiteSetting sysSiteSetting) {

        try {
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

            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }
            if (sysSiteSetting.getId() == null || sysSiteSetting.getId().equals("")) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHssmmSSS");
                String id = simpleDateFormat.format(new Date());
                sysSiteSetting.setId(id);
                sysSiteSetting.setTime(new Date());



                sysSiteSettingMapper.SysSiteSetting(sysSiteSetting);
            }
            else {
                // 判断前端传回的图片路径与数据库中之前的路径是否一致，不一致的话删除数据库的图片路径下的图片
                SysSiteSetting sysSiteSetting1 = sysSiteSettingMapper.SysSiteGetting();
                if((sysSiteSetting.getSysSiteLogo() != sysSiteSetting1.getSysSiteLogo())&&(sysSiteSetting.getSysSiteLogo() != null)){
                    fastdfsClientUtil.deleteFile(sysSiteSetting1.getSysSiteLogo());
                }
                if((sysSiteSetting.getSysSiteFavicon() != sysSiteSetting1.getSysSiteFavicon())&&(sysSiteSetting.getSysSiteFavicon() != null)){
                    fastdfsClientUtil.deleteFile(sysSiteSetting1.getSysSiteFavicon());
                }
                sysSiteSettingMapper.SysSiteUpdate(sysSiteSetting);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult SysSiteGetting() {
        SysSiteSetting sysSiteSetting = sysSiteSettingMapper.SysSiteGetting();
        return AjaxResult.success("提交成功", sysSiteSetting);
    }
    @Override
    public String SysSiteGettinglogo(){
        SysSiteSetting sysSiteSetting = sysSiteSettingMapper.SysSiteGetting();
        return sysSiteSetting.getSysSiteLogo();
    }
    @Override
    public String SysSiteGettingFavicon(){
        SysSiteSetting sysSiteSetting = sysSiteSettingMapper.SysSiteGetting();
        return sysSiteSetting.getSysSiteFavicon();
    }
}


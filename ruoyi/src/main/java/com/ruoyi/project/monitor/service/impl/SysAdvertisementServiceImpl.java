package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.domain.SysAdvertisement;
import com.ruoyi.project.monitor.mapper.SysAdvertisementMapper;
import com.ruoyi.project.monitor.service.SysAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAdvertisementServiceImpl implements SysAdvertisementService {
    @Autowired
    SysAdvertisementMapper sysAdvertisementMapper;

    @Autowired
    FastdfsClientUtil fastdfsClientUtil;


    @Override
    public AjaxResult AdvertisementGetting(Integer advid) {
        try{
            String msg = "";
            if (advid > 4 || advid <0) {
                msg += "该页面不存在";
            }

            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }else{
                SysAdvertisement sysAdvertisement = sysAdvertisementMapper.GetAdvertisement(advid);
                return AjaxResult.success("提交成功", sysAdvertisement);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult AdvertisementUpdate(SysAdvertisement sysAdvertisement) {
        try {
            String msg = "";
            String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";

            if (sysAdvertisement.getLinkUrl() != null) {
                if (!sysAdvertisement.getLinkUrl().matches(regex)) {
                    msg += "url格式不正确，请以http://开头";
                }
            }

            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }
            else {
                // 判断前端传回的图片路径与数据库中之前的路径是否一致，不一致的话删除数据库的图片路径下的图片
                SysAdvertisement sysAdvertisement1 = sysAdvertisementMapper.GetAdvertisement(sysAdvertisement.getAdvId());
                if(sysAdvertisement1 != null && (sysAdvertisement.getPicUrl() != sysAdvertisement1.getPicUrl())){
                    fastdfsClientUtil.deleteFile(sysAdvertisement1.getPicUrl());
                }

                sysAdvertisementMapper.UpdateAdvertisement(sysAdvertisement);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }
}

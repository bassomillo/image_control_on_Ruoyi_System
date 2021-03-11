package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.domain.SysCarouselMap;
import com.ruoyi.project.monitor.mapper.SysCarouselMapMapper;
import com.ruoyi.project.monitor.service.SysCarouselMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysCarouselMapServiceImpl implements SysCarouselMapService {
    @Autowired
    SysCarouselMapMapper sysCarouselMapMapper;
    @Resource
    FastdfsClientUtil fastdfsClientUtil;

    @Override
    public AjaxResult CarouselMapUpdate(SysCarouselMap sysCarouselMap) {
        try {
            String msg = "";
            String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";

            if (sysCarouselMap.getLinkUrl() != null) {
                if (!sysCarouselMap.getLinkUrl().matches(regex)) {
                    msg += "url格式不正确，请以http://开头";
                }
            }

            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }
            else {
                // 判断前端传回的图片路径与数据库中之前的路径是否一致，不一致的话删除数据库的图片路径下的图片
                SysCarouselMap sysCarouselMap1 = sysCarouselMapMapper.CarouselMapGetting(sysCarouselMap.getPosterId());
                if(sysCarouselMap1.getPicUrl() != null && (sysCarouselMap.getPicUrl() != sysCarouselMap1.getPicUrl())){
                    fastdfsClientUtil.deleteFile(sysCarouselMap.getPicUrl());
                }

                sysCarouselMapMapper.CarouselMapUpdate(sysCarouselMap);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult CarouselMapGetting(Integer posterid) {
        try{
            String msg = "";
            if (posterid > 8 || posterid <0) {
                msg += "该页面不存在";
            }

            if (!"".equals(msg)) {
                return AjaxResult.error(msg);
            }else{
                SysCarouselMap sysCarouselMap = sysCarouselMapMapper.CarouselMapGetting(posterid);
                return AjaxResult.success("提交成功", sysCarouselMap);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }
}

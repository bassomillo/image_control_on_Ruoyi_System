package com.ruoyi.project.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.VO.WebTagVO;
import com.ruoyi.project.monitor.domain.VO.WebTagVO2;
import com.ruoyi.project.monitor.domain.VO.WebTagVO3;
import com.ruoyi.project.monitor.domain.WebTag;
import com.ruoyi.project.monitor.mapper.WebTagDao;
import com.ruoyi.project.monitor.service.WebTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Service
public class WebTagServiceImpl extends ServiceImpl<WebTagDao, WebTag> implements WebTagService {
    @Autowired
    WebTagDao webTagDao;

    @Override
    public AjaxResult WebTagInsert(String name) {
        try {
            WebTag webTag = new WebTag();
            webTag.setName(name);
            webTag.setOrgCode("1.");
            webTag.setOrgId(1);
            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));
            webTag.setCreatedTime(timestamp);

            webTagDao.InsertWebTag(webTag);

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult WebTagDelete(Integer id) {
        try {
            webTagDao.DeleteWebTag(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

   @Override
    public AjaxResult SearchWebTagById(Integer id) {
        try {
            WebTag webTag = webTagDao.SearchWebTagById(id);

            Integer timestamp = webTag.getCreatedTime();
            long time =timestamp.longValue();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date(time * 1000L));//转成string类型
            Date date1 = sdf.parse(date);//转成date类型

            WebTagVO webTagVO = new WebTagVO();
            webTagVO.setId(webTag.getId());
            webTagVO.setCreatedTime(date1);
            webTagVO.setName(webTag.getName());
            webTagVO.setOrgId(webTag.getOrgId());
            webTagVO.setOrgCode(webTag.getOrgCode());

            /*Map<WebTag, Date> map = new HashMap<>();
            map.put(webTag, date1);*/

            return AjaxResult.success("操作成功",webTagVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebTagUpdate(WebTag webTag) {
        try {
            webTagDao.UpdateWebTag(webTag);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult TagNamesGet(Integer pagesize, Integer page) {
        try {
            List<String> namelist = webTagDao.GetWebTagNames(pagesize, (page-1)*pagesize);
            return AjaxResult.success("操作成功", namelist);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebTagGet(Integer pagesize, Integer page) {
        try {
            List<WebTagVO2> tagVO2List = webTagDao.GetWebTags(pagesize,(page-1)*pagesize);
            List<WebTagVO3> tagVO3List = new ArrayList<>();
            Integer tagNum = webTagDao.GetTagNum();
            for(WebTagVO2 webTagVO2 : tagVO2List){
                Integer timestamp = webTagVO2.getCreatedTime();
                long time =timestamp.longValue();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(new Date(time * 1000L));//转成string类型
                Date date1 = sdf.parse(date);//转成date类型

                WebTagVO3 webTagVO3 = new WebTagVO3();
                webTagVO3.setId(webTagVO2.getId());
                webTagVO3.setCreatedTime(date1);
                webTagVO3.setName(webTagVO2.getName());
                webTagVO3.setOrgId(webTagVO2.getOrgId());
                webTagVO3.setOrgCode(webTagVO2.getOrgCode());
                webTagVO3.setGroupName(webTagVO2.getGroupName());

                tagVO3List.add(webTagVO3);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", tagVO3List);
            map.put("count", tagNum);
            return AjaxResult.success("操作成功", map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }
}

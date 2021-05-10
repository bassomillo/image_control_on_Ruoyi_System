package com.ruoyi.project.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.VO.WebTagGroupVO;
import com.ruoyi.project.monitor.domain.WebTagGroup;
import com.ruoyi.project.monitor.mapper.WebTagGroupDao;
import com.ruoyi.project.monitor.service.WebTagGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 标签组表 服务实现类
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Service
public class WebTagGroupServiceImpl extends ServiceImpl<WebTagGroupDao, WebTagGroup> implements WebTagGroupService {
    @Autowired
    WebTagGroupDao webTagGroupDao;

    @Override
    public Date IntegerToDate(Integer timestamp) throws ParseException {
        long time =timestamp.longValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time * 1000L));//转成string类型
        Date date1 = sdf.parse(date);//转成date类型
        return date1;
    }

    @Override
    public AjaxResult WebTagGroupInsert(WebTagGroup webTagGroup) {
        try {
            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));

            webTagGroup.setCreatedTime(timestamp);
            webTagGroup.setUpdatedTime(timestamp);
            webTagGroupDao.InsertWebTagGroup(webTagGroup);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult WebTagGroupDelete(Integer id) {
        try {
            webTagGroupDao.DeleteWebTagGroup(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult WebTagGroupSearchById(Integer id) {
        try {
            WebTagGroup webTagGroup = webTagGroupDao.SearchTagGroupById(id);
            Integer timestamp = webTagGroup.getCreatedTime();
            Date date = IntegerToDate(timestamp);//转成date类型

            Integer timestamp1 = webTagGroup.getUpdatedTime();
            Date date2 = IntegerToDate(timestamp1);
            WebTagGroupVO webTagGroupVO = new WebTagGroupVO();
            webTagGroupVO.setId(webTagGroup.getId());
            webTagGroupVO.setName(webTagGroup.getName());
            webTagGroupVO.setResident(webTagGroup.getResident());
            webTagGroupVO.setScope(webTagGroup.getScope());
            webTagGroupVO.setTagIds(webTagGroup.getTagIds());
            webTagGroupVO.setTagNum(webTagGroup.getTagNum());
            webTagGroupVO.setCreatedTime(date);
            webTagGroupVO.setUpdatedTime(date2);
            return AjaxResult.success("操作成功",webTagGroupVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebTagGroupUpdate(WebTagGroup webTagGroup) {
        try {
            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));

            webTagGroup.setUpdatedTime(timestamp);
            webTagGroupDao.UpdateWebTagGroup(webTagGroup);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult WebTagGroupGet(Integer pagesize, Integer page) {
        try {
            List<WebTagGroup> list = webTagGroupDao.GetWebTagGroups(pagesize,(page-1)*pagesize);
            List<WebTagGroupVO> tagGroupVOList = new ArrayList<>();
            Integer tagGroupNum = webTagGroupDao.GetTagGroupNum();
            for(WebTagGroup webTagGroup : list){
                Integer timestamp = webTagGroup.getCreatedTime();
                Date date = IntegerToDate(timestamp);//转成date类型

                Integer timestamp1 = webTagGroup.getUpdatedTime();
                Date date1 = IntegerToDate(timestamp1);

                WebTagGroupVO webTagGroupVO = new WebTagGroupVO();
                webTagGroupVO.setUpdatedTime(date);
                webTagGroupVO.setCreatedTime(date1);
                webTagGroupVO.setId(webTagGroup.getId());
                webTagGroupVO.setName(webTagGroup.getName());
                webTagGroupVO.setResident(webTagGroup.getResident());
                webTagGroupVO.setScope(webTagGroup.getScope());
                webTagGroupVO.setTagIds(webTagGroup.getTagIds());
                webTagGroupVO.setTagNum(webTagGroup.getTagNum());

                tagGroupVOList.add(webTagGroupVO);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("list", tagGroupVOList);
            map.put("count", tagGroupNum);
            return AjaxResult.success("操作成功", map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


}

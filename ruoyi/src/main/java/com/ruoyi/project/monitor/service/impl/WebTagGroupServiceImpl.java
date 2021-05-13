package com.ruoyi.project.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.VO.WebTagGroupVO;
import com.ruoyi.project.monitor.domain.WebTagGroup;
import com.ruoyi.project.monitor.mapper.WebTagGroupDao;
import com.ruoyi.project.monitor.service.WebTagGroupService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    /*
     * String to List
     */
    @Override
    public List<String> StringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }
    /*
     * List to String
     */
    @Override
    public  String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens) {
        final Iterator<?> it = tokens.iterator();
        if (!it.hasNext()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    @Override
    public Date IntegerToDate(Integer timestamp) throws ParseException {
        long time =timestamp.longValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time * 1000L));//转成string类型
        Date date1 = sdf.parse(date);//转成date类型
        return date1;
    }

    @Override
    public AjaxResult WebTagGroupInsert(WebTagGroupVO webTagGroupVO) {
        try {
            WebTagGroup webTagGroup = new WebTagGroup();

            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));

            webTagGroup.setCreatedTime(timestamp);
            webTagGroup.setUpdatedTime(timestamp);

            if(webTagGroupVO.getTagIds() == null || webTagGroupVO.getTagIds().isEmpty()){
                //webTagGroup.setTagIds("");
                webTagGroup.setName(webTagGroupVO.getName());
                webTagGroup.setResident(webTagGroupVO.getResident());
                webTagGroup.setTagNum(webTagGroupVO.getTagNum());
                //插入tag_group数据库
                webTagGroupDao.InsertWebTagGroup(webTagGroup);
            }else {
                //list转换为string
                String tagIds = ListToString(",", webTagGroupVO.getTagIds());

                webTagGroup.setTagIds(tagIds);
                webTagGroup.setName(webTagGroupVO.getName());
                webTagGroup.setResident(webTagGroupVO.getResident());
                webTagGroup.setTagNum(webTagGroupVO.getTagNum());
                //插入tag_group数据库
                webTagGroupDao.InsertWebTagGroup(webTagGroup);

                //插入tag_group_tag数据库
                Integer tagGroupId = webTagGroupDao.GetMaxId();
                String[] arr = tagIds.split(",");
                for(String s : arr){
                    if(s!=null){
                        Integer tagId = Integer.valueOf(s);
                        webTagGroupDao.InsertWebTagGroupTag(tagId, tagGroupId);
                    }
                }
            }

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
            webTagGroupDao.DeleteWebTagGroupTag(id);
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

            if(webTagGroup.getTagIds() == null || webTagGroup.getTagIds().isEmpty()){
                WebTagGroupVO webTagGroupVO = new WebTagGroupVO();
                webTagGroupVO.setId(webTagGroup.getId());
                webTagGroupVO.setName(webTagGroup.getName());
                webTagGroupVO.setResident(webTagGroup.getResident());
                webTagGroupVO.setScope(webTagGroup.getScope());
                //webTagGroupVO.setTagIds(tagIds);
                webTagGroupVO.setTagNum(webTagGroup.getTagNum());
                webTagGroupVO.setCreatedTime(date);
                webTagGroupVO.setUpdatedTime(date2);
                return AjaxResult.success("操作成功",webTagGroupVO);
            }else {
                //string转为List<Integer>
                List<Integer> tagIds = StringToList(webTagGroup.getTagIds()).stream().map(Integer::parseInt).collect(Collectors.toList());

                WebTagGroupVO webTagGroupVO = new WebTagGroupVO();
                webTagGroupVO.setId(webTagGroup.getId());
                webTagGroupVO.setName(webTagGroup.getName());
                webTagGroupVO.setResident(webTagGroup.getResident());
                webTagGroupVO.setScope(webTagGroup.getScope());
                webTagGroupVO.setTagIds(tagIds);
                webTagGroupVO.setTagNum(webTagGroup.getTagNum());
                webTagGroupVO.setCreatedTime(date);
                webTagGroupVO.setUpdatedTime(date2);
                return AjaxResult.success("操作成功",webTagGroupVO);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebTagGroupUpdate(WebTagGroupVO webTagGroupVO) {
        try {
            WebTagGroup webTagGroup = new WebTagGroup();

            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));

            webTagGroup.setUpdatedTime(timestamp);

            Integer groupId = webTagGroupVO.getId();

            //list转换为string
            if(webTagGroupVO.getTagIds() == null || webTagGroupVO.getTagIds().isEmpty()){
                webTagGroup.setTagIds("");
                webTagGroup.setId(groupId);
                webTagGroup.setName(webTagGroupVO.getName());
                webTagGroup.setResident(webTagGroupVO.getResident());
                webTagGroup.setTagNum(webTagGroupVO.getTagNum());

                //更新tag_group表
                webTagGroupDao.UpdateWebTagGroup(webTagGroup);

                //更新tag_group_tag表
                webTagGroupDao.DeleteWebTagGroupTag(groupId);

            }else {
                String tagIds = ListToString(",", webTagGroupVO.getTagIds());
                webTagGroup.setTagIds(tagIds);
                webTagGroup.setId(webTagGroupVO.getId());
                webTagGroup.setName(webTagGroupVO.getName());
                webTagGroup.setResident(webTagGroupVO.getResident());
                webTagGroup.setTagNum(webTagGroupVO.getTagNum());

                //更新tag_group表
                webTagGroupDao.UpdateWebTagGroup(webTagGroup);

                //更新tag_group_tag表
                webTagGroupDao.DeleteWebTagGroupTag(groupId);

                String[] arr = tagIds.split(",");
                for(String s : arr){
                    if(s!=null){
                        Integer tagId = Integer.valueOf(s);
                        webTagGroupDao.InsertWebTagGroupTag(tagId, groupId);
                    }
                }
            }

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

                if(webTagGroup.getTagIds() == null || webTagGroup.getTagIds().isEmpty()){
                    webTagGroupVO.setUpdatedTime(date);
                    webTagGroupVO.setCreatedTime(date1);
                    webTagGroupVO.setId(webTagGroup.getId());
                    webTagGroupVO.setName(webTagGroup.getName());
                    webTagGroupVO.setResident(webTagGroup.getResident());
                    webTagGroupVO.setScope(webTagGroup.getScope());
                    //webTagGroupVO.setTagIds(tagIds);
                    webTagGroupVO.setTagNum(webTagGroup.getTagNum());
                    tagGroupVOList.add(webTagGroupVO);
                }else {
                    //string转为List<Integer>
                    List<Integer> tagIds = StringToList(webTagGroup.getTagIds()).stream().map(Integer::parseInt).collect(Collectors.toList());

                    webTagGroupVO.setUpdatedTime(date);
                    webTagGroupVO.setCreatedTime(date1);
                    webTagGroupVO.setId(webTagGroup.getId());
                    webTagGroupVO.setName(webTagGroup.getName());
                    webTagGroupVO.setResident(webTagGroup.getResident());
                    webTagGroupVO.setScope(webTagGroup.getScope());
                    webTagGroupVO.setTagIds(tagIds);
                    webTagGroupVO.setTagNum(webTagGroup.getTagNum());
                    tagGroupVOList.add(webTagGroupVO);
                }

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

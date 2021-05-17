package com.ruoyi.project.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.VO.WebArticleCategoryBtreeVO;
import com.ruoyi.project.monitor.domain.VO.WebArticleCategoryVO;
import com.ruoyi.project.monitor.domain.WebArticleCategory;
import com.ruoyi.project.monitor.mapper.WebArticleCategoryMapper;
import com.ruoyi.project.monitor.service.WebArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author zm
 * @since 2021-04-23
 */
@Service
public class WebArticleCategoryServiceImpl extends ServiceImpl<WebArticleCategoryMapper, WebArticleCategory> implements WebArticleCategoryService {

    @Autowired
    WebArticleCategoryMapper webArticleCategoryMapper;

    @Override
    public Date IntegerToDate(Integer timestamp) throws ParseException {
        long time =timestamp.longValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time * 1000L));//转成string类型
        Date date1 = sdf.parse(date);//转成date类型
        return date1;
    }

    @Override
    public AjaxResult TopArticleCategoryGet(Integer pagesize, Integer page, String name) {
        try {
            List<String> list = webArticleCategoryMapper.GetTopArticleCategory(pagesize, (page-1)*pagesize, name);
            return AjaxResult.success("操作成功", list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebArticleCategoryInsert(WebArticleCategory webArticleCategory) {
        try {
            //生成时间戳
            long time = System.currentTimeMillis() / 1000;
            Integer timestamp = Integer.valueOf(Long.toString(time));

            webArticleCategory.setCreatedTime(timestamp);

            webArticleCategory.setWeight(webArticleCategoryMapper.KidArticleNum(webArticleCategory.getParentId())+1);
            webArticleCategoryMapper.InsertWebArticleCategory(webArticleCategory);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult WebArticleCategoryDelete(Integer id, Integer parentId) {
        try {
            if(parentId !=0){
                webArticleCategoryMapper.DeleteWebArticleCategory(id);
            }else if(webArticleCategoryMapper.KidArticleNum(id)==0){
                webArticleCategoryMapper.DeleteWebArticleCategory(id);
            }else{
                return AjaxResult.error("该栏目下有子栏目，不能删除");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult SearchArticleCategoryById(Integer id) {
        try {
            WebArticleCategory webArticleCategory = webArticleCategoryMapper.SearchArticleCategoryById(id);
            Integer timestamp = webArticleCategory.getCreatedTime();
            Date date = IntegerToDate(timestamp);//转成date类型

            WebArticleCategoryVO webArticleCategoryVO = new WebArticleCategoryVO();
            webArticleCategoryVO.setCreatedTime(date);
            webArticleCategoryVO.setId(webArticleCategory.getId());
            webArticleCategoryVO.setName(webArticleCategory.getName());
            webArticleCategoryVO.setParentId(webArticleCategory.getParentId());
            webArticleCategoryVO.setPublishArticle(webArticleCategory.getPublishArticle());
            webArticleCategoryVO.setPublished(webArticleCategory.getPublished());
            webArticleCategoryVO.setSeoDesc(webArticleCategory.getSeoDesc());
            webArticleCategoryVO.setSeoKeyword(webArticleCategory.getSeoKeyword());
            webArticleCategoryVO.setSeoTitle(webArticleCategory.getSeoTitle());
            webArticleCategoryVO.setWeight(webArticleCategory.getWeight());
            return AjaxResult.success("操作成功", webArticleCategoryVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebArticleCategoryGet(Integer pagesize, Integer page) {
        try {
            List<WebArticleCategory> list = webArticleCategoryMapper.GetWebArticleCategorys(pagesize, (page-1)*pagesize);
            List<WebArticleCategoryVO> webArticleCategoryVOList = new ArrayList<>();
            List<WebArticleCategoryBtreeVO> webArticleCategoryBtreeVOList = new ArrayList<>();
            Integer articleCategoryNum = webArticleCategoryMapper.GetArticleCategoryNum();
            for(WebArticleCategory webArticleCategory : list){
                Integer timestamp = webArticleCategory.getCreatedTime();
                Date date = IntegerToDate(timestamp);//转成date类型

                if(webArticleCategory.getParentId() == 0){
                    WebArticleCategoryBtreeVO webArticleCategoryBtreeVO = new WebArticleCategoryBtreeVO();
                    webArticleCategoryBtreeVO.setCreatedTime(date);
                    webArticleCategoryBtreeVO.setId(webArticleCategory.getId());
                    webArticleCategoryBtreeVO.setName(webArticleCategory.getName());
                    webArticleCategoryBtreeVO.setParentId(webArticleCategory.getParentId());
                    webArticleCategoryBtreeVO.setPublishArticle(webArticleCategory.getPublishArticle());
                    webArticleCategoryBtreeVO.setPublished(webArticleCategory.getPublished());
                    webArticleCategoryBtreeVO.setSeoDesc(webArticleCategory.getSeoDesc());
                    webArticleCategoryBtreeVO.setSeoKeyword(webArticleCategory.getSeoKeyword());
                    webArticleCategoryBtreeVO.setSeoTitle(webArticleCategory.getSeoTitle());
                    webArticleCategoryBtreeVO.setWeight(webArticleCategory.getWeight());
                    webArticleCategoryBtreeVOList.add(webArticleCategoryBtreeVO);
                }

            }
            for(WebArticleCategory webArticleCategory : list){
                Integer timestamp = webArticleCategory.getCreatedTime();
                Date date = IntegerToDate(timestamp);//转成date类型
                WebArticleCategoryVO webArticleCategoryVO = new WebArticleCategoryVO();

                if (webArticleCategory.getParentId() != 0){
                    webArticleCategoryVO.setCreatedTime(date);
                    webArticleCategoryVO.setId(webArticleCategory.getId());
                    webArticleCategoryVO.setName(webArticleCategory.getName());
                    webArticleCategoryVO.setParentId(webArticleCategory.getParentId());
                    webArticleCategoryVO.setPublishArticle(webArticleCategory.getPublishArticle());
                    webArticleCategoryVO.setPublished(webArticleCategory.getPublished());
                    webArticleCategoryVO.setSeoDesc(webArticleCategory.getSeoDesc());
                    webArticleCategoryVO.setSeoKeyword(webArticleCategory.getSeoKeyword());
                    webArticleCategoryVO.setSeoTitle(webArticleCategory.getSeoTitle());
                    webArticleCategoryVO.setWeight(webArticleCategory.getWeight());

                    for(WebArticleCategoryBtreeVO webArticleCategoryBtreeVO : webArticleCategoryBtreeVOList){
                        if(webArticleCategoryBtreeVO.getId() == webArticleCategory.getParentId()){
                            webArticleCategoryVOList.add(webArticleCategoryVO);
                            webArticleCategoryBtreeVO.setChildren(webArticleCategoryVOList);
                        }
                    }
                }

            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", webArticleCategoryBtreeVOList);
            map.put("count", articleCategoryNum);
            return AjaxResult.success("操作成功", map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public AjaxResult WebArticleCategoryUpdate(WebArticleCategory webArticleCategory) {
        try {
            webArticleCategory.setWeight(webArticleCategoryMapper.KidArticleNum(webArticleCategory.getParentId())+1);
            webArticleCategoryMapper.UpdateWebArticleCategory(webArticleCategory);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }
}

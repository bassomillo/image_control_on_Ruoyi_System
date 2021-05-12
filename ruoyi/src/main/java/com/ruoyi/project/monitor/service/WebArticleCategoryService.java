package com.ruoyi.project.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebArticleCategory;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * 资讯表 服务类
 * </p>
 *
 * @author zm
 * @since 2021-04-23
 */
public interface WebArticleCategoryService extends IService<WebArticleCategory> {
    AjaxResult TopArticleCategoryGet(Integer pagesize, Integer page, String name);

    AjaxResult WebArticleCategoryInsert (WebArticleCategory webArticleCategory);

    AjaxResult WebArticleCategoryDelete(Integer id, Integer parentId);

    AjaxResult SearchArticleCategoryById(Integer id);

    AjaxResult WebArticleCategoryGet(Integer pagesize, Integer page);

    AjaxResult WebArticleCategoryUpdate(WebArticleCategory webArticleCategory);

    Date IntegerToDate(Integer timestamp) throws ParseException;

}

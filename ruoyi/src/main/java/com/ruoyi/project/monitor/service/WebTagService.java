package com.ruoyi.project.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebTag;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
public interface WebTagService extends IService<WebTag> {
    AjaxResult WebTagInsert (String name);

    AjaxResult WebTagDelete(Integer id);

    AjaxResult SearchWebTagById(Integer id);

    AjaxResult WebTagGet(Integer pagesize, Integer page);

    AjaxResult WebTagUpdate(WebTag webTag);

    AjaxResult TagNamesGet(String name, Integer pagesize, Integer page);

}

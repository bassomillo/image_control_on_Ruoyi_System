package com.ruoyi.project.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebTagGroup;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * 标签组表 服务类
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
public interface WebTagGroupService extends IService<WebTagGroup> {
    AjaxResult WebTagGroupInsert(WebTagGroup webTagGroup);

    AjaxResult WebTagGroupDelete(Integer id);

    AjaxResult WebTagGroupSearchById(Integer id);

    AjaxResult WebTagGroupUpdate(WebTagGroup webTagGroup);

    AjaxResult WebTagGroupGet(Integer pagesize, Integer page);

    Date IntegerToDate(Integer timestamp) throws ParseException;

}

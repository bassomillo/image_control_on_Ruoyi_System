package com.ruoyi.project.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.VO.WebTagGroupVO;
import com.ruoyi.project.monitor.domain.WebTagGroup;
import lombok.NonNull;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 标签组表 服务类
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
public interface WebTagGroupService extends IService<WebTagGroup> {
    AjaxResult WebTagGroupInsert(WebTagGroupVO webTagGroupVO);

    AjaxResult WebTagGroupDelete(Integer id);

    AjaxResult WebTagGroupSearchById(Integer id);

    AjaxResult WebTagGroupUpdate(WebTagGroupVO webTagGroupVO);

    AjaxResult WebTagGroupGet(Integer pagesize, Integer page);

    Date IntegerToDate(Integer timestamp) throws ParseException;

    String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens);

    List<String> StringToList(String strs);

}

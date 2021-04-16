package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.SuggestBox;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */
public interface ISuggestService {

    /**
     * 新增建言
     * @param suggest
     * @return
     */
    AjaxResult insertSuggest(SuggestBox suggest);

    /**
     * 查询首页建言记录
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getTopSuggestList(Integer userId,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 根据id查询记录详情
     * @param id
     * @return
     */
    AjaxResult getSuggestDetailById(Integer id);

    /**
     * 条件查询后台建言列表
     * @param content
     * @param year
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getBackSuggestList(String content,
                                  Integer year,
                                  Integer pageNum,
                                  Integer pageSize);

    /**
     * 批量删除后台建言
     * @param idList
     * @return
     */
    AjaxResult deleteSuggest(List<Integer> idList);

    /**
     * 后台回复建言
     * @param content
     * @param suggestId
     * @param userId
     * @return
     */
    AjaxResult replySuggest(String content,
                            Integer suggestId,
                            Integer userId);

    /**
     * 上传首页建言文件
     * @param file
     * @param userId
     * @return
     */
    AjaxResult uploadSuggestFile(MultipartFile file,
                                 Integer userId);

    /**
     * 删除首页建言文件
     * @param id
     * @return
     */
    AjaxResult deleteFile(Integer id);

    /**
     * 下载上传的文件
     * @param id
     * @param response
     */
    AjaxResult downloadFile(Integer id,
                      HttpServletResponse response);

    /**
     * 批量导出
     * @param content
     * @param year
     * @param response
     * @param request
     */
    AjaxResult export(String content,
                Integer year,
                HttpServletResponse response,
                HttpServletRequest request);
}

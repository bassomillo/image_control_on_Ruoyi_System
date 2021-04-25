package com.ruoyi.project.democratic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 主席信箱表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
public interface IChairmanLetterService extends IService<ChairmanLetterBox> {

    /**
     * 获取写信对象id
     * @param userId
     * @return
     */
    AjaxResult getLetterMan(Integer userId);

    /**
     * 发送信件
     * @param letterBox
     * @return
     */
    AjaxResult insertChairmanLetter(ChairmanLetterBox letterBox);

    /**
     * 查询首页信件列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getTopChairmanList(Integer userId,
                                  Integer pageNum,
                                  Integer pageSize);

    /**
     * 查询记录详情
     * @param id
     * @return
     */
    AjaxResult getChairmanDetailById(Integer id);

    /**
     * 首页回复评价
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    AjaxResult evaluate(String evaluate,
                        String evaluateContent,
                        Integer requireId);

    /**
     * 后台条件查询列表
     * @param content
     * @param year
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getBackLetterList(String content,
                                 Integer year,
                                 Integer userId,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 后台批量删除
     * @param idList
     * @return
     */
    AjaxResult deleteLetter(List<Integer> idList);

    /**
     * 后台批量回复
     * @param replyLetter
     * @return
     */
    AjaxResult replyLetter(ReplyLetterDO replyLetter);

    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    AjaxResult uploadFile(MultipartFile file,
                          Integer userId);

    /**
     * 删除文件
     * @param id
     * @return
     */
    AjaxResult deleteFile(Integer id);

    /**
     * 下载文件
     * @param id
     * @param response
     * @return
     */
    AjaxResult downloadFile(Integer id,
                            HttpServletResponse response);

    /**
     * 批量导出
     * @param content
     * @param year
     * @param userId
     * @param response
     * @param request
     * @return
     */
    AjaxResult export(String content,
                      Integer year,
                      Integer userId,
                      HttpServletResponse response,
                      HttpServletRequest request);
}

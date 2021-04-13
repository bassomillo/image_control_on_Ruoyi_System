package com.ruoyi.project.democratic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 总经理信箱 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
public interface ManagerLetterService extends IService<ManagerLetterBox> {

    /**
     * 首页-获取总经理信息
     * @param userId
     * @return
     */
    AjaxResult getLetterMan(Integer userId);

    /**
     * 首页-发送信件
     * @param letterBox
     * @return
     */
    AjaxResult insertManagerLetter(ManagerLetterBox letterBox);

    /**
     * 首页-查询回复记录列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getTopManagerList(Integer userId,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 根据单个记录id查详情
     * @param id
     * @return
     */
    AjaxResult getManagerDetailById(Integer id);

    /**
     * 评价回复
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    AjaxResult evaluate(String evaluate,
                        String evaluateContent,
                        Integer requireId);

    /**
     * 后台-条件查询
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
     * 后台批量删除信件
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

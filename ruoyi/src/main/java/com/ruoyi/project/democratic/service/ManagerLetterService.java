package com.ruoyi.project.democratic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;

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
     * @param fileList
     * @return
     */
    AjaxResult insertManagerLetter(ManagerLetterBox letterBox,
                                   List<Integer> fileList);

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
}

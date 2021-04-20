package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;

/**
 * <p>
 * 考试表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-15
 */
public interface IExamService extends IService<Exam> {

    /**
     * 新增考试
     * @param exam
     * @return
     */
    AjaxResult insertExam(Exam exam);

    /**
     * 条件查询后台列表
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getBackExamList(String title,
                           Integer pageNum,
                           Integer pageSize);

    /**
     * 后台发布考试
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult publish(Integer examId,
                       Integer userId);

    /**
     * 后台取消发布
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult unpublish(Integer examId,
                         Integer userId);

    /**
     * 后台回收考试
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult over(Integer examId,
                    Integer userId);

    /**
     * 后台删除考试
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult deleteBack(Integer examId,
                          Integer userId);

    /**
     * 后台重命名
     * @param examId
     * @param userId
     * @param title
     * @return
     */
    AjaxResult rename(Integer examId,
                          Integer userId,
                          String title);

    /**
     * 根据id查详情
     * @param examId
     * @return
     */
    AjaxResult getDetailById(Integer examId);

    /**
     * 更改考试基础信息
     * @param examBase
     * @return
     */
    AjaxResult updateBaseData(ExamBaseVO examBase);
}

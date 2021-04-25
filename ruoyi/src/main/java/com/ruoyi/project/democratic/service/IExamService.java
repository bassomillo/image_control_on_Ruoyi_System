package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * 后台更改考试基础信息
     * @param examBase
     * @return
     */
    AjaxResult updateBaseData(ExamBaseVO examBase);

    /**
     * 后台编辑标题、封面
     * @param exam
     * @return
     */
    AjaxResult updateExam(Exam exam);

    /**
     * 后台新增题目
     * @param question
     * @return
     */
    AjaxResult insertQuestion(ExamQuestion question);

    /**
     * 后台编辑题目
     * @param question
     * @return
     */
    AjaxResult updateQuestion(ExamQuestion question);

    /**
     * 后台-删除题目/选项
     * @param questionId
     * @param optionId
     * @return
     */
    AjaxResult deleteQuestionOrOption(Integer questionId,
                                      Integer optionId);

    /**
     * 后台-校验导入
     * @param file
     * @param examId
     * @param request
     * @return
     */
    AjaxResult checkImportQuestion(MultipartFile file,
                                   Integer examId,
                                   HttpServletRequest request);

    /**
     * 后台-批量导题
     * @param questionList
     * @return
     */
    AjaxResult importQuestion(List<ExamQuestion> questionList);
}

package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.ExamPaper;
import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.entity.ExamSave;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 后台置顶/取消置顶
     * @param examId
     * @param sticky
     * @return
     */
    AjaxResult setTop(Integer examId,
                      Integer sticky);

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
     * @return
     */
    AjaxResult checkImportQuestion(MultipartFile file,
                                   Integer examId);

    /**
     * 后台-批量导题
     * @param questionList
     * @return
     */
    AjaxResult importQuestion(List<ExamQuestion> questionList);

    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    AjaxResult upload(MultipartFile file,
                      Integer userId);

    /**
     * 删除文件
     * @param fileId
     * @param type
     * @param id
     * @return
     */
    AjaxResult deleteFile(Integer fileId,
                          String type,
                          Integer id);

    /**
     * 后台统计结果
     * @param examId
     * @return
     */
    AjaxResult analyseExam(Integer examId);

    /**
     * 后台导出答题数据
     * @param examId
     * @return
     */
    AjaxResult exportPaperData(Integer examId,
                               HttpServletResponse response);

    /**
     * 后台发布情况
     * @param startTime
     * @param endTime
     * @return
     */
    AjaxResult getPublishList(String startTime,
                              String endTime);

    /**
     * 后台导出发布情况
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    AjaxResult exportPublishList(String startTime,
                                 String endTime,
                                 HttpServletResponse response);

    /**
     * 首页-查询列表
     * @param userId
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getTopExamList(Integer userId,
                              String title,
                              Integer pageNum,
                              Integer pageSize);

    /**
     * 首页-保存答题记录
     * @param examSave
     * @return
     */
    AjaxResult saveExamPaper(ExamSave examSave);

    /**
     * 首页-根据id查询考试详情
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult getTopDetail(Integer examId,
                            Integer userId);

    /**
     * 首页-交卷
     * @param paperList
     * @return
     */
    AjaxResult submitExam(List<ExamPaper> paperList);
}

package com.ruoyi.project.democratic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Vote;
import com.ruoyi.project.democratic.entity.VotePaper;
import com.ruoyi.project.democratic.entity.VoteQuestion;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 问卷表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
public interface IQuestionnaireService extends IService<Vote> {

    /**
     * 后台新增问卷
     * @param question
     * @return
     */
    AjaxResult insertQuestionnaire(Vote question);

    /**
     * 条件查询后台问卷列表
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getBackQuestionList(String title,
                                   Integer pageNum,
                                   Integer pageSize);

    /**
     * 后台置顶/取消
     * @param questionnaireId
     * @param sticky
     * @return
     */
    AjaxResult setTop(Integer questionnaireId,
                      Integer sticky);

    /**
     * 后台发布/取消
     * @param questionnaireId
     * @param userId
     * @param isPublish
     * @return
     */
    AjaxResult publishOrUn(Integer questionnaireId,
                           Integer userId,
                           boolean isPublish);

    /**
     * 后台-回收
     * @param questionnaireId
     * @param userId
     * @return
     */
    AjaxResult over(Integer questionnaireId,
                    Integer userId);

    /**
     * 后台-删除
     * @param questionnaireId
     * @param userId
     * @return
     */
    AjaxResult deleteBack(Integer questionnaireId,
                          Integer userId);

    /**
     * 后台重命名
     * @param questionnaireId
     * @param userId
     * @param title
     * @return
     */
    AjaxResult rename(Integer questionnaireId,
                      Integer userId,
                      String title);

    /**
     * 后台查详情
     * @param questionnaireId
     * @return
     */
    AjaxResult getBackDetail(Integer questionnaireId);

    /**
     * 后台修改基础信息
     * @param questionBase
     * @return
     */
    AjaxResult updateBaseData(Vote questionBase);

    /**
     * 后台编辑标题封面
     * @param questionCover
     * @return
     */
    AjaxResult updateCover(Vote questionCover);

    /**
     * 后台添加题目
     * @param question
     * @return
     */
    AjaxResult insertQuestion(VoteQuestion question);

    /**
     * 后台编辑题目
     * @param question
     * @return
     */
    AjaxResult updateQuestion(VoteQuestion question);

    /**
     * 后台删除题目/选项
     * @param questionId
     * @param optionId
     * @return
     */
    AjaxResult deleteQuestionOrOption(Integer questionId,
                                      Integer optionId);

    /**
     * 后台校验
     * @param file
     * @param questionnaireId
     * @return
     */
    AjaxResult checkImportQuestion(MultipartFile file,
                                   Integer questionnaireId);

    /**
     * 后台批量导题
     * @param questionList
     * @return
     */
    AjaxResult importQuestion(List<VoteQuestion> questionList);

    /**
     * 删除文件/图片
     * @param fileId
     * @param type
     * @param id
     * @return
     */
    AjaxResult deleteFile(Integer fileId,
                          String type,
                          Integer id);

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
     * 后台统计结果
     * @param questionnaireId
     * @return
     */
    AjaxResult analyseQuestionnaire(Integer questionnaireId);

    /**
     * 后台导出答题数据
     * @param questionnaireId
     * @param response
     * @return
     */
    AjaxResult exportPaperData(Integer questionnaireId,
                               HttpServletResponse response);

    /**
     * 后台导出统计结果
     * @param questionnaireId
     * @param response
     * @return
     */
    AjaxResult exportAnalyse(Integer questionnaireId,
                             HttpServletResponse response);

    /**
     * 后台人员情况
     * @param questionnaireId
     * @param orgId
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getMember(Integer questionnaireId,
                         Integer orgId,
                         Integer pageNum,
                         Integer pageSize);

    /**
     * 后台导出人员情况
     * @param questionnaireId
     * @param orgId
     * @param response
     * @return
     */
    AjaxResult exportMember(Integer questionnaireId,
                            Integer orgId,
                            HttpServletResponse response);

    /**
     * 首页条件查询列表
     * @param userId
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getTopQuList(Integer userId,
                            String title,
                            Integer pageNum,
                            Integer pageSize);

    /**
     * 首页查询问卷详情
     * @param questionnaireId
     * @param userId
     * @return
     */
    AjaxResult getTopDetail(Integer questionnaireId,
                            Integer userId);

    /**
     * 首页提交问卷
     * @param paperList
     * @return
     */
    AjaxResult submitQu(List<VotePaper> paperList);
}

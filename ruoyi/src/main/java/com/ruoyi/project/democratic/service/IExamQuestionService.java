package com.ruoyi.project.democratic.service;

import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考试题目表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-23
 */
public interface IExamQuestionService extends IService<ExamQuestion> {

    /**
     * 新增题目列表
     * @param questionList
     * @return
     */
    boolean insertQuestionList(List<ExamQuestion> questionList);
}

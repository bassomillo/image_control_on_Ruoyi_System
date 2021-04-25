package com.ruoyi.project.democratic.service;

import com.ruoyi.project.democratic.entity.ExamOption;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.ExamQuestion;

import java.util.List;

/**
 * <p>
 * 考试题目选项表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-23
 */
public interface IExamOptionService extends IService<ExamOption> {

    /**
     * 新增选项
     * @param questionList
     * @return
     */
    boolean insertOptions(List<ExamQuestion> questionList);
}

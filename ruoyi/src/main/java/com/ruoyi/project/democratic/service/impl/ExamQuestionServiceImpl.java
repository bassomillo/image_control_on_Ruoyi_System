package com.ruoyi.project.democratic.service.impl;

import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.mapper.ExamQuestionMapper;
import com.ruoyi.project.democratic.service.IExamQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考试题目表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-23
 */
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements IExamQuestionService {

    @Override
    public boolean insertQuestionList(List<ExamQuestion> questionList) {
        if (questionList == null || questionList.size() == 0){
            return true;
        }
        return saveBatch(questionList);
    }
}

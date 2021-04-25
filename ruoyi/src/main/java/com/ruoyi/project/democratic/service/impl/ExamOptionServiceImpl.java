package com.ruoyi.project.democratic.service.impl;

import com.ruoyi.project.democratic.entity.ExamOption;
import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.mapper.ExamOptionMapper;
import com.ruoyi.project.democratic.service.IExamOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考试题目选项表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-23
 */
@Service
public class ExamOptionServiceImpl extends ServiceImpl<ExamOptionMapper, ExamOption> implements IExamOptionService {

    @Override
    public boolean insertOptions(List<ExamQuestion> questionList) {
        try {
            for (ExamQuestion question : questionList){
                List<ExamOption> optionList = question.getOptionList();
                for (ExamOption option : optionList){
                    option.setExamQuestionId(question.getId());
                }
                saveBatch(optionList);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

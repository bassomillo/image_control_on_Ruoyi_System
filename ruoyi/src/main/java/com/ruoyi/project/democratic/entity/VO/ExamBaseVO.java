package com.ruoyi.project.democratic.entity.VO;

import com.ruoyi.project.democratic.entity.Exam;
import com.ruoyi.project.democratic.entity.ExamQuestionType;
import lombok.Data;

import java.util.List;

@Data
public class ExamBaseVO {
    //考试信息
    private Exam exam;

    //分值列表
    private List<ExamQuestionType> typeList;
}

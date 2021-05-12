package com.ruoyi.project.democratic.entity.DO;

import com.ruoyi.project.democratic.entity.ExamPaper;
import lombok.Data;

import java.util.List;

@Data
public class ExamPaperExportDO {

    private Integer userId;

    private String name;

    private String gender;

    private String sex;

    private String mobile;

    private Integer orgId;

    private String orgName;

    private String time;

    private Double score;

    private String img;

    private String employmentForm;

    private String orgNameDetail;

    private List<QuestionAndPaperDO> paperList;
}

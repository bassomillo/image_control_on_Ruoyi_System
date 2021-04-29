package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamSaveVO {

    @ApiModelProperty(value = "题目id")
    private Integer examQuestionId;

    @ApiModelProperty(value = "填写内容，单选/判断填选项id；多选答案用空格分隔；填空题填填写的内容，多个用空格分隔")
    private String content;

    @ApiModelProperty(value = "题目类型")
    private String type;
}

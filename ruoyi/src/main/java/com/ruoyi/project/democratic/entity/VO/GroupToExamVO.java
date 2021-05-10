package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GroupToExamVO {

    @ApiModelProperty(value = "类型，exam考试，questionnaire问卷，vote投票，必传")
    private String type;

    private List<Integer> groupIdList;

    private Integer eqvId;
}

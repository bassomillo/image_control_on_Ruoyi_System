package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddTreeMemberVO {

    @ApiModelProperty(value = "类型，exam考试，questionnaire问卷，vote投票，必传")
    private String type;

    @ApiModelProperty(value = "人员id列表，必传")
    private List<Integer> userIdList;

    @ApiModelProperty(value = "考试/问卷/投票id，添加到考试/问卷/投票必传")
    private Integer eqvId;

    @ApiModelProperty(value = "分组id，添加到分组时必传")
    private Integer groupId;
}

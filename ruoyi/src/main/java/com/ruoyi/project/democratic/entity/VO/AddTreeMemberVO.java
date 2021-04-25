package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddTreeMemberVO {

    @ApiModelProperty(value = "人员id列表，必传")
    private List<Integer> userIdList;

    @ApiModelProperty(value = "考试id，添加到考试必传")
    private Integer examId;

    @ApiModelProperty(value = "分组id，添加到分组时必传")
    private Integer groupId;
}

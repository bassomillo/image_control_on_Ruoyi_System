package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddTreeMemberVO {

    @ApiModelProperty(value = "人员id列表")
    private List<Integer> userIdList;

    @ApiModelProperty(value = "考试id")
    private Integer examId;
}

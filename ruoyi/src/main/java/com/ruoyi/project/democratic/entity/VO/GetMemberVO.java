package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetMemberVO {
    @ApiModelProperty(value = "选中的组织树id列表，只放父节点的id")
    private List<Integer> idList;

    @ApiModelProperty(value = "考试id")
    private Integer examId;

    @ApiModelProperty(value = "页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页面大小，默认10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "用工形式")
    private String employmentForm;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}

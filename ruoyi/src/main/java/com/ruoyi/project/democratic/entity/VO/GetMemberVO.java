package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetMemberVO {

    @ApiModelProperty(value = "类型，exam考试，questionnaire问卷，vote投票，必传")
    private String type;

    @ApiModelProperty(value = "选中的组织树id列表，只放父节点的id，必传")
    private List<Integer> idList;

    @ApiModelProperty(value = "考试/问卷/投票id，必传")
    private Integer eqvId;

    @ApiModelProperty(value = "页码，默认1，条件搜索时必传")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页面大小，默认10，条件搜索时必传")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "用工形式")
    private String employmentForm;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "分组id，添加到分组时必传")
    private Integer groupId;
}

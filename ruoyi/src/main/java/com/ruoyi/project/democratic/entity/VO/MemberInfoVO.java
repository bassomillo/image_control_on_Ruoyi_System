package com.ruoyi.project.democratic.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回组织树人员信息
 */
@Data
public class MemberInfoVO {

    @ApiModelProperty(value = "人员id")
    private Integer id;

    @ApiModelProperty(value = "真实姓名")
    private String truename;

    @ApiModelProperty(value = "用工形式")
    private String employmentForm;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "是否已被添加，1是，0否")
    private Integer isAdd;

    @ApiModelProperty(value = "性别")
    private String sex;

    private Integer orgId;

    @ApiModelProperty(value = "归属组织")
    private String orgName;

    @ApiModelProperty(value = "投票情况，1已投，2未投")
    private Integer status = 0;
}

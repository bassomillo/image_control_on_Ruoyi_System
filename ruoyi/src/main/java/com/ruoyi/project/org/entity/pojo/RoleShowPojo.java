package com.ruoyi.project.org.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleShowPojo {

    @ApiModelProperty(value = "数据库唯一标识")
    private Integer id;

    @ApiModelProperty(value = "职位名称")
    private String role;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "姓名")
    private String truename;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;
}

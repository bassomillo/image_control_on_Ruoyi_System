package com.ruoyi.project.org.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserShowPojo {

    @ApiModelProperty(value = "数据库唯一标识")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "姓名")
    private String truename;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "小头像")
    private String smallAvatar;
}

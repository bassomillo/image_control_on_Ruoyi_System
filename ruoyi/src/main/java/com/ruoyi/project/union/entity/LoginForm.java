package com.ruoyi.project.union.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginForm
{
    @ApiModelProperty(value= "用户名/手机号/邮箱")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码的uuid")
    private String uuid;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "公钥")
    private String publicKey;
}

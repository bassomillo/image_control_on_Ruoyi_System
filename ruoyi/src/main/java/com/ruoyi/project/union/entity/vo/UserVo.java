package com.ruoyi.project.union.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVo {

    @ApiModelProperty(value = "用户名 User", example = "超级管理员")
    private String nickname;

    @ApiModelProperty(value = "真实姓名 UserProfile", example = "张三")
    private String truename;

    @ApiModelProperty(value = "性别 UserProfile", example = "male-男，female-女")
    private String gender;

    @ApiModelProperty(value = "所属机构", example = "male-男，female-女")
    private String organization;

    @ApiModelProperty(value = "手机 UserProfile", example = "111")
    private String mobile;
}

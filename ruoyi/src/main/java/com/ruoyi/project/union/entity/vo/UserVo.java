package com.ruoyi.project.union.entity.vo;

import com.ruoyi.project.system.domain.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户名 User", example = "超级管理员")
    private String nickname;

    @ApiModelProperty(value = "真实姓名 UserProfile", example = "张三")
    private String truename;

    @ApiModelProperty(value = "性别 UserProfile", example = "male-男，female-女")
    private String gender;

    @ApiModelProperty(value = "所属机构id")
    private Integer orgId;

    @ApiModelProperty(value = "所属机构")
    private String organization;

    @ApiModelProperty(value = "手机 UserProfile", example = "111")
    private String mobile;

    @ApiModelProperty(value = "禁用状态 1-禁用 0-未禁用", example = "0")
    private Integer locked;

    @ApiModelProperty(value = "角色", example = "0")
    private List<SysRole> role;
}

package com.ruoyi.project.union.entity.vo;

import com.ruoyi.project.system.domain.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleVo {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private List<Long> roles;
}

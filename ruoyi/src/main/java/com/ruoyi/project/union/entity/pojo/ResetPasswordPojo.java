package com.ruoyi.project.union.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
public class ResetPasswordPojo {

    @ApiModelProperty(value = "当前操作管理员id")
    private Integer manageUserId;

    @ApiModelProperty(value = "当前操作管理员密码")
    private String password;

    @ApiModelProperty(value = "要重置密码的用户id")
    private Integer resetUserId;

    @ApiModelProperty(value = "重置的密码")
    private String resetPassword;
}

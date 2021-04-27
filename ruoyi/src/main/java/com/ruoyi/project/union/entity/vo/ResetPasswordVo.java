package com.ruoyi.project.union.entity.vo;

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
public class ResetPasswordVo {

    @ApiModelProperty(value = "当前操作管理员id，必填")
    private Integer manageUserId;

    @ApiModelProperty(value = "当前操作管理员密码，必填")
    private String password;

    @ApiModelProperty(value = "公钥，必填")
    private String publicKey;

    @ApiModelProperty(value = "要重置密码的用户id，必填")
    private Integer resetUserId;

    @ApiModelProperty(value = "重置的密码")
    private String resetPassword;
}

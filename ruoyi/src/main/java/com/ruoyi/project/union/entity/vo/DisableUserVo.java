package com.ruoyi.project.union.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
public class DisableUserVo {

    @ApiModelProperty(value = "当前操作管理员id，必填")
    private Integer manageUserId;

    @ApiModelProperty(value = "当前操作管理员密码，必填")
    private String password;

    @ApiModelProperty(value = "公钥，必填")
    private String publicKey;

    @ApiModelProperty(value = "要禁用的用户id，必填")
    private Integer disableUserId;

    @ApiModelProperty(value = "禁止期限，不传或空为无限期禁用", example = "yyyy-MM-dd")
    private LocalDate lockDeadline;
}

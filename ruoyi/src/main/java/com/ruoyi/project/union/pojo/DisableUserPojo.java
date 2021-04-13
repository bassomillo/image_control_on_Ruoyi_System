package com.ruoyi.project.union.pojo;

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
public class DisableUserPojo {

    @ApiModelProperty(value = "当前操作管理员id")
    private Integer userId;

    @ApiModelProperty(value = "当前操作管理员密码")
    private String password;

    @ApiModelProperty(value = "要禁用的用户id")
    private Integer disableUserId;

    @ApiModelProperty(value = "禁止期限，不传或空为无限期禁用", example = "yyyy-MM-dd")
    private LocalDate lockDeadline;
}

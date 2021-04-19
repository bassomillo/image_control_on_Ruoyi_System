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
public class AccountSearchVo {

    @ApiModelProperty(value = "第几页", example = "1")
    private Integer current;

    @ApiModelProperty(value = "每页数据量", example = "10")
    private Integer size;

    @ApiModelProperty(value = "机构id", example = "1")
    private Integer orgId;

    @ApiModelProperty(value = "用户名 user", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "姓名 user_profile", example = "张三")
    private String truename;

    @ApiModelProperty(value = "角色id", example = "1")
    private Integer roleId;


}

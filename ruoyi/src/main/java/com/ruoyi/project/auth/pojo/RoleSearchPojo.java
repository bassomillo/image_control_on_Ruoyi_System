package com.ruoyi.project.auth.pojo;

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
public class RoleSearchPojo {

    @ApiModelProperty(value = "第几页", example = "1")
    private Integer current;

    @ApiModelProperty(value = "每页数据量", example = "10")
    private Integer size;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色编码")
    private String code;
}

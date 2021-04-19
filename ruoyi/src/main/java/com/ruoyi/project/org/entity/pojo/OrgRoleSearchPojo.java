package com.ruoyi.project.org.entity.pojo;

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
public class OrgRoleSearchPojo {

    @ApiModelProperty(value = "第几页", example = "1")
    private Integer current;

    @ApiModelProperty(value = "每页数据量", example = "10")
    private Integer size;

    @ApiModelProperty(value = "机构id")
    private Integer orgId;
}

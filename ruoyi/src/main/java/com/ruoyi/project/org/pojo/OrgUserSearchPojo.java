package com.ruoyi.project.org.pojo;

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
public class OrgUserSearchPojo {

    @ApiModelProperty(value = "第几页")
    private Integer current;

    @ApiModelProperty(value = "每页数据量")
    private Integer size;

    @ApiModelProperty(value = "查询内容")
    private String msg;
}

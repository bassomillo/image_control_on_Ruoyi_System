package com.ruoyi.project.unionhelp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchUser {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "第几页")
    private Integer index;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;
}

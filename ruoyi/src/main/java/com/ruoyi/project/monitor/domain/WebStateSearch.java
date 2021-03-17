package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebStateSearch {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

}

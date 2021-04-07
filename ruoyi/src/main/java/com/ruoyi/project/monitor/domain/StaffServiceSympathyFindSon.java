package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffServiceSympathyFindSon {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织ID")
    private int id;

    @ApiModelProperty(value = "父组织链")
    private String orgCode;
}

package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class WebStateManage {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private int id;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "发布人")
    private String workRange;

    @ApiModelProperty(value = "公告内容")
    private  String statement;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private Date publishTime;

    @ApiModelProperty(value = "结束时间")
    private Date updateTime;
}

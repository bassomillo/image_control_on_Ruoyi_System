package com.ruoyi.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WebStateManageRequire {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private int id;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "发布人")
    private List<String> workRangeRequire;

    @ApiModelProperty(value = "公告内容")
    private  String statement;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

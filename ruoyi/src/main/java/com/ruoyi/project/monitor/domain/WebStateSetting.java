package com.ruoyi.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WebStateSetting {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private int id;

    @ApiModelProperty(value = "公告内容")
    private String webStatement;

    @ApiModelProperty(value = "发布人")
    private String publishedPeople;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    @ApiModelProperty(value = "公告数量")
    private int count;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;

}

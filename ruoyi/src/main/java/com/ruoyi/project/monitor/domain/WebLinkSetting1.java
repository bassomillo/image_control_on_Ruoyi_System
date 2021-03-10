package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WebLinkSetting1 {
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "第一列链接文本")
    private String firstColumnText;

    @ApiModelProperty(value = "第一列链接地址")
    private String firstColumnAddress;

    @ApiModelProperty(value = "第一列链接是否开启新窗口")
    private String firstColumnStatus;

     @ApiModelProperty(value = "程序运行时间点")
    private Date time;
}

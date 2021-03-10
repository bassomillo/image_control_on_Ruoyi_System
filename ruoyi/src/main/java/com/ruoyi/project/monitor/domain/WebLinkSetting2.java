package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class WebLinkSetting2 {
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "第二列链接文本")
    private String secondColumnText;

    @ApiModelProperty(value = "第二列链接地址")
    private String secondColumnAddress;

    @ApiModelProperty(value = "第二列链接是否开启新窗口")
    private String secondColumnStatus;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;
}

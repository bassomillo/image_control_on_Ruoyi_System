package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class WebLinkSetting3 {
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "第三列链接文本")
    private String thirdColumnText;

    @ApiModelProperty(value = "第三列链接地址")
    private String thirdColumnAddress;

    @ApiModelProperty(value = "第三列链接是否开启新窗口")
    private String thirdColumnStatus;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;
}

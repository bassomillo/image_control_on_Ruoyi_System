package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysCarouselMap {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "海报id")
    private Integer posterId;

    @ApiModelProperty(value = "展示模式，0-平铺显示，1-居中显示")
    private Boolean displayMode;

    @ApiModelProperty(value = "开关，0-关，1-开")
    private Boolean onOff;

    @ApiModelProperty(value = "照片地址")
    private String picUrl;

    @ApiModelProperty(value = "背景颜色")
    private String backColor;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "照片描述")
    private String picDesc;

}

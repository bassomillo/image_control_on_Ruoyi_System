package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysAdvertisement {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告id")
    private Integer advId;

    @ApiModelProperty(value = "照片地址")
    private String picUrl;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "是否开启 0-关，1-开")
    private Boolean newWindow;
}

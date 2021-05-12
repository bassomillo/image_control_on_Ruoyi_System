package com.ruoyi.project.monitor.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebTagNamesVO {
    @ApiModelProperty("标签id")
    private Integer id;

    @ApiModelProperty("标签名称")
    private String name;
}

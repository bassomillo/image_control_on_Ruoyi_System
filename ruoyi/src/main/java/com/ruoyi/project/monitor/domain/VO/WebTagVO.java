package com.ruoyi.project.monitor.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WebTagVO {
    @ApiModelProperty("标签id")
    private Integer id;

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("标签创建时间")
    private Date createdTime;

    @ApiModelProperty("组织机构id")
    private Integer orgId;

    @ApiModelProperty("组织机构内部编码")
    private String orgCode;
}

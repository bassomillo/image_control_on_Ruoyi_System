package com.ruoyi.project.monitor.domain.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebTagVO2 {
    @ApiModelProperty("标签id")
    private Integer id;

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("标签创建时间")
    private Integer createdTime;

    @ApiModelProperty("标签组名称")
    private String groupName;

    @ApiModelProperty("组织机构id")
    private Integer orgId;

    @ApiModelProperty("组织机构内部编码")
    private String orgCode;
}

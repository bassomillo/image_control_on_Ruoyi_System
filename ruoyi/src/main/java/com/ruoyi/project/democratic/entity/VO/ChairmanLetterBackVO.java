package com.ruoyi.project.democratic.entity.VO;

import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChairmanLetterBackVO extends ChairmanLetterBox {
    @ApiModelProperty(value = "发送者")
    private String userName;

    @ApiModelProperty(value = "发送者id")
    private Integer userId;

    @ApiModelProperty(value = "所属机构")
    private String orgName;

    @ApiModelProperty(value = "机构id")
    private Integer orgId;
}

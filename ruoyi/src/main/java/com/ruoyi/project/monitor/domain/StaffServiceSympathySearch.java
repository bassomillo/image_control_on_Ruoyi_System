package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffServiceSympathySearch {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "慰问单位")
    private int expenseOrgId;

    @ApiModelProperty(value = "填报单位")
    private int orgId;

    @ApiModelProperty(value = "提交人的所在单位，这个字段必填")
    private int createdUserOrg;

    @ApiModelProperty(value = "提交状态")
    private Integer submitStatus;

    @ApiModelProperty(value = "选本人创建填0,选下级创建填1,不选不填")
    private Integer status;

    @ApiModelProperty(value = "慰问形式，填节假日慰问记录或者生日慰问记录")
    private String type;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "第几页")
    private Integer index;
}

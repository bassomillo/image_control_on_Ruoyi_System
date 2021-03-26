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

    @ApiModelProperty(value = "提交人的所在单位")
    private int createdUserOrg;

    @ApiModelProperty(value = "提交状态")
    private int submitStatus;
}

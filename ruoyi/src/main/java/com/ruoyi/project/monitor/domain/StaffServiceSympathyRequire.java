package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StaffServiceSympathyRequire {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "慰问ID")
    private int id;

    @ApiModelProperty(value = "创建人ID")
    private int createdUserId;

    @ApiModelProperty(value = "填报单位名称")
    private int orgId;

    @ApiModelProperty(value = "慰问单位名称")
    private int expenseOrgId;

    @ApiModelProperty(value = "慰问类型")
    private String type;

    @ApiModelProperty(value = "慰问形式")
    private List<String> sympathyTypeList;

    @ApiModelProperty(value = "慰问时间")
    private Date sympathyTime;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "提交状态，0为未提交，1为已提交")
    private Integer submitStatus;

    @ApiModelProperty(value = "资金来源")
    private List<String> fundsSourcesList;

    @ApiModelProperty(value = "慰问人数")
    private int sympathyNumber;

    @ApiModelProperty(value = "覆盖人群")
    private List<String> coverTypeList;

    @ApiModelProperty(value = "慰问费用")
    private float sympathyCost;

    @ApiModelProperty(value = "补充说明")
    private String remark;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}

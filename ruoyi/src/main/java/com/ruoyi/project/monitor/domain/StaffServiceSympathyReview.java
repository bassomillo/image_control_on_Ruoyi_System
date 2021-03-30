package com.ruoyi.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StaffServiceSympathyReview {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "慰问ID")
    private int id;

    @ApiModelProperty(value = "创建人ID")
    private int createdUserId;

    @ApiModelProperty(value = "填报单位名称")
    private String orgName;

    @ApiModelProperty(value = "慰问单位名称")
    private String expenseOrgName;

    @ApiModelProperty(value = "慰问类型")
    private String type;

    @ApiModelProperty(value = "慰问形式")
    private List<String> sympathyTypeList;

    @ApiModelProperty(value = "慰问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sympathyTime;

    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;

    @ApiModelProperty(value = "提交状态，0为未提交，1为已提交")
    private int submitStatus;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;
}

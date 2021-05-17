package com.ruoyi.project.activityreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityReportRequire {
    private static final long serialVersionUID=1L;



    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 活动详情id
     */

    @ApiModelProperty(value = "活动详情id")
    private Integer itemId;

    /**
     * 开始时间
     */

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */

    @ApiModelProperty(value = "结束时间")
    private Date endTime;


    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String mobile;

    /**
     * 所属工会小组
     */

    @ApiModelProperty(value = "所属工会小组")
    private String unitGroup;

    /**
     * 创建用户id
     */

    @ApiModelProperty(value = "创建用户id")
    private Integer createdUserId;

    /**
     * 预算
     */
    @ApiModelProperty(value = "预算")
    private Float budget;

    /**
     * 所属机构
     */

    @ApiModelProperty(value = "所属机构")
    private Integer orgId;

    /**
     * 审核状态
     */

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    /**
     * 审核理由
     */

    @ApiModelProperty(value = "审核理由")
    private String auditReason;

}

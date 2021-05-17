package com.ruoyi.project.activityreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础活动
 * </p>
 *
 * @author crl
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActivityReport extends Model<ActivityReport> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

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
    @TableField("itemId")
    @ApiModelProperty(value = "活动详情id")
    private Integer itemId;

    /**
     * 开始时间
     */
    @TableField("startTime")
    @ApiModelProperty(value = "开始时间")
    private Integer startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    @ApiModelProperty(value = "结束时间")
    private Integer endTime;

    /**
     * 创建用户
     */
    @TableField("createUser")
    @ApiModelProperty(value = "创建用户")
    private String createUser;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String mobile;

    /**
     * 所属工会小组
     */
    @TableField("unitGroup")
    @ApiModelProperty(value = "所属工会小组")
    private String unitGroup;

    /**
     * 创建用户id
     */
    @TableField("createdUserId")
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
    @TableField("orgId")
    @ApiModelProperty(value = "所属机构")
    private Integer orgId;

    /**
     * 审核状态
     */
    @TableField("auditStatus")
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    /**
     * 审核理由
     */
    @TableField("auditReason")
    @ApiModelProperty(value = "审核理由")
    private String auditReason;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间")
    private Integer createdTime;

    /**
     * 更新时间
     */
    @TableField("updatedTime")
    @ApiModelProperty(value = "更新时间")
    private Integer updatedTime;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String ITEMID = "itemId";

    public static final String STARTTIME = "startTime";

    public static final String ENDTIME = "endTime";

    public static final String CREATEUSER = "createUser";

    public static final String MOBILE = "mobile";

    public static final String UNITGROUP = "unitGroup";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String BUDGET = "budget";

    public static final String ORGID = "orgId";

    public static final String AUDITSTATUS = "auditStatus";

    public static final String AUDITREASON = "auditReason";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

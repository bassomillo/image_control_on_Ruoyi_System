package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 考试基本信息表
 * </p>
 *
 * @author cxr
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Exam extends Model<Exam> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "考试id")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题，新增必填")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述，传输时记得转义引号，否则接收不到")
    private String description;

    /**
     * 封面地址
     */
    @TableField("coverUrl")
    @ApiModelProperty(value = "封面地址")
    private String coverUrl;

    /**
     * 封面id
     */
    @TableField("coverId")
    @ApiModelProperty(value = "封面id，有封面上传时必填")
    private Integer coverId;

    /**
     * 置顶1，0不置顶
     */
    @ApiModelProperty(value = "是否置顶，1置顶，0不置顶")
    private Integer sticky;

    /**
     * 状态，published发布，unpublished未发布，over已作废
     */
    @ApiModelProperty(value = "状态，published发布，unpublished未发布，over已作废")
    private String status;

    /**
     * 创建用户id
     */
    @TableField("createUserId")
    @ApiModelProperty(value = "创建用户id，填当前登录人id，新增时必填，其余时不填")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 更新用户id
     */
    @TableField("updateUserId")
    @ApiModelProperty(value = "更新用户id，填当前登录人id，更新时必填")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    /**
     * 入场开始时间
     */
    @TableField("entranceStartDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "入场开始时间")
    private Date entranceStartDate;

    /**
     * 入场结束时间
     */
    @TableField("entranceEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "入场结束时间")
    private Date entranceEndDate;

    /**
     * 考试时长
     */
    @ApiModelProperty(value = "考试时长")
    private Integer duration = 0;

    /**
     * 最大提交数
     */
    @TableField("maxSubmit")
    @ApiModelProperty(value = "最大提交数，默认为1")
    private Integer maxSubmit;

    /**
     * 专项标记，activity活动，default非活动
     */
    @ApiModelProperty(value = "类型，activity活动，default非活动，新增必填")
    private String mark;

    /**
     * 是否签名
     */
    @TableField("isSigned")
    @ApiModelProperty(value = "是否需要签名照，1是，0否")
    private Integer isSigned;

    /**
     * 是否展示（用作判断是否被删除），1是，0否
     */
    @TableField("isShow")
    @ApiModelProperty(value = "是否展示（用作判断是否被删除），1是，0否")
    private Integer isShow;

    @TableField(exist = false)
    @ApiModelProperty(value = "首页状态，未作答/已作答/已结束")
    private String newStatus;

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String COVERURL = "coverUrl";

    public static final String COVERID = "coverId";

    public static final String STICKY = "sticky";

    public static final String STATUS = "status";

    public static final String CREATEUSERID = "createUserId";

    public static final String CREATEDATE = "createDate";

    public static final String UPDATEUSERID = "updateUserId";

    public static final String UPDATEDATE = "updateDate";

    public static final String ENTRANCESTARTDATE = "entranceStartDate";

    public static final String ENTRANCEENDDATE = "entranceEndDate";

    public static final String DURATION = "duration";

    public static final String MAXSUBMIT = "maxSubmit";

    public static final String MARK = "mark";

    public static final String ISSIGNED = "isSigned";

    public static final String ISSHOW = "isShow";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

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
 * 问卷/投票表
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Vote extends Model<Vote> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "投票/问卷id，有必填")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
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
     * 封面地址id
     */
    @TableField("coverUrlId")
    @ApiModelProperty(value = "封面地址id")
    private Integer coverUrlId;

    /**
     * 置顶1，不置顶0
     */
    @ApiModelProperty(value = "置顶1，不置顶0")
    private Integer sticky;

    /**
     * 状态，over回收，published发布，unpublished未发布
     */
    @ApiModelProperty(value = "状态，over回收，published发布，unpublished未发布")
    private String status;

    /**
     * 创建用户id
     */
    @TableField("createUserId")
    @ApiModelProperty(value = "创建用户id，创建时必填")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createDate")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 更新用户id
     */
    @TableField("updateUserId")
    @ApiModelProperty(value = "更新用户id，更新时必填")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateDate")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 开始时间
     */
    @TableField("startTime")
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 最大提交数
     */
    @TableField("maxSubmit")
    @ApiModelProperty(value = "最大提交数")
    private Integer maxSubmit;

    /**
     * 类型，vote投票，questionnaire问卷
     */
    @ApiModelProperty(value = "类型，vote投票，questionnaire问卷")
    private String type;

    /**
     * 专项标记，default非活动，activity活动
     */
    @ApiModelProperty(value = "专项标记，default非活动，activity活动")
    private String mark;

    /**
     * 是否签名，1是，0否
     */
    @TableField("isSigned")
    @ApiModelProperty(value = "是否签名，1是，0否")
    private Integer isSigned;

    /**
     * 删除状态，1未删，0已删
     */
    @TableField("isShow")
    @ApiModelProperty(value = "删除状态，1未删，0已删", hidden = true)
    private Integer isShow;

    @TableField(exist = false)
    @ApiModelProperty(value = "首页状态，未作答/已作答/已结束")
    private String newStatus;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String COVERURL = "coverUrl";

    public static final String COVERURLID = "coverUrlId";

    public static final String STICKY = "sticky";

    public static final String STATUS = "status";

    public static final String CREATEUSERID = "createUserId";

    public static final String CREATEDATE = "createDate";

    public static final String UPDATEUSERID = "updateUserId";

    public static final String UPDATEDATE = "updateDate";

    public static final String STARTTIME = "startTime";

    public static final String ENDTIME = "endTime";

    public static final String MAXSUBMIT = "maxSubmit";

    public static final String TYPE = "type";

    public static final String MARK = "mark";

    public static final String ISSIGNED = "isSigned";

    public static final String ISSHOW = "isShow";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

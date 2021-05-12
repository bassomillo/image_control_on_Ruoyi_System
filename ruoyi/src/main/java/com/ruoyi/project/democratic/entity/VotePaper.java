package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * 投票/问卷提交表
 * </p>
 *
 * @author cxr
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VotePaper extends Model<VotePaper> {

    private static final long serialVersionUID=1L;

    /**
     * 问卷/投票填写id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "问卷/投票填写id")
    private Integer id;

    /**
     * 问卷/投票id
     */
    @TableField("voteId")
    @ApiModelProperty(value = "问卷/投票id")
    private Integer voteId;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String tel;

    /**
     * 提交时间
     */
    @TableField("submitTime")
    @ApiModelProperty(value = "提交时间", hidden = true)
    private Integer submitTime;

    /**
     * 提交时间
     */
    @TableField("submitDate")
    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitDate;

    /**
     * 提交次数标记：当前是第几次提交
     */
    @TableField("submitFlag")
    @ApiModelProperty(value = "提交次数标记：当前是第几次提交")
    private Integer submitFlag;

    /**
     * 投票问题id
     */
    @TableField("voteQuestionId")
    @ApiModelProperty(value = "投票问题id")
    private Integer voteQuestionId;

    /**
     * 题型
     */
    @ApiModelProperty(value = "题型")
    private String type;

    /**
     * 提交内容
     */
    @TableField("submitContent")
    @ApiModelProperty(value = "提交内容")
    private String submitContent;

    /**
     * 签名照
     */
    @TableField("signatureImg")
    @ApiModelProperty(value = "签名照")
    private String signatureImg;

    /**
     * 签名照id
     */
    @TableField("signatureImgId")
    @ApiModelProperty(value = "签名照id")
    private Integer signatureImgId;


    public static final String ID = "id";

    public static final String VOTEID = "voteId";

    public static final String USERID = "userId";

    public static final String TEL = "tel";

    public static final String SUBMITTIME = "submitTime";

    public static final String SUBMITDATE = "submitDate";

    public static final String SUBMITFLAG = "submitFlag";

    public static final String VOTEQUESTIONID = "voteQuestionId";

    public static final String TYPE = "type";

    public static final String SUBMITCONTENT = "submitContent";

    public static final String SIGNATUREIMG = "signatureImg";

    public static final String SIGNATUREIMGID = "signatureImgId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

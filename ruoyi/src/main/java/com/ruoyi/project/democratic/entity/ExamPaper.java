package com.ruoyi.project.democratic.entity;

import java.math.BigDecimal;
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
 * 考试提交表
 * </p>
 *
 * @author cxr
 * @since 2021-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamPaper extends Model<ExamPaper> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "试卷答题id")
    private Integer id;

    /**
     * 考试id
     */
    @TableField("examId")
    @ApiModelProperty(value = "考试id，必填")
    private Integer examId;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id，必填")
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
     * 问题id
     */
    @TableField("examQuestionId")
    @ApiModelProperty(value = "题目id，必填")
    private Integer examQuestionId;

    /**
     * 题型
     */
    @ApiModelProperty(value = "题型，必填")
    private String type;

    /**
     * 提交内容
     */
    @TableField("submitContent")
    @ApiModelProperty(value = "提交内容，必填")
    private String submitContent;

    /**
     * 得分
     */
    @ApiModelProperty(value = "得分")
    private BigDecimal score;

    /**
     * 签名照id
     */
    @TableField("signatureImg")
    @ApiModelProperty(value = "签名照url，有上传照片必传")
    private String signatureImg;

    @TableField("signatureImgId")
    @ApiModelProperty(value = "签名照id，有上传照片必传")
    private String signatureImgId;


    public static final String ID = "id";

    public static final String EXAMID = "examId";

    public static final String USERID = "userId";

    public static final String TEL = "tel";

    public static final String SUBMITTIME = "submitTime";

    public static final String SUBMITDATE = "submitDate";

    public static final String SUBMITFLAG = "submitFlag";

    public static final String EXAMQUESTIONID = "examQuestionId";

    public static final String TYPE = "type";

    public static final String SUBMITCONTENT = "submitContent";

    public static final String SCORE = "score";

    public static final String SIGNATUREIMG = "signatureImg";

    public static final String SIGNATUREIMGID = "signatureImgId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

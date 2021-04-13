package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 总经理信箱
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerLetterBox extends Model<ManagerLetterBox> {

    private static final long serialVersionUID=1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录编号，不必填写")
    private Integer id;

    /**
     * 发送用户id
     */
    @TableField("fromId")
    @ApiModelProperty(value = "发送者id，一定要填")
    private Integer fromId;

    @TableField(exist = false)
    @ApiModelProperty(value = "发送者名字")
    private String fromName;

    /**
     * 发送对象
     */
    @ApiModelProperty(value = "发送对象，省总经理/市总经理，必填")
    private String object;

    /**
     * 信息
     */
    @ApiModelProperty(value = "写信内容")
    private String content;

    /**
     * 是否已回复
     */
    @TableField("isReply")
    @ApiModelProperty(value = "是否已回复，1是，0否，不必填")
    private Integer isReply;

    /**
     * 回复id
     */
    @TableField("parentId")
    @ApiModelProperty(value = "回复人id，不必填")
    private Integer parentId;

    /**
     * 是否显示
     */
    @TableField("isShow")
    @ApiModelProperty(value = "是否显示，1是，0否，不必填")
    private Integer isShow;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(hidden = true)
    private Integer createdTime;

    @TableField("imgUrl")
    @ApiModelProperty(value = "发送图片地址，不必填")
    private String imgUrl;

    @TableField("toId")
    @ApiModelProperty(value = "接收人id，必填")
    private Integer toId;

    /**
     * 是否已读
     */
    @TableField("isRead")
    @ApiModelProperty(value = "是否已读，1是，0否，不必填")
    private Integer isRead;

    /**
     * 评价
     */
    @ApiModelProperty(value = "评价，complete满意，fail不满意，评价时填")
    private String evaluate;

    /**
     * 评价内容
     */
    @TableField("evaluateContent")
    @ApiModelProperty(value = "评价内容，评价时填")
    private String evaluateContent;

    /**
     * 创建日期
     */
    @TableField("createDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期，不必填")
    private Date createDate;

    @TableField("realReply")
    @ApiModelProperty(value = "真实回复人id，回复时必填")
    private Integer realReply;

    @TableField(exist = false)
    @ApiModelProperty(value = "上传的附件id，如有上传附件必填")
    private List<Integer> fileList;

    public static final String ID = "id";

    public static final String FROMID = "fromId";

    public static final String OBJECT = "object";

    public static final String CONTENT = "content";

    public static final String ISREPLY = "isReply";

    public static final String PARENTID = "parentId";

    public static final String ISSHOW = "isShow";

    public static final String CREATEDTIME = "createdTime";

    public static final String IMGURL = "imgUrl";

    public static final String TOID = "toId";

    public static final String ISREAD = "isRead";

    public static final String EVALUATE = "evaluate";

    public static final String EVALUATECONTENT = "evaluateContent";

    public static final String CREATEDATE = "createDate";

    public static final String REALREPLY = "realReply";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

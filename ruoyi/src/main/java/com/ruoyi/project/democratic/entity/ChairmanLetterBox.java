package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主席信箱表
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChairmanLetterBox extends Model<ChairmanLetterBox> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "信件id，不必传")
    private Integer id;

    /**
     * 发送用户id
     */
    @TableField("fromId")
    @ApiModelProperty(value = "发送人id，必传")
    private Integer fromId;

    /**
     * 发送内容
     */
    @ApiModelProperty(value = "发送内容，必传")
    private String content;

    /**
     * 发送图片地址
     */
    @TableField("imgUrl")
    @ApiModelProperty(hidden = true)
    private String imgUrl;

    /**
     * 接收用户id
     */
    @TableField("toId")
    @ApiModelProperty(value = "接收人id，必传")
    private Integer toId;

    /**
     * 接收用户职位
     */
    @ApiModelProperty(value = "接收人职位，必填，按照实际情况填省主席/市主席")
    private String receiver;

    /**
     * 是否已读
     */
    @TableField("isRead")
    @ApiModelProperty(value = "是否已读，不必填，1已读，0未读")
    private Integer isRead;

    /**
     * 是否已回复
     */
    @TableField("isReply")
    @ApiModelProperty(value = "是否回复，不必填，1回复，0未回")
    private Integer isReply;

    /**
     * 发送时间
     */
    @TableField("createdTime")
    @ApiModelProperty(hidden = true)
    private Integer createdTime;

    /**
     * 回复信息id号
     */
    @TableField("parentId")
    @ApiModelProperty(value = "回复信息时，被回复信件id，不必填")
    private Integer parentId;

    /**
     * 评价
     */
    @ApiModelProperty(value = "评价，评价时必填，complete满意，fail不满意")
    private String evaluate;

    /**
     * 评价内容
     */
    @TableField("evaluateContent")
    @ApiModelProperty(value = "评价内容，评价时必填")
    private String evaluateContent;

    /**
     * 创建时间
     */
    @TableField("createDate")
    @ApiModelProperty(value = "创建时间，不必填")
    private Date createDate;

    /**
     * 真实回复人
     */
    @TableField("realReply")
    @ApiModelProperty(hidden = true)
    private String realReply;

    @TableField(exist = false)
    @ApiModelProperty(value = "上传的附件id，如有上传附件必填")
    private List<Integer> fileList;


    public static final String ID = "id";

    public static final String FROMID = "fromId";

    public static final String CONTENT = "content";

    public static final String IMGURL = "imgUrl";

    public static final String TOID = "toId";

    public static final String RECEIVER = "receiver";

    public static final String ISREAD = "isRead";

    public static final String ISREPLY = "isReply";

    public static final String CREATEDTIME = "createdTime";

    public static final String PARENTID = "parentId";

    public static final String EVALUATE = "evaluate";

    public static final String EVALUATECONTENT = "evaluateContent";

    public static final String CREATEDATE = "createDate";

    public static final String REALREPLY = "realReply";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

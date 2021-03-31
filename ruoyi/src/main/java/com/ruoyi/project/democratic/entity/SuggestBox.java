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
 * 建言献策实体类
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SuggestBox extends Model<SuggestBox> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 发送者id
     */
    @TableField("createUserId")
    @ApiModelProperty(value = "发送者id")
    private Integer createUserId;

    private String object;

    /**
     * 建议类型
     */
    @ApiModelProperty(value = "建议类型")
    private String type;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String content;

    /**
     * 发送图片地址
     */
    @TableField("imgUrl")
    @ApiModelProperty(value = "发送图片地址")
    private String imgUrl;

    /**
     * 回复状态，1已回，0未回
     */
    @ApiModelProperty(value = "回复状态，1已回，0未回")
    private Integer replied;

    /**
     * 回复id
     */
    @TableField("parentId")
    @ApiModelProperty(value = "回复人id")
    private Integer parentId;

    /**
     * 是否显示，1是，0否
     */
    @TableField("isShow")
    @ApiModelProperty(value = "是否显示，1是，0否")
    private Integer isShow;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Integer createdTime;

    /**
     * 是否已读
     */
    @TableField("isRead")
    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

    /**
     * 创建日期，datetime类型
     */
    @TableField("createDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date createDate;


    public static final String ID = "id";

    public static final String CREATEUSERID = "createUserId";

    public static final String OBJECT = "object";

    public static final String TYPE = "type";

    public static final String CONTENT = "content";

    public static final String IMGURL = "imgUrl";

    public static final String REPLIED = "replied";

    public static final String PARENTID = "parentId";

    public static final String ISSHOW = "isShow";

    public static final String CREATEDTIME = "createdTime";

    public static final String ISREAD = "isRead";

    public static final String CREATEDATE = "createDate";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

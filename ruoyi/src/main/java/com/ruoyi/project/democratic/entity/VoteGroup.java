package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 投票人员分组表
 * </p>
 *
 * @author cxr
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VoteGroup extends Model<VoteGroup> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "分组id")
    private Integer id;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String name;

    /**
     * 分组人员id号
     */
    @TableField("userIds")
    @ApiModelProperty(value = "分组人员id号")
    private String userIds;

    /**
     * 创建用户id
     */
    @TableField("createUserId")
    @ApiModelProperty(value = "创建用户id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Integer createdTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员列表")
    private List<VoteGroupMember> userList;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String USERIDS = "userIds";

    public static final String CREATEUSERID = "createUserId";

    public static final String CREATEDTIME = "createdTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

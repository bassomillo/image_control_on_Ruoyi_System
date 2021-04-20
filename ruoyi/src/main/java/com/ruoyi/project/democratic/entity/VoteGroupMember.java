package com.ruoyi.project.democratic.entity;

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
 * 
 * </p>
 *
 * @author cxr
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VoteGroupMember extends Model<VoteGroupMember> {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "分组成员编号")
    private Integer id;

    /**
     * 分组编号
     */
    @TableField("groupId")
    @ApiModelProperty(value = "分组编号")
    private Integer groupId;

    /**
     * 成员编号
     */
    @TableField("userId")
    @ApiModelProperty(value = "成员编号")
    private Integer userId;

    @TableField(exist = false)
    @ApiModelProperty(value = "成员名字")
    private String truename;

    public static final String ID = "id";

    public static final String GROUPID = "groupId";

    public static final String USERID = "userId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

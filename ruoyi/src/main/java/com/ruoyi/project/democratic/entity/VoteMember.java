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
 * 投票/问卷成员表
 * </p>
 *
 * @author cxr
 * @since 2021-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VoteMember extends Model<VoteMember> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "成员id号")
    private Integer id;

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
     * 问卷/投票id
     */
    @TableField("voteId")
    @ApiModelProperty(value = "问卷/投票id")
    private Integer voteId;

    @ApiModelProperty(value = "类型，questionnaire问卷，vote投票")
    private String type;


    public static final String ID = "id";

    public static final String USERID = "userId";

    public static final String TEL = "tel";

    public static final String VOTEID = "voteId";

    public static final String TYPE = "type";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

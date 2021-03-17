package com.ruoyi.project.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role extends Model<Role> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 权限配置
     */
    private String data;

    /**
     * 是否系统初始化
     */
    private Integer setup;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Integer createdTime;

    /**
     * 创建用户ID
     */
    @TableField("createdUserId")
    private Integer createdUserId;

    /**
     * 更新时间
     */
    @TableField("updatedTime")
    private Integer updatedTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色-创建时间-用于前端显示", example = "")
    private String createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色-创建者-用于前端显示", example = "")
    private String createName;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色-权限一览-用于前端显示", example = "")
    private List<String> menuList;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String DATA = "data";

    public static final String SETUP = "setup";

    public static final String CREATEDTIME = "createdTime";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String UPDATEDTIME = "updatedTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

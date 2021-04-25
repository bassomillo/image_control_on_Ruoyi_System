package com.ruoyi.project.org.entity;

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
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrgCommissioner extends Model<OrgCommissioner> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("orgId")
    @ApiModelProperty(value = "组织机构id，必填")
    private Integer orgId;

    @TableField("userId")
    @ApiModelProperty(value = "用户id（机构委员），必填")
    private Integer userId;

    @ApiModelProperty(value = "机构职位，必填")
    private String position;

    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间")
    private Integer createdTime;

    @TableField("updatedTime")
    @ApiModelProperty(value = "最后更新时间")
    private Integer updatedTime;


    public static final String ID = "id";

    public static final String ORGID = "orgId";

    public static final String USERID = "userId";

    public static final String POSITION = "position";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

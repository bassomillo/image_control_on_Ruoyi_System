package com.ruoyi.project.monitor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebTag extends Model<WebTag> {

    private static final long serialVersionUID=1L;

    /**
     * 标签ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "标签ID")
    private Integer id;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String name;

    /**
     * 标签创建时间
     */
    @ApiModelProperty(value = "标签创建时间")
    @TableField("createdTime")
    private Integer createdTime;

    /**
     * 组织机构id
     */
    @ApiModelProperty(value = "组织机构id")
    @TableField("orgId")
    private Integer orgId;

    /**
     * 组织机构内部编码
     */
    @ApiModelProperty(value = "组织机构内部编码")
    @TableField("orgCode")
    private String orgCode;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CREATEDTIME = "createdTime";

    public static final String ORGID = "orgId";

    public static final String ORGCODE = "orgCode";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

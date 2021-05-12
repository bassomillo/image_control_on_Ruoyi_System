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
 * 标签组表
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebTagGroup extends Model<WebTagGroup> {

    private static final long serialVersionUID=1L;

    /**
     * 标签ID
     */
    @ApiModelProperty(value = "标签ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签组名字
     */
    @ApiModelProperty(value = "标签组名字")
    private String name;

    /**
     * 标签组应用范围
     */
    @ApiModelProperty(value = "标签组应用范围")
    private String scope;

    /**
     * 标签组里的标签数量
     */
    @ApiModelProperty(value = "标签组里的标签数量")
    @TableField("tagNum")
    private Integer tagNum;

    /**
     * 含有标签id
     */
    @ApiModelProperty(value = "含有标签id")
    @TableField("tagIds")
    private String tagIds;

    /**
     * 是否常驻,常驻不可删除
     */
    @ApiModelProperty(value = "是否常驻,常驻不可删除 0-非常驻，1-常驻")
    private Boolean resident;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("updatedTime")
    private Integer updatedTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("createdTime")
    private Integer createdTime;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String SCOPE = "scope";

    public static final String TAGNUM = "tagNum";

    public static final String TAGIDS = "tagIds";

    public static final String RESIDENT = "resident";

    public static final String UPDATEDTIME = "updatedTime";

    public static final String CREATEDTIME = "createdTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

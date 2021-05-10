package com.ruoyi.project.monitor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标签组跟标签的中间表
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebTagGroupTag extends Model<WebTagGroupTag> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签ID
     */
    @TableField("tagId")
    private Integer tagId;

    /**
     * 标签组ID
     */
    @TableField("groupId")
    private Integer groupId;


    public static final String ID = "id";

    public static final String TAGID = "tagId";

    public static final String GROUPID = "groupId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

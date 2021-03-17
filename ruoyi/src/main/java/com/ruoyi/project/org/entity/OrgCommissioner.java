package com.ruoyi.project.org.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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

    /**
     * 组织机构id
     */
    @TableField("orgId")
    private Integer orgId;

    /**
     * 机构委员id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 机构职位
     */
    private String position;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Integer createdTime;

    /**
     * 最后更新时间
     */
    @TableField("updatedTime")
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

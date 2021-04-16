package com.ruoyi.project.org.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.ruoyi.project.org.entity.pojo.RoleShowPojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Org extends Model<Org> {

    private static final long serialVersionUID=1L;

    /**
     * 组织机构ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "机构名称，必填")
    private String name;

    @TableField("parentId")
    @ApiModelProperty(value = "组织机构父ID，必填")
    private Integer parentId;

    /**
     * 辖下组织机构数量
     */
    @TableField("childrenNum")
    private Integer childrenNum;

    /**
     * 当前组织机构层级
     */
    private Integer depth;

    /**
     * 索引
     */
    private Integer seq;

    /**
     * 备注
     */
    private String description;

    /**
     * 荣耀
     */
    private String honor;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 内部编码
     */
    @TableField("orgCode")
    private String orgCode;

    /**
     * 管理员id
     */
    @TableField("managerIds")
    private String managerIds;

    @TableField("createdUserId")
    @ApiModelProperty(value = "创建用户ID，创建时必填")
    private Integer createdUserId;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Long createdTime;

    /**
     * 最后更新时间
     */
    @TableField("updatedTime")
    private Long updatedTime;

    @TableField("simpleName")
    @ApiModelProperty(value = "工会组织简称，必填")
    private String simpleName;

    @TableField("inAdminOrg")
    @ApiModelProperty(value = "所在行政单位名称，必填")
    private String inAdminOrg;

    @TableField("socialCode")
    @ApiModelProperty(value = "统一社会信用代码，必填")
    private String socialCode;

    @TableField("orgCategory")
    @ApiModelProperty(value = "组织类别，必填")
    private String orgCategory;

    @TableField("orgLevel")
    @ApiModelProperty(value = "组织级别，必填")
    private String orgLevel;

    @ApiModelProperty(value = "所在地，必填")
    private String location;

    @TableField("orgStatus")
    @ApiModelProperty(value = "工会组织情况，必填")
    private String orgStatus;

    /**
     * 联系人mss账号
     */
    private String conMss;

    @TableField("setTime")
    @ApiModelProperty(value = "成立时间，必填")
    private LocalDate setTime;

    /******************************************************************************************************************/
    @TableField(exist = false)
    private List<Org> children;

    @TableField(exist = false)
    private Integer userTotal;

    @TableField(exist = false)
    private List<RoleShowPojo> roles;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PARENTID = "parentId";

    public static final String CHILDRENNUM = "childrenNum";

    public static final String DEPTH = "depth";

    public static final String SEQ = "seq";

    public static final String DESCRIPTION = "description";

    public static final String HONOR = "honor";

    public static final String CODE = "code";

    public static final String ORGCODE = "orgCode";

    public static final String MANAGERIDS = "managerIds";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    public static final String SIMPLENAME = "simpleName";

    public static final String INADMINORG = "inAdminOrg";

    public static final String SOCIALCODE = "socialCode";

    public static final String ORGCATEGORY = "orgCategory";

    public static final String ORGLEVEL = "orgLevel";

    public static final String LOCATION = "location";

    public static final String ORGSTATUS = "orgStatus";

    public static final String CON_MSS = "con_mss";

    public static final String SETTIME = "setTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

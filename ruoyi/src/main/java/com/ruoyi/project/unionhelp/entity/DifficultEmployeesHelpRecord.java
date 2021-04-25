package com.ruoyi.project.unionhelp.entity;

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
 * 困难员工帮扶记录表
 * </p>
 *
 * @author crl
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DifficultEmployeesHelpRecord extends Model<DifficultEmployeesHelpRecord> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帮扶对象
     */
    @TableField("userId")
    @ApiModelProperty(value = "帮扶对象")
    private Integer userId;

    /**
     * 帮扶形式
     */
    @TableField("modality")
    @ApiModelProperty(value = "帮扶形式")
    private String modality;

    /**
     * 慰问形式
     */
    @TableField("greetingType")
    @ApiModelProperty(value = "慰问形式")
    private String greetingType;

    /**
     * 帮扶金额
     */
    @TableField("money")
    @ApiModelProperty(value = "帮扶金额")
    private Float money;

    /**
     * 帮扶时间
     */
    @TableField("helpTime")
    @ApiModelProperty(value = "帮扶时间")
    private Integer helpTime;

    /**
     * 帮扶人或组织
     */
    @TableField("comforter")
    @ApiModelProperty(value = "帮扶人或组织")
    private String comforter;

    /**
     * 帮扶人电话
     */
    @TableField("comforterMobile")
    @ApiModelProperty(value = "帮扶人电话")
    private String comforterMobile;

    /**
     * 帮扶内容
     */
    @TableField("content")
    @ApiModelProperty(value = "帮扶内容")
    private String content;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间")
    private Integer createdTime;

    /**
     * 更新时间
     */
    @TableField("updatedTime")
    @ApiModelProperty(value = "更新时间")
    private Integer updatedTime;


    public static final String ID = "id";

    public static final String USERID = "userId";

    public static final String MODALITY = "modality";

    public static final String GREETINGTYPE = "greetingType";

    public static final String MONEY = "money";

    public static final String HELPTIME = "helpTime";

    public static final String COMFORTER = "comforter";

    public static final String COMFORTERMOBILE = "comforterMobile";

    public static final String CONTENT = "content";

    public static final String REMARK = "remark";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

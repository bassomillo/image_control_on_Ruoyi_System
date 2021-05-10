package com.ruoyi.project.unionhelp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class DifficultEmployeesHelpRecordGet {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "姓名")
    private String truename;

    @ApiModelProperty(value = "电话")
    private String mobile;

//    @ApiModelProperty(value = "邮箱")
//    private String email;

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
    @ApiModelProperty(value = "帮扶时间-时间戳格式")
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

}

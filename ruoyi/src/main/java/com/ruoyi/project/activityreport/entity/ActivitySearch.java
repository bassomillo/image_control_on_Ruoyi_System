package com.ruoyi.project.activityreport.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivitySearch {
    /**
     * 所属机构
     */
    @TableField("orgId")
    @ApiModelProperty(value = "所属机构")
    private Integer orgId;

    /**
     * 所属工会小组
     */
    @TableField("unitGroup")
    @ApiModelProperty(value = "所属工会小组")
    private String unitGroup;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
}

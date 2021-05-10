package com.ruoyi.project.unionhelp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.project.monitor.domain.WebLinkSetting0;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DifficultEmployeesHelpRecordSearch {

    private static final long serialVersionUID=1L;

    private DifficultEmployeesHelpRecordGet difficultEmployeesHelpRecordGet;

    @TableField("helpTime")
    @ApiModelProperty(value = "帮扶时间-字符串格式")
    private String helpTimeStringFormate;

}

package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysSiteUserLogin {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "用户登录限制状态")
    private String restrictForUserLogin;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;

}

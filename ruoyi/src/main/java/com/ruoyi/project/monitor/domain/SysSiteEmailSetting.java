package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysSiteEmailSetting {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "邮件发送按钮开关状态")
    private String emailOpenStatus;

    @ApiModelProperty(value = "SMTP服务器地址")
    private String smtpServerAddress;

    @ApiModelProperty(value = "SMTP端口号")
    private String smptPortNumber;

    @ApiModelProperty(value = "SMTP用户名")
    private String smptUserName;

    @ApiModelProperty(value = "SMTP授权码")
    private String smptAuthorizationCode;

    @ApiModelProperty(value = "发件人地址")
    private String smptEmailAddress;

    @ApiModelProperty(value = "发件人名称")
    private String emailSenderName;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;
}

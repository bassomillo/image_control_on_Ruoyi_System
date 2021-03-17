package com.ruoyi.project.monitor.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysSiteSetting{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "网站名字")
    private String sysSiteName;

    @ApiModelProperty(value = "网站副标题")
    private String sysSiteSlogan;

    @ApiModelProperty(value = "网站名字")
    private String sysSiteUrl;

    @ApiModelProperty(value = "网站logo")
    private String sysSiteLogo;

    @ApiModelProperty(value = "浏览器图标")
    private String sysSiteFavicon;

    @ApiModelProperty(value = "管理员邮箱")
    private String sysSiteMasterEmail;

    @ApiModelProperty(value = "ICP备案号")
    private String sysSiteIcp;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;
}

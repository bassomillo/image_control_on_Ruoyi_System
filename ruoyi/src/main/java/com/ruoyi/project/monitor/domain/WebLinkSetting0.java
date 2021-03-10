package com.ruoyi.project.monitor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WebLinkSetting0 {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "第一列链接标题")
    private String firstColumnHeadline;

    @ApiModelProperty(value = "第二列链接标题")
    private String secondColumnHeadline;

    @ApiModelProperty(value = "第三列链接标题")
    private String thirdColumnHeadline;

    @ApiModelProperty(value = "logo链接地址")
    private String logoAddress;

    @ApiModelProperty(value = "logo是否开启新窗口")
    private String logoStatus;

    @ApiModelProperty(value = "微博链接文本")
    private String weiboText;

    @ApiModelProperty(value = "微博链接地址")
    private String weiboAddress;

    @ApiModelProperty(value = "微博链接是否开启新窗口")
    private String weiboStatus;

    @ApiModelProperty(value = "上传微信公众号的二维码图片地址")
    private String weixinQrcodeAddress;

    @ApiModelProperty(value = "上传iOS版APP下载二维码地址")
    private String iosQrcodeAddress;

    @ApiModelProperty(value = "上传Android版APP下载二维码地址")
    private String androidQrcodeAddress;

    @ApiModelProperty(value = "程序运行时间点")
    private Date time;

}

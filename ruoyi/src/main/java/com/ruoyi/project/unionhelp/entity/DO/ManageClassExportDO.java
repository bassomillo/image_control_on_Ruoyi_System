package com.ruoyi.project.unionhelp.entity.DO;

import lombok.Data;

import java.util.Date;

@Data
public class ManageClassExportDO {
    private Integer id;

    //举办单位
    private String company;

    //负责人
    private Integer leaderUserId;

    private String trueName;
    //电话号码
    private String mobile;

    //开班时间
    private Integer startTime;

    private String openTime;

    //结束时间
    private Integer endTime;

    private String closeTime;

    //开班数量
    private Integer classNum;

    //报名人数
    private Integer willNum;

    //费用
    private String money;

    //参加人数
    private Integer joinNum;

    //举办形式
    private String holdForm;

    //师资来源
    private String teacherSource;

    //场地
    private String site;

    //资金来源
    private String moneySource;

    //刊发新闻稿外部媒体数量
    private Integer outNewNum;

    //刊发新闻稿内部媒体数量
    private Integer inNewNum;

    //刊发新闻稿其他媒体数量
    private Integer otherNewNum;

    //课程内容
    private String content;

    //补充说明
    private String remark;

}

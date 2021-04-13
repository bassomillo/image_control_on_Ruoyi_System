package com.ruoyi.project.democratic.entity.DO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ManageExportDO {
    //序号
    private Integer id;

    private Integer letterId;

    //提供建议人/发送者
    private String fromName;

    //所属机构
    private String orgName;

    private Integer orgId;

    //发送对象
    private String object;

    //发送时间
    private String sendDate;

    private Date date;

    //意见或建议
    private String content;

    //附件列表
    private List<FileUrlNameDO> files;

    //接受者
    private String toName;

    //回复信息
    private String reply;
}

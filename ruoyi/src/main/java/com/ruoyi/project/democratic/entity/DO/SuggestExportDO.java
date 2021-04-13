package com.ruoyi.project.democratic.entity.DO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SuggestExportDO {

    //序号
    private Integer id;

    private Integer suggestId;

    //提供建议人/发送者
    private String userName;

    //所属机构
    private String orgName;

    private Integer orgId;

    //发送时间
    private String sendDate;

    private Date date;

    //类型
    private String type;

    //意见或建议
    private String content;

    //附件列表
    private List<FileUrlNameDO> files;
}

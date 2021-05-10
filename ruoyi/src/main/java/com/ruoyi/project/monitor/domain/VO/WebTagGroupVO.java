package com.ruoyi.project.monitor.domain.VO;

import lombok.Data;

import java.util.Date;

@Data
public class WebTagGroupVO {
    private Integer id;

    private String name;

    private String scope;

    private Integer tagNum;

    private String tagIds;

    private Boolean resident;

    private Date updatedTime;

    private Date createdTime;
}

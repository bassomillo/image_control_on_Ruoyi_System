package com.ruoyi.project.monitor.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WebTagGroupVO {
    private Integer id;

    private String name;

    private String scope;

    private Integer tagNum;

    private List<Integer> tagIds;

    private Integer resident;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}

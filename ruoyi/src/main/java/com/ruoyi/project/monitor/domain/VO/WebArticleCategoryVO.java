package com.ruoyi.project.monitor.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WebArticleCategoryVO {
    private Integer id;

    private String name;

    private Integer weight;

    private Integer publishArticle;

    private String seoTitle;

    private String seoKeyword;

    private String seoDesc;

    private Integer published;

    private Integer parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}

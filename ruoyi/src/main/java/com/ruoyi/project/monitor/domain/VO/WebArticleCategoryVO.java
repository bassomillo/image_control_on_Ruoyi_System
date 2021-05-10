package com.ruoyi.project.monitor.domain.VO;

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

    private Date createdTime;
}

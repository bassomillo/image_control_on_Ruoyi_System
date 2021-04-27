package com.ruoyi.project.chairmanOnline.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签(Tag)实体类
 *
 * @author weide
 * @since 2021-04-25 15:57:07
 */
@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = -85390984109831970L;
    /**
     * 标签ID
     */
    private Integer id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签创建时间
     */
    private Date createdtime;

    private Integer orgid;
    /**
     * 组织机构内部编码
     */
    private String orgcode;



}

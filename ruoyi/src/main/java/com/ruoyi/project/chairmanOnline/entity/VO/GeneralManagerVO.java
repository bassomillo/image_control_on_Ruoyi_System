package com.ruoyi.project.chairmanOnline.entity.VO;

import lombok.Data;


/**
 * @param
 * @Date 2021/5/6
 * @Author weide
 * @description
 **/

public class GeneralManagerVO {

    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

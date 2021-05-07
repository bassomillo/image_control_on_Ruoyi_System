package com.ruoyi.project.chairmanOnline.entity;

import java.io.Serializable;

/**
 * 聊天室标签表(SocketChatroomTag)实体类
 *
 * @author weide
 * @since 2021-05-06 16:09:24
 */
public class SocketChatroomTag implements Serializable {
    private static final long serialVersionUID = -93629692254624978L;
    /**
     * id号
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

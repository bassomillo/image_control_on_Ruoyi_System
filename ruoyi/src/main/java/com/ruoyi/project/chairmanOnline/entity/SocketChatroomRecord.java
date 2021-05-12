package com.ruoyi.project.chairmanOnline.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天记录(SocketChatroomRecord)实体类
 *
 * @author weide
 * @since 2021-05-06 10:11:47
 */
@Data
public class SocketChatroomRecord implements Serializable {
    private static final long serialVersionUID = -75024801457444183L;
    /**
     * id
     */
    private Integer id;
    /**
     * 发送用户id
     */
    private Integer senderid;
    /**
     * 信息
     */
    private String content;
    /**
     * 类型document', 'video', 'audio', 'image', 'other', 'flash', 'text'
     */
    private Integer type;
    /**
     * 标签id
     */
    private Integer tagid;
    /**
     * 是否已显示
     */
    private Integer isshow;
    /**
     * 创建时间
     */
    private Date createdtime;
    /**
     * 是否删除
     */
    private Integer isdelete;

    @Transient
    private  String token;

    /**
     * 头像
     */

    @Transient
    private  String smallAvatar;


    /**
     * 发送者姓名
     */
    @Transient
    private  String nickname;




}

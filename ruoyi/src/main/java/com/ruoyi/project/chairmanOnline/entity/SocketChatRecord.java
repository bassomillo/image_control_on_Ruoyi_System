package com.ruoyi.project.chairmanOnline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.io.Serializable;

/**
 * 聊天记录(SocketChatRecord)实体类
 *
 * @author weide
 * @since 2021-04-19 09:46:53
 */
@Data
public class SocketChatRecord implements Serializable {
    private static final long serialVersionUID = 703210703071885099L;
    /**
     * id
     */
    private Integer id;
    /**
     * 所属对话
     */
    private Integer conversationid;
    /**
     * 发送用户id
     */
    private Integer senderid;
    /**
     * 接收用户id
     */
    private Integer receiverid;
    /**
     * 信息
     */
    private String content;
    /**
     * 是否已回复
     */
    private Integer isreply;
    /**
     * 标签id
     */
    private Integer tagid;
    /**
     * 所属模块
     */
    private Integer module;
    /**
     * 是否过期
     */
    private Integer expired;
    /**
     * 是否查看
     */
    private Integer isshow;
    /**
     * 聊天记录类型：1:'document',2:'video',3:'audio',4:'image',5:'other',6:'flash',7:'text'
     */
    @ApiModelProperty(example = " 聊天记录类型：1:'document',2:'video',3:'audio',4:'image',5:'other',6:'flash',7:'text'")
    private Integer type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdtime;
    /**
     * 是否删除
     */
    private Integer isdelete;
    /**
     * 所属话题
     */
    private Integer topic;
    /**
     * 是否已读
     */
    private Integer isread;
    /**
     * 是否发送到对方客户端
     */
    private Integer issent;


    /**
     * token字符串
     */
    @Transient
    private String token;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConversationid() {
        return conversationid;
    }

    public void setConversationid(Integer conversationid) {
        this.conversationid = conversationid;
    }

    public Integer getSenderid() {
        return senderid;
    }

    public void setSenderid(Integer senderid) {
        this.senderid = senderid;
    }

    public Integer getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(Integer receiverid) {
        this.receiverid = receiverid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsreply() {
        return isreply;
    }

    public void setIsreply(Integer isreply) {
        this.isreply = isreply;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public Integer getIssent() {
        return issent;
    }

    public void setIssent(Integer issent) {
        this.issent = issent;
    }
}

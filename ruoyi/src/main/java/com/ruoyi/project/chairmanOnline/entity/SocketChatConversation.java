package com.ruoyi.project.chairmanOnline.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 聊天会话表(SocketChatConversation)实体类
 *
 * @author weide
 * @since 2021-04-19 09:48:09
 */
@Data
public class SocketChatConversation implements Serializable {
    private static final long serialVersionUID = 816839342562380908L;
    /**
     * 会话Id
     */
    private Integer id;
    /**
     * 发信人Id
     */
    private Integer senderid;
    /**
     * 收信人Id
     */
    private Integer receiverid;
    /**
     * 此对话的信息条数
     */
    private Integer messagenum;
    /**
     * 最后发信人ID
     */
    private Integer latestmessageuserid;
    /**
     * 最后发信时间
     */
    private Integer latestmessagetime;
    /**
     * 最后发信内容
     */
    private String latestmessagecontent;
    /**
     * 聊天记录类型：1:'document',2:'video',3:'audio',4:'image',5:'other',6:'flash',7:'text'
     */
    private String latestmessagetype;
    /**
     * 所属模块
     */
    private Integer module;
    /**
     * 最后会话标签
     */
    private Integer latesttagid;
    /**
     * 是否置顶
     */
    private Integer issticky;
    /**
     * 未读数量
     */
    private Integer unreadnum;
    /**
     * 会话创建时间
     */
    private Date createdtime;
    /**
     * 更新时间
     */
    private Date updatedtime;
    /**
     * 话题
     */
    private Integer topic;

    private Integer isdelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getMessagenum() {
        return messagenum;
    }

    public void setMessagenum(Integer messagenum) {
        this.messagenum = messagenum;
    }

    public Integer getLatestmessageuserid() {
        return latestmessageuserid;
    }

    public void setLatestmessageuserid(Integer latestmessageuserid) {
        this.latestmessageuserid = latestmessageuserid;
    }

    public Integer getLatestmessagetime() {
        return latestmessagetime;
    }

    public void setLatestmessagetime(Integer latestmessagetime) {
        this.latestmessagetime = latestmessagetime;
    }

    public String getLatestmessagecontent() {
        return latestmessagecontent;
    }

    public void setLatestmessagecontent(String latestmessagecontent) {
        this.latestmessagecontent = latestmessagecontent;
    }

    public String getLatestmessagetype() {
        return latestmessagetype;
    }

    public void setLatestmessagetype(String latestmessagetype) {
        this.latestmessagetype = latestmessagetype;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public Integer getLatesttagid() {
        return latesttagid;
    }

    public void setLatesttagid(Integer latesttagid) {
        this.latesttagid = latesttagid;
    }

    public Integer getIssticky() {
        return issticky;
    }

    public void setIssticky(Integer issticky) {
        this.issticky = issticky;
    }

    public Integer getUnreadnum() {
        return unreadnum;
    }

    public void setUnreadnum(Integer unreadnum) {
        this.unreadnum = unreadnum;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

}

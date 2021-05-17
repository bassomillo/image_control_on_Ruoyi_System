package com.ruoyi.project.chairmanOnline.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (OrgCommissioner)实体类
 *
 * @author weide
 * @since 2021-05-07 15:44:17
 */
@Data
@TableName("org_commissioner")
public class SocketChatOrgCommissioner implements Serializable {
    private static final long serialVersionUID = 969659114518748048L;
    /**
     * id
     */
    private Integer id;
    /**
     * 组织机构id
     */
    private Integer orgid;
    /**
     * 机构委员id
     */
    private Integer userid;
    /**
     * 机构职位
     */
    private String position;
    /**
     * 创建时间
     */
    private Integer createdtime;
    /**
     * 最后更新时间
     */

    private Integer updatedtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Integer createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Integer updatedtime) {
        this.updatedtime = updatedtime;
    }

}

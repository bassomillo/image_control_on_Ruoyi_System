package com.ruoyi.project.chairmanOnline.entity.QO;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @param
 * @Date 2021/4/20
 * @Author weide
 * @description
 **/
@Data
public class SocketChatRecordQO implements Serializable {

    @ApiModelProperty(example = "对话id")
    private Integer conversationid;
    /**
     *
     */
    @ApiModelProperty(example = "发送者，此参数查询必传",required = true)
    private Integer senderid;
    /**
     * 接收用户id
     */
    @ApiModelProperty(example = "接收者，此参数查询必传",required = true)
    private Integer receiverid;
    /**
     *
     */
    @ApiModelProperty(example = "内容")
    private String content;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(example = "开始时间，格式yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(example = "截至时间，格式yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    /**
     * 所属话题
     */
    @ApiModelProperty(example = "话题")
    private Integer topic;



}

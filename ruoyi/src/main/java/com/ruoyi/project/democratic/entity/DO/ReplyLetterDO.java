package com.ruoyi.project.democratic.entity.DO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReplyLetterDO {

    @ApiModelProperty(value = "回复的信件id列表，必填")
    private List<Integer> idList;

    @ApiModelProperty(value = "回复内容，必填")
    private String content;

    @ApiModelProperty(value = "当前登录者id，必填")
    private Integer userId;
}

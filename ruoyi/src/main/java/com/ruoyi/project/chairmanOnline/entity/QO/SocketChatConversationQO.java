package com.ruoyi.project.chairmanOnline.entity.QO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @param
 * @Date 2021/4/28
 * @Author weide
 * @description
 **/
@Data
public class SocketChatConversationQO {

    @ApiModelProperty(example = "用户姓名/用户名",required = true)
    private String name;

}

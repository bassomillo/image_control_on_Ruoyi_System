package com.ruoyi.project.chairmanOnline.entity.VO;

import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import lombok.Data;

/**
 * @param
 * @Date 2021/4/29
 * @Author weide
 * @description
 **/
@Data
public class SocketChatConversationVO extends SocketChatConversation {


    private Integer userId;
    private String nickname;
    private String smallAvatar;
    private String mediumAvatar;
    private String largeAvatar;
    private Integer orgId;
    private String orgName;
    private String trueName;


}

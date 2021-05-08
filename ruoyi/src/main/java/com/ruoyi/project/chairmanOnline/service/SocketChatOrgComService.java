package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketChatOrgCommissioner;

/**
 * (OrgCommissioner)表服务接口
 *
 * @author weide
 * @since 2021-05-07 15:44:17
 */
public interface SocketChatOrgComService {

     Integer getCommissionerByUserId(int userId);
}

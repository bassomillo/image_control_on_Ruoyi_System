package com.ruoyi.project.chairmanOnline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.project.chairmanOnline.dao.SocketChatOrgComDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatOrgCommissioner;
import com.ruoyi.project.chairmanOnline.service.SocketChatOrgComService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (SocketChatOrgCommissioner)表服务实现类
 *
 * @author weide
 * @since 2021-05-07 15:44:17
 */
@Service("socketChatOrgComService")
public class SocketChatOrgComServiceImpl implements SocketChatOrgComService {
    @Resource
    private SocketChatOrgComDao socketChatOrgComDao;

 /**
      * 如果当前用户是秘书则获取其所属的总经理信息
      *
      * @param
      * @return
      */
    @Override
    public Integer getCommissionerByUserId(int userId) {
        QueryWrapper<SocketChatOrgCommissioner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SocketChatOrgCommissioner::getUserid, userId).like(SocketChatOrgCommissioner::getPosition, "秘书");
        SocketChatOrgCommissioner sec = socketChatOrgComDao.selectOne(queryWrapper);
        if(null == sec){
            return userId;
        }
        queryWrapper.clear();
        queryWrapper.eq("orgId", sec.getOrgid()).eq("position", sec.getPosition().trim().replace("秘书", ""));
        SocketChatOrgCommissioner gm = socketChatOrgComDao.selectOne(queryWrapper);
        if(null == gm){
            return userId;
        }
        return gm.getUserid();
    }
}

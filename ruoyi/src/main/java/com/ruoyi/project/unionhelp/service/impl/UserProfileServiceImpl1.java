package com.ruoyi.project.unionhelp.service.impl;

import com.ruoyi.project.unionhelp.entity.UserProfile1;
import com.ruoyi.project.unionhelp.mapper.UserProfileDao1;
import com.ruoyi.project.unionhelp.service.IUserProfileService1;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crl
 * @since 2021-04-21
 */
@Service
public class UserProfileServiceImpl1 extends ServiceImpl<UserProfileDao1, UserProfile1> implements IUserProfileService1 {
    @Autowired
    private UserProfileDao1 userProfileDao;
    @Override
    public List<Userinfo> findAllPage(int pageSize, int index, String searchContent){
        return userProfileDao.findAllPage(pageSize,index,searchContent);
    }


}

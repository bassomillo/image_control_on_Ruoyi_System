package com.ruoyi.project.union.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserProfileDao;
import com.ruoyi.project.union.service.IUserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileDao, UserProfile> implements IUserProfileService {

    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    public UserProfile searchUserProfileById(Integer id) {
        return userProfileDao.selectById(id);
    }
}

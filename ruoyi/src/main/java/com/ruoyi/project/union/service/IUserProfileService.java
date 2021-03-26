package com.ruoyi.project.union.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.entity.UserProfile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IUserProfileService extends IService<UserProfile> {

    UserProfile searchUserProfileById(Integer id);
}

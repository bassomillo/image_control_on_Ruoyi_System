package com.ruoyi.project.union.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.union.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.union.pojo.UserSearchPojo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IUserService extends IService<User> {

    AjaxResult searchUser(UserSearchPojo userSearchPojo);

    User searchUserByAccount(String account);
}

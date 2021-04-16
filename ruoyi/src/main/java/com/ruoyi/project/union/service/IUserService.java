package com.ruoyi.project.union.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.pojo.OrgUserSearchPojo;
import com.ruoyi.project.union.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.pojo.DisableUserPojo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;

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

    AjaxResult searchAccount(AccountSearchVo accountSearchVo);

    boolean isExistMobile(String mobile);

    void createUser(UserProfile userProfile);

    AjaxResult disableUser(DisableUserPojo disableUserPojo);

    void updateUser(UserProfile userProfile);

    AjaxResult searchUserById(Integer userId);

    AjaxResult searchOrgUser(OrgUserSearchPojo orgUserSearchPojo);

    User searchUserByAccount(String account);
}

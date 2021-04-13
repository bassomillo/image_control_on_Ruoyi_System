package com.ruoyi.project.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.pojo.OrgUserSearchPojo;
import com.ruoyi.project.union.mapper.UserProfileDao;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.UserVo;
import com.ruoyi.project.union.pojo.DisableUserPojo;
import com.ruoyi.project.union.pojo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private OrgDao orgDao;

    @Override
    public AjaxResult searchUser(UserSearchPojo userSearchPojo) {
        Map<String, Object> map = new HashMap<>();

        if(null == userSearchPojo.getSize())
            userSearchPojo.setSize(20);
        if(null == userSearchPojo.getCurrent())
            userSearchPojo.setCurrent(1);
        List<Integer> ids = userDao.searchUser(userSearchPojo);

        IPage page = new Page<>(userSearchPojo.getCurrent(), userSearchPojo.getSize());
        IPage<User> user = userDao.selectPage(page, new QueryWrapper<User>().in(User.ID, ids));
        List<UserVo> userVos = new ArrayList<>();
        for(User u : user.getRecords()) {
            UserVo uv = new UserVo();
            uv.setNickname(u.getNickname());
            UserProfile up = userProfileDao.selectById(u.getId());
            uv.setTruename(up.getTruename());
            uv.setGender(up.getGender().equals("male") ? "男" : "女");
            uv.setOrganization(null);
            uv.setMobile(up.getMobile());

            userVos.add(uv);
        }
        page.setRecords(userVos);
        map.put("info", page);

        List<User> userList = userDao.selectList(new QueryWrapper<>());
        map.put("total", userList.size()); // 总数
        List<User> userLoecked = userDao.selectList(new QueryWrapper<User>().eq(User.LOCKED, 1));
        map.put("locked", userLoecked.size()); // 禁用人数

        return AjaxResult.success(map);
    }

    @Override
    @Transactional
    public void createUser(UserProfile userProfile) {
        // 先对user表相关数据进行新增，主键id同时作为user_profile的id
        User user = new User();
        user.setCreatedTime((int) System.currentTimeMillis() / 1000);
        user.setUpdatedTime((int) System.currentTimeMillis() / 1000);
        user.setOrgId(userProfile.getOrgId());
        Org org = orgDao.selectById(userProfile.getOrgId());
        user.setOrgCode(org != null ? org.getOrgCode() : "");
        userDao.insert(user);

        userProfile.setId(user.getId());
        userProfileDao.insert(userProfile);
    }

    @Override
    @Transactional
    public AjaxResult disableUser(DisableUserPojo disableUserPojo) {
        // 用户与密码进行校检

        // 对user进行更新
        User user = userDao.selectById(disableUserPojo.getDisableUserId());
        user.setLocked(1);
        Long lockDeadline = disableUserPojo.getLockDeadline().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        user.setLockDeadline(lockDeadline);
        userDao.updateById(user);
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public void updateUser(UserProfile userProfile) {
        User user = new User();
        user.setId(userProfile.getId());
        user.setUpdatedTime((int) System.currentTimeMillis() / 1000);
        if(null != userProfile.getOrgId()) {
            user.setOrgId(userProfile.getOrgId());
            Org org = orgDao.selectById(userProfile.getOrgId());
            user.setOrgCode(org != null ? org.getOrgCode() : "");
        }

        userDao.updateById(user);
        userProfileDao.updateById(userProfile);
    }

    @Override
    @Transactional
    public AjaxResult searchUserById(Integer userId) {
        return AjaxResult.success(userProfileDao.selectById(userId));
    }

    @Override
    public AjaxResult searchOrgUser(OrgUserSearchPojo orgUserSearchPojo) {
        return AjaxResult.success(userDao.searchOrgUser(orgUserSearchPojo));
    }

    @Override
    public User searchUserByAccount(String account) {
        return userDao.selectOne(new QueryWrapper<User>().eq(User.NICKNAME, account));
    }
}

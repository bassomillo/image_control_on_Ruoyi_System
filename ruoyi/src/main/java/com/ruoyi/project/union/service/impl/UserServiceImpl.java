package com.ruoyi.project.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.entity.pojo.OrgUserSearchPojo;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.SysRoleMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import com.ruoyi.project.union.mapper.UserProfileDao;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.UserVo;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.pojo.DisableUserPojo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.*;

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

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

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
            uv.setId(u.getId());
            uv.setNickname(u.getNickname());
            uv.setOrganization(combinOrg(u.getOrgId()));
            uv.setLocked(u.getLocked());

            UserProfile up = userProfileDao.selectById(u.getId());
            uv.setTruename(up.getTruename());
            uv.setGender(up.getGender().equals("male") ? "男" : "女");
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
    public AjaxResult searchAccount(AccountSearchVo accountSearchVo) {
        Map<String, Object> map = new HashMap<>();
        List<UserVo> userVoList = userDao.searchAccount(accountSearchVo);
        userVoList.forEach(item -> {
            item.setOrganization(combinOrg(item.getOrgId()));
            item.setRole(sysRoleMapper.selectRolePermissionByUserId((long) item.getId()));
        });

        map.put("userVoList", userVoList);

        Set<SysUserRole> userRoles = new HashSet<>(sysUserRoleMapper.selectList(new QueryWrapper<SysUserRole>().ne(SysUserRole.ROLE_ID, 1)));
        map.put("total", userRoles.size());

        return AjaxResult.success(map);
    }

    private String combinOrg(Integer orgId) {
        List<String> orgList = new ArrayList<>();
        combinParentOrg(orgId, orgList);
        Collections.reverse(orgList); // 反转顺序
        StringBuilder org = new StringBuilder();
        for(int i = 0; i < orgList.size(); i++) {
            if(0 == i) {
                org.append(orgList.get(i));
            } else {
                org.append("/").append(orgList.get(i));
            }
        }

        return String.valueOf(org);
    }

    private void combinParentOrg(Integer orgId, List<String> orgList) {
        Org org = orgDao.selectById(orgId);
        orgList.add(org.getName());
        if(1 != org.getId()) {
            combinParentOrg(org.getParentId(), orgList);
        }
    }

    @Override
    public boolean isExistMobile(String mobile) {
        List<UserProfile> userProfile = userProfileDao.selectList(new QueryWrapper<UserProfile>().eq(UserProfile.MOBILE, mobile));
        return 0 != userProfile.size();
    }

    @Override
    @Transactional
    public void createUser(UserProfile userProfile) {
        // 先对user表相关数据进行新增，主键id同时作为user_profile的id
        User user = new User();
        user.setNickname("user" + IdUtils.randomUUID());
        user.setCreatedTime((int) (System.currentTimeMillis() / 1000));
        user.setUpdatedTime((int) (System.currentTimeMillis() / 1000));
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
        user.setUpdatedTime((int) (System.currentTimeMillis() / 1000));
        if(null != userProfile.getOrgId()) {
            user.setOrgId(userProfile.getOrgId());
            Org org = orgDao.selectById(userProfile.getOrgId());
            user.setOrgCode(org != null ? org.getOrgCode() : "");
        }

        userDao.updateById(user);
        userProfileDao.updateById(userProfile);
    }

    @Override
    public AjaxResult searchUserById(Integer userId) {
        UserProfile userProfile = userProfileDao.selectById(userId);
        User user = userDao.selectById(userId);
        userProfile.setOrganization(combinOrg(user.getOrgId()));
        userProfile.setOrgId(user.getOrgId());
        userProfile.setId(userId);
        return AjaxResult.success(userProfile);
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

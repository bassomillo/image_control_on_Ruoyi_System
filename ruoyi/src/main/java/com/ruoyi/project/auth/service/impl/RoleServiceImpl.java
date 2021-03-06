package com.ruoyi.project.auth.service.impl;

import cn.hutool.core.util.EscapeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.entity.Role;
import com.ruoyi.project.auth.mapper.RoleDao;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.auth.pojo.RoleSearchPojo;
import com.ruoyi.project.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysRoleMenu;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.SysMenuMapper;
import com.ruoyi.project.system.mapper.SysRoleMapper;
import com.ruoyi.project.system.mapper.SysRoleMenuMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import com.ruoyi.project.system.service.impl.SysMenuServiceImpl;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.service.LoginTokenService;
import org.phprpc.util.AssocArray;
import org.phprpc.util.Cast;
import org.phprpc.util.PHPSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional
    public AjaxResult searchRole(RoleSearchPojo roleSearchPojo) {
        IPage page = new Page<>(roleSearchPojo.getCurrent(), roleSearchPojo.getSize());
        IPage<SysRole> sysRoles = sysRoleMapper.selectPage(page, new QueryWrapper<SysRole>()
                .like(!StringUtils.isEmpty(roleSearchPojo.getName()), SysRole.ROLE_NAME, roleSearchPojo.getName()).or()
                .like(!StringUtils.isEmpty(roleSearchPojo.getCode()), SysRole.ROLE_KEY, roleSearchPojo.getCode()));

//        List<Role> roleList = new ArrayList<>();
//        PHPSerializer phpSerializer = new PHPSerializer();
//        IPage page = new Page<>(roleSearchPojo.getCurrent(), roleSearchPojo.getSize());
//        IPage<Role> role = roleDao.selectPage(page, new QueryWrapper<Role>()
//                .like(!StringUtils.isEmpty(roleSearchPojo.getName()), Role.NAME, roleSearchPojo.getName()).or()
//                .like(!StringUtils.isEmpty(roleSearchPojo.getCode()), Role.CODE, roleSearchPojo.getCode()));
//        for(Role r : role.getRecords()) {
//            r.setCreateName(null != userDao.selectById(r.getCreatedUserId()) ? userDao.selectById(r.getCreatedUserId()).getNickname() : null);
//            r.setCreateTime(null);
//
//            // 处理角色对应的menu
//            List<String> menuList = new ArrayList<>();
//            try {
//                AssocArray array = (AssocArray) phpSerializer.unserialize(r.getData().getBytes());
//                for (int i = 0; i < array.size(); i++) {
//                    String t = (String) Cast.cast(array.get(i), String.class);
//                    menuList.add(t);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            r.setMenuList(menuList);
//
//            // 处理创建时间
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String res = simpleDateFormat.format(new Date(r.getCreatedTime() * 1000L));
//            r.setCreateTime(res);
//
//            roleList.add(r);
//        }
//        page.setRecords(roleList);

        return AjaxResult.success(sysRoles);
    }

    @Override
    @Transactional
    public AjaxResult createRole(RoleCreatePojo roleCreatePojo) {
//        long currentTime = System.currentTimeMillis() / 1000;

        User loginUser = SpringUtils.getBean(LoginTokenService.class).getLoginUser(ServletUtils.getRequest());
        roleCreatePojo.getSysRole().setCreateBy(loginUser.getNickname());
        // 处理各类不能为空的字段
        if(null == roleCreatePojo.getSysRole().getRoleSort())
            roleCreatePojo.getSysRole().setRoleSort("1");
        if(null == roleCreatePojo.getSysRole().getStatus())
            roleCreatePojo.getSysRole().setStatus("0");
        if(null == roleCreatePojo.getSysRole().getCreateTime())
            roleCreatePojo.getSysRole().setCreateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        if(null == roleCreatePojo.getSysRole().getRemark())
            roleCreatePojo.getSysRole().setRemark(roleCreatePojo.getSysRole().getRoleName());

        // 新增角色
        SysRole sysRole = roleCreatePojo.getSysRole();
        sysRoleMapper.insert(sysRole);
        Long roleId = sysRole.getRoleId();

        // 新增角色对应的menu
        if(null != roleCreatePojo.getMenuList() && 0 != roleCreatePojo.getMenuList().size()) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            roleCreatePojo.getMenuList().forEach(item -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(item);

                sysRoleMenus.add(sysRoleMenu);
            });
            sysRoleMenuMapper.batchRoleMenu(sysRoleMenus);
        }

        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult del(Long roleId) {
        // 删除角色-菜单表中对应的数据
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq(SysRoleMenu.ROLE_ID, roleId));

        // 删除用户-角色表中对应的数据
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq(SysUserRole.ROLE_ID, roleId));

        // 删除角色
        sysRoleMapper.delete(new QueryWrapper<SysRole>().eq(SysRole.ROLE_ID, roleId));

        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult updateRole(SysRole sysRole) {
        // 更新角色对应菜单列表
        if(null != sysRole.getMenuIds() && 0 != sysRole.getMenuIds().length) {
            sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq(SysRoleMenu.ROLE_ID, sysRole.getRoleId()));
            for(Long menuId : sysRole.getMenuIds()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getRoleId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }

        // 更新角色信息
        sysRole.setUpdateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        int i = sysRoleMapper.updateRole(sysRole);

        if(0 == i)
            return AjaxResult.error("不存在此角色！");
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult searchRoleById(Long roleId) {
        Map<String, Object> map = new HashMap<>();

        // 获取对应角色id详细信息
        SysRole sysRole = sysRoleMapper.selectRoleById(roleId);
        map.put("sysRole", sysRole);

        // 获取对应角色拥有的菜单
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq(SysRoleMenu.ROLE_ID, roleId));
        List<Long> menuIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        if(0 == menuIds.size())
            menuIds.add(0L);
        List<SysMenu> menus = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().in(SysMenu.MENU_ID, menuIds).eq(SysMenu.STATUS, 0));
        map.put("menus", sysMenuService.getChildPerms(menus, 0));

        return AjaxResult.success(map);
    }
}

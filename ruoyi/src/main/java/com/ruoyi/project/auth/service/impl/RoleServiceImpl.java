package com.ruoyi.project.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.entity.Role;
import com.ruoyi.project.auth.mapper.RoleDao;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.auth.pojo.RoleSearchPojo;
import com.ruoyi.project.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.union.mapper.UserDao;
import org.phprpc.util.AssocArray;
import org.phprpc.util.Cast;
import org.phprpc.util.PHPSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public AjaxResult searchRole(RoleSearchPojo roleSearchPojo) {
        List<Role> roleList = new ArrayList<>();
        PHPSerializer phpSerializer = new PHPSerializer();
        IPage page = new Page<>(roleSearchPojo.getCurrent(), roleSearchPojo.getSize());
        IPage<Role> role = roleDao.selectPage(page, new QueryWrapper<Role>()
                .like(!StringUtils.isEmpty(roleSearchPojo.getName()), Role.NAME, roleSearchPojo.getName()).or()
                .like(!StringUtils.isEmpty(roleSearchPojo.getCode()), Role.CODE, roleSearchPojo.getCode()));
        for(Role r : role.getRecords()) {
            r.setCreateName(null != userDao.selectById(r.getCreatedUserId()) ? userDao.selectById(r.getCreatedUserId()).getNickname() : null);
            r.setCreateTime(null);

            // 处理角色对应的menu
            List<String> menuList = new ArrayList<>();
            try {
                AssocArray array = (AssocArray) phpSerializer.unserialize(r.getData().getBytes());
                for (int i = 0; i < array.size(); i++) {
                    String t = (String) Cast.cast(array.get(i), String.class);
                    menuList.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            r.setMenuList(menuList);

            // 处理创建时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String res = simpleDateFormat.format(new Date(r.getCreatedTime() * 1000L));
            r.setCreateTime(res);

            roleList.add(r);
        }
        page.setRecords(roleList);
        return AjaxResult.success(page);
    }

    @Override
    public AjaxResult createRole(Role role) {
        long currentTime = System.currentTimeMillis() / 1000;
        if(null == role.getCreatedTime())
            role.setCreatedTime((int) currentTime);
        if(null == role.getUpdatedTime())
            role.setUpdatedTime((int) currentTime);
        if(null == role.getSetup())
            role.setSetup(0);

        roleDao.insert(role);
        return AjaxResult.success();
    }
}

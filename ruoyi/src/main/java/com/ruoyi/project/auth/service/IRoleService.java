package com.ruoyi.project.auth.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.auth.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.auth.pojo.RoleCreatePojo;
import com.ruoyi.project.auth.pojo.RoleSearchPojo;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IRoleService extends IService<Role> {

    AjaxResult searchRole(RoleSearchPojo roleSearchPojo);

    AjaxResult createRole(Role role);
}

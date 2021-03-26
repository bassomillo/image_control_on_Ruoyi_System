package com.ruoyi.project.auth.pojo;

import com.ruoyi.project.system.domain.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
public class RoleCreatePojo {

    @ApiModelProperty(value = "角色实体")
    private SysRole sysRole;

    @ApiModelProperty(value = "权限列表")
    private List<Long> menuList;
}

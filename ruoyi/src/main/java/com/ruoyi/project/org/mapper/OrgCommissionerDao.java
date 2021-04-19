package com.ruoyi.project.org.mapper;

import com.ruoyi.project.org.entity.OrgCommissioner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.org.entity.pojo.OrgRoleSearchPojo;
import com.ruoyi.project.org.entity.pojo.RoleShowPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Repository
public interface OrgCommissionerDao extends BaseMapper<OrgCommissioner> {

    List<RoleShowPojo> searchOrgRole(OrgRoleSearchPojo roleSearchPojo);

    List<RoleShowPojo> searchOrgRoleById(@Param("orgId") Integer orgId);
}

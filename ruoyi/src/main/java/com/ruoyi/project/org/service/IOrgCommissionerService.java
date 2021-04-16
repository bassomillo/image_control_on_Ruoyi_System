package com.ruoyi.project.org.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.org.entity.pojo.OrgRoleSearchPojo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IOrgCommissionerService extends IService<OrgCommissioner> {

    AjaxResult searchOrgRole(OrgRoleSearchPojo roleSearchPojo);

    AjaxResult delOrgRoleById(Integer id);

    AjaxResult createOrgCommissioner(OrgCommissioner orgCommissioner);

    AjaxResult orgRoleImport(MultipartFile file);

    AjaxResult orgRoleImportCheck(MultipartFile file);

    AjaxResult orgImport(MultipartFile file);

    AjaxResult orgImportCheck(MultipartFile file);
}

package com.ruoyi.project.org.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IOrgService extends IService<Org> {

    List<Org> searchOrgTree();

    void createOrg(Org org);

    AjaxResult delOrgById(Integer orgId);

    AjaxResult updateOrg(Org org);

    Org searchOrgById(Integer orgId);

    AjaxResult removeOrg(Integer orgId, Integer parentId);

    boolean isRepeat(Org org, Integer parentId);

    /******************************************************************************************************************/
    List<Integer> searchOrgMem(Integer orgId);
}

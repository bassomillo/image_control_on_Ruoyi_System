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

    /**
     * 校检A部门下是否存在B名称部门
     * @param orgName B名称
     * @param parentId A部门orgId
     * @return true-存在/false-不存在
     */
    boolean isRepeat(String orgName, Integer parentId);

    /******************************************************************************************************************/
    /**
     * 获取指定机构下所有用户id
     * @param orgId 机构id
     * @return 用户ids
     */
    List<Integer> searchOrgMem(Integer orgId);
}

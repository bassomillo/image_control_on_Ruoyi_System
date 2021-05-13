package com.ruoyi.project.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.entity.pojo.RoleShowPojo;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.service.LoginTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
@Slf4j
public class OrgServiceImpl extends ServiceImpl<OrgDao, Org> implements IOrgService {

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private OrgCommissionerDao orgCommissionerDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Org> searchOrgTree() {
        List<Org> fatherOrg = orgDao.selectList(new QueryWrapper<Org>());
        return getChildPerms(fatherOrg, 0);
    }

    @Override
    @Transactional
    public void createOrg(Org org) {
        User operUser = SpringUtils.getBean(LoginTokenService.class).getLoginUser(ServletUtils.getRequest());
        org.setCreatedUserId(operUser.getId());
        if(null == org.getCreatedTime())
            org.setCreatedTime(System.currentTimeMillis() / 1000);
        if(null == org.getUpdatedTime())
            org.setUpdatedTime(System.currentTimeMillis() / 1000);
        orgDao.insert(org);

        // 新增成功后更新orgCode
        String orgCodeStart = org.getId().toString() + ".";
        String orgCode = combinOrgCode(org.getParentId(), orgCodeStart);
        org.setOrgCode(orgCode);
        orgDao.updateById(org);
    }

    @Override
    @Transactional
    public AjaxResult delOrgById(Integer orgId) {
        // 校检该机构下是否有子机构
        List<Org> orgs = orgDao.selectList(new QueryWrapper<Org>().eq(Org.PARENTID, orgId));
        if(0 != orgs.size())
            return AjaxResult.error("该机构下有附属机构，不能删除");

        List<Integer> orgIds = new ArrayList<>();
        getChildOrgId(orgId, orgIds);
        // 校检该机构及其附属下是否有对应角色
        List<OrgCommissioner> orgCommissioners = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().in(OrgCommissioner.ORGID, orgIds));
        if(0 != orgCommissioners.size())
            return AjaxResult.error("该机构或下属机构拥有用户，不能删除");

        // 校检该机构下是否有对应会员（账号）
        List<User> users = userDao.selectList(new QueryWrapper<User>().in(User.ORGID, orgIds));
        if(0 != users.size())
            return AjaxResult.error("该机构或下属机构拥有用户，不能删除");

        orgDao.deleteById(orgId);

        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateOrg(Org org) {
        if(isRepeat(org.getName(), org.getId(), org.getParentId()))
            return AjaxResult.error("当前机构下存在同名机构，机构名<" + org.getName() + ">不可用");

        org.setUpdatedTime(System.currentTimeMillis() / 1000);
        orgDao.updateById(org);

        return AjaxResult.success();
    }

    @Override
    public Org searchOrgById(Integer orgId) {
        // 查询该机构下的会员人数
        Org org = orgDao.selectById(orgId);
        List<Integer> orgIds = Str.getOrgChildIds(orgId, org.getOrgCode());
        List<User> users = userDao.selectList(new QueryWrapper<User>().in(0 != orgIds.size(), User.ORGID, orgIds));
        org.setUserTotal(users.size());
        // 赋值机构隶属
        if(1 == orgId) {
            org.setParentOrg("-");
        } else {
            Org parentOrg = orgDao.selectById(org.getParentId());
            org.setParentOrg(parentOrg.getName());
        }
        // 赋值机构级别
        org.setLevel(Str.getOrgLevel(orgId, org.getOrgCode()));

        // 查询该机构下的委员（角色）情况
        List<RoleShowPojo> roles = orgCommissionerDao.searchOrgRoleById(orgId);
        org.setRoles(roles);

        return org;
    }

    @Override
    public AjaxResult removeOrg(Integer orgId, Integer parentId) {
        Org org = orgDao.selectById(orgId);
        Org orgParent = orgDao.selectById(parentId);
        if(isRepeat(org.getName(), null, parentId))
            return AjaxResult.error("目标机构下存在同名机构，机构名<" + org.getName() + ">不可用");

        String orgCode = orgParent.getOrgCode() + org.getId() + ".";
        org.setParentId(parentId);
        org.setOrgCode(orgCode);
        orgDao.updateById(org);

        // 更新该机构下所有下属机构的orgCode
        List<Integer> childOrgIds = new ArrayList<>();
        getChildOrgId(orgId, childOrgIds);
        childOrgIds.forEach(item->{
            if(!item.equals(orgId)) {
                String start = item.toString() + ".";
                String code = combinOrgCode(orgId, start);

                Org childOrg = new Org();
                childOrg.setId(item);
                childOrg.setOrgCode(code);
                orgDao.updateById(childOrg);
            }
        });

        return AjaxResult.success();
    }

    /**
     * 同名机构校检
     */
    @Override
    public boolean isRepeat(String orgName, Integer orgId, Integer parentId) {
        List<Org> repeatOrg = orgDao.selectList(new QueryWrapper<Org>().eq(Org.NAME, orgName).eq(Org.PARENTID, parentId)
                .ne(null != orgId, Org.ID, orgId));
        return repeatOrg.size() > 0;
    }
    /******************************************************************************************************************/
    @Override
    public List<Integer> searchOrgMem(Integer orgId) {
        List<Integer> orgIds = new ArrayList<>();
        getChildOrgId(orgId, orgIds);
        List<User> users = userDao.selectList(new QueryWrapper<User>().in(0 != orgIds.size(), User.ORGID, orgIds));
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    /******************************************************************************************************************/
    public List<Org> getChildPerms(List<Org> list, int parentId) {
        List<Org> returnList = new ArrayList<Org>();
        for (Iterator<Org> iterator = list.iterator(); iterator.hasNext();) {
            Org t = (Org) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    private void recursionFn(List<Org> list, Org t) {
        // 得到子节点列表
        List<Org> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Org tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<Org> it = childList.iterator();
                while (it.hasNext()) {
                    Org n = (Org) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    private List<Org> getChildList(List<Org> list, Org t) {
        List<Org> tlist = new ArrayList<Org>();
        Iterator<Org> it = list.iterator();
        while (it.hasNext()) {
            Org n = (Org) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Org> list, Org t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 拼接org对象中的orgCode
     */
    private String combinOrgCode(Integer parentId, String orgCode) {
        if(1 == parentId)
            return "1." + orgCode;
        Org org = orgDao.selectById(parentId);
        orgCode = org.getId().toString() + "." + orgCode;
        return combinOrgCode(org.getParentId(), orgCode);
    }

    /**
     * 获取机构下附属id
     */
    private void getChildOrgId(Integer orgId, List<Integer> orgIds) {
        orgIds.add(orgId);
        List<Org> orgs = orgDao.selectList(new QueryWrapper<Org>().eq(Org.PARENTID, orgId));
        if(0 != orgs.size()) {
            orgs.forEach(item -> {
                getChildOrgId(item.getId(), orgIds);
            });
        }
    }
}

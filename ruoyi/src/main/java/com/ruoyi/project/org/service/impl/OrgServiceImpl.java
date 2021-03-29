package com.ruoyi.project.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.system.domain.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgDao, Org> implements IOrgService {

    @Autowired
    private OrgDao orgDao;

    @Override
    public List<Org> searchOrgTree() {
        List<Org> fatherOrg = orgDao.selectList(new QueryWrapper<Org>());
        return getChildPerms(fatherOrg, 0);
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
}

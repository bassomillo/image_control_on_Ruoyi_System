package com.ruoyi.project.democratic.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 工具类
 */
@Component
public class ToolUtils {
    public static ToolUtils toolUtils;

    @Autowired
    private OrgDao orgDao;

    /**
     * 生成组织架构名，格式xxxx/xxx/xxxx
     * @param orgId
     * @return
     */
    public String getOrgName(Integer orgId){
        String orgName = "";

        int parentId = 1;
        while (parentId != 0){
            if (orgId == null){
                break;
            }
            Org org = toolUtils.orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, orgId));
            parentId = org.getParentId();
            orgId = org.getParentId();
            orgName = "/" + org.getName() + orgName;
        }
        orgName = orgId == null ? orgName : orgName.substring(1);

        return orgName;
    }


    @PostConstruct
    public void init(){
        toolUtils = this;
    }
}

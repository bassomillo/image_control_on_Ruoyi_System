package com.ruoyi.project.democratic.tool;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

        if (orgId == null){
            return null;
        }
        List<Integer> orgInt = getOrgTreeInt(orgId);
        if (orgInt.size() == 0){
            return orgName;
        }

        List<Org> orgList = toolUtils.orgDao.selectList(new QueryWrapper<Org>().
                in(Org.ID, orgInt));
        for (Org o : orgList){
            orgName += o.getName() + "/";
        }
        orgName = orgName.substring(0, orgName.length()-1);

        return orgName;
    }

    /**
     * 查询当前组织所属的主席
     * @param orgId
     * @return
     */
    public JSONObject getChairmanId(Integer orgId){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("省主席", "");
        jsonObject.put("省副主席", "");
        jsonObject.put("省主席信箱管理人", "");
        jsonObject.put("市主席", "");
        jsonObject.put("市副主席", "");
        jsonObject.put("市主席信箱管理人", "");
        return jsonObject;
    }

    /**
     * 查询当前组织所属的总经理
     * @param orgId
     * @return
     */
    public JSONObject getManagerId(Integer orgId){
        JSONObject jsonObject = new JSONObject();

        if (orgId == null){
            return jsonObject;
        }
        //获取此部门组织架构树
        List<Integer> orgInt = getOrgTreeInt(orgId);

        //查省级
        for (Integer o : orgInt){

        }

        //查市级


        jsonObject.put("省总经理", "");
        jsonObject.put("省总经理秘书", "");
        jsonObject.put("市总经理", "");
        jsonObject.put("市总经理秘书", "");
        return jsonObject;
    }

    /**
     * 获取此部门组织架构树id
     * @param orgId
     * @return
     */
    public List<Integer> getOrgTreeInt(Integer orgId){
        Org org = toolUtils.orgDao.selectOne(new QueryWrapper<Org>().
                eq(Org.ID, orgId));
        String orgCode = org.getOrgCode().replace(".", ",");
        orgCode = orgCode.substring(0, orgCode.length()-1);
        String[] orgArray = orgCode.split(",");
        List<Integer> orgInt = new ArrayList<>();
        for (String s : orgArray){
            orgInt.add(Integer.valueOf(s));
        }
        return orgInt;
    }


    @PostConstruct
    public void init(){
        toolUtils = this;
    }
}

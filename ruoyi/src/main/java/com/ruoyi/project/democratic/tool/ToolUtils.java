package com.ruoyi.project.democratic.tool;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
@Component
public class ToolUtils {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private OrgCommissionerDao orgCommissionerDao;

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

        List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().
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
    public Map<String, List<Integer>> getChairmanId(Integer orgId){
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> province = new ArrayList<>();
        List<Integer> provincePre = new ArrayList<>();
        List<Integer> provinceBox = new ArrayList<>();
        List<Integer> city = new ArrayList<>();
        List<Integer> cityPre = new ArrayList<>();
        List<Integer> cityBox = new ArrayList<>();

        if (orgId == null){
            return map;
        }
        //获取此部门组织架构树
        List<Integer> orgInt = getOrgTreeInt(orgId);

        //查省级
        Integer cityCode = null;
        for (Integer oId : orgInt){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, oId));
            if ("市级分公司".equals(org.getOrgLevel())){
                cityCode = oId;
                break;
            }

            List<OrgCommissioner> commissioners = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                    eq(OrgCommissioner.ORGID, oId));
            for (OrgCommissioner comm : commissioners){
                if ("主席".equals(comm.getPosition())){
                    province.add(comm.getUserId());
                }else if ("副主席".equals(comm.getPosition())){
                    provincePre.add(comm.getUserId());
                }else if ("省主席信箱管理人".equals(comm.getPosition())){
                    provinceBox.add(comm.getUserId());
                }else if ("省主席".equals(comm.getPosition())){
                    province.add(comm.getUserId());
                }
            }
        }
        //查市级
        if (cityCode != null){
            List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().
                    like(Org.ORGCODE, cityCode));
            for (Org o : orgList){
                List<OrgCommissioner> commissioners = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                        eq(OrgCommissioner.ORGID, o.getId()));
                for (OrgCommissioner comm : commissioners){
                    if ("主席".equals(comm.getPosition())){
                        city.add(comm.getUserId());
                    }else if ("副主席".equals(comm.getPosition())){
                        cityPre.add(comm.getUserId());
                    }else if ("市主席信箱管理人".equals(comm.getPosition())){
                        cityBox.add(comm.getUserId());
                    }else if ("省主席信箱管理人".equals(comm.getPosition())){
                        provinceBox.add(comm.getUserId());
                    }
                }
            }
        }

        map.put("省主席", province);
        map.put("省副主席", provincePre);
        map.put("省主席信箱管理人", provinceBox);
        map.put("市主席", city);
        map.put("市副主席", cityPre);
        map.put("市主席信箱管理人", cityBox);
        return map;
    }

    /**
     * 查询当前组织所属的总经理
     * @param orgId
     * @return
     */
    public Map<String, List<Integer>> getManagerId(Integer orgId){
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> province = new ArrayList<>();
        List<Integer> provinceAssist = new ArrayList<>();
        List<Integer> city = new ArrayList<>();
        List<Integer> cityAssist = new ArrayList<>();

        if (orgId == null){
            return map;
        }
        //获取此部门组织架构树
        List<Integer> orgInt = getOrgTreeInt(orgId);

        Integer cityCode = null;
        //查省级
        for (Integer oId : orgInt){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, oId));
            if ("市级分公司".equals(org.getOrgLevel())){
                cityCode = oId;
                break;
            }

            List<OrgCommissioner> commissioners = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                    eq(OrgCommissioner.ORGID, oId));
            for (OrgCommissioner comm : commissioners){
                if ("省总经理".equals(comm.getPosition())){
                    province.add(comm.getUserId());
                }else if ("省总经理秘书".equals(comm.getPosition())){
                    provinceAssist.add(comm.getUserId());
                }
            }
        }
        //查市级
        if (cityCode != null){
            List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().
                    like(Org.ORGCODE, cityCode));
            for (Org o : orgList){
                List<OrgCommissioner> commissioners = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                        eq(OrgCommissioner.ORGID, o.getId()));
                for (OrgCommissioner comm : commissioners){
                    if ("市总经理".equals(comm.getPosition())){
                        city.add(comm.getUserId());
                    }else if ("市总经理秘书".equals(comm.getPosition())){
                        cityAssist.add(comm.getUserId());
                    }else if ("省总经理秘书".equals(comm.getPosition())){
                        provinceAssist.add(comm.getUserId());
                    }
                }
            }
        }

        map.put("省总经理", province);
        map.put("省总经理秘书", provinceAssist);
        map.put("市总经理", city);
        map.put("市总经理秘书", cityAssist);
        return map;
    }

    /**
     * 获取此部门组织架构树id
     * @param orgId
     * @return
     */
    public List<Integer> getOrgTreeInt(Integer orgId){
        Org org = orgDao.selectOne(new QueryWrapper<Org>().
                eq(Org.ID, orgId));
        if (org == null){
            return new ArrayList<>();
        }
        String orgCode = org.getOrgCode().replace(".", ",");
        orgCode = orgCode.substring(0, orgCode.length()-1);
        String[] orgArray = orgCode.split(",");
        List<Integer> orgInt = new ArrayList<>();
        for (String s : orgArray){
            orgInt.add(Integer.valueOf(s));
        }
        return orgInt;
    }

}

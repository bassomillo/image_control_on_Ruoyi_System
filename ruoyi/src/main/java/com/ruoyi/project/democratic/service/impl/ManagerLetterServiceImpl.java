package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.entity.VO.ManagerLetterBackVO;
import com.ruoyi.project.democratic.mapper.ManagerLetterMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.ManagerLetterService;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 总经理信箱 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
@Service
public class ManagerLetterServiceImpl extends ServiceImpl<ManagerLetterMapper, ManagerLetterBox> implements ManagerLetterService {

    @Autowired
    private ManagerLetterMapper managerLetterMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;
    @Autowired
    private OrgCommissionerDao orgCommissionerDao;
    @Autowired
    private OrgDao orgDao;

    @Resource
    private ToolUtils toolUtils;

    /**
     * 获取总经理信息
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getLetterMan(Integer userId) {
        try {
            //查询总经理id
            User user = userDao.selectOne(new QueryWrapper<User>().
                    eq(User.ID, userId));
            Map<String, List<Integer>> m1 = toolUtils.getManagerId(user.getOrgId());
            Integer province = m1.get("省总经理").size()==0 ? null : m1.get("省总经理").get(0);
            Integer city = m1.get("市总经理").size()==0 ? null : m1.get("市总经理").get(0);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("province", province);
            jsonObject.put("city", city);

            return AjaxResult.success("获取成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("获取失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页-发送信件
     * @param letterBox
     * @param fileList
     * @return
     */
    @Override
    public AjaxResult insertManagerLetter(ManagerLetterBox letterBox, List<Integer> fileList) {
        try {
            letterBox.setCreateDate(new Date());
            save(letterBox);

            if (fileList != null && fileList.size() > 0){
                List<UploadFiles> uploadFiles = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, fileList));
                for (UploadFiles files : uploadFiles){
                    files.setTargetId(letterBox.getId());
                    files.setTargetType("manager");
                    uploadFilesMapper.updateById(files);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查询回复记录列表-首页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopManagerList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            //分页插件
            PageHelper.startPage(pageNum, pageSize);
            List<ManagerLetterBox> letterBoxList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.FROMID, userId).
                    eq(ManagerLetterBox.PARENTID, 0).
                    orderByDesc(ManagerLetterBox.CREATEDATE));
            PageInfo pageInfo = new PageInfo<>(letterBoxList, pageSize);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 根据id查详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getManagerDetailById(Integer id) {
        try {
            //查记录详情
            ManagerLetterBox letterBox = managerLetterMapper.selectOne(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.ID, id));
            //查回复详情
            List<ManagerLetterBox> requireList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.PARENTID, id).
                    orderByAsc(ManagerLetterBox.CREATEDATE));
            //查附件
            List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.TARGETTYPE, "manager").
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", letterBox);
            jsonObject.put("require", requireList);
            jsonObject.put("file", filesList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 评价回复
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    @Override
    public AjaxResult evaluate(String evaluate, String evaluateContent, Integer requireId) {
        try {
            ManagerLetterBox letterBox = managerLetterMapper.selectOne(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.ID, requireId));
            letterBox.setEvaluate(evaluate);
            letterBox.setEvaluateContent(evaluateContent);
            updateById(letterBox);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("评价失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("评价成功");
    }

    /**
     * 条件查询
     * @param content
     * @param year
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackLetterList(String content, Integer year, Integer userId, Integer pageNum, Integer pageSize) {
        try {
            content = Str.fuzzyQuery(content);
            List<String> provinceInt = getProvinceManager(userId);
            List<String> cityInt = getCityManager(userId);
            Integer isAdmin = getIsAdmin(userId);

            PageHelper.startPage(pageNum, pageSize);
            List<ManagerLetterBackVO> letterBoxList = managerLetterMapper.getLetterList(content, year, provinceInt, cityInt, isAdmin);
            //生成所属组织架构名
            for (ManagerLetterBackVO letter : letterBoxList){
                String orgName = toolUtils.getOrgName(letter.getOrgId());
                letter.setOrgName(orgName);
            }
            PageInfo pageInfo = new PageInfo<>(letterBoxList);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }



    /**
     * 查询当前用户身份，1为超管，2不为超管
     * @param userId
     * @return
     */
    private Integer getIsAdmin(Integer userId){
        Integer isAdmin = 2;
        //管理员
        SysRole sysRole = managerLetterMapper.getUserRole(userId);
        if (sysRole != null && "ROLE_SUPER_ADMIN".equals(sysRole.getRoleKey())){
            isAdmin = 1;
        }

        return isAdmin;
    }

    /**
     * 获取省总经理对应部门节点
     * @param userId
     * @return
     */
    private List<String> getProvinceManager(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            if ("省总经理".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省总经理秘书".equals(comm.getPosition())){
                province.add(comm);
            }
        }

        List<String> provinceInt = new ArrayList<>();
        //省总经理+省总经理秘书
        for (OrgCommissioner comm : province){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在省级下被任命，则直接记录；否则层层寻找其省级节点
            if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                provinceInt.add(org.getId().toString() + ".");
            }else {
                List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
                for (Integer id : orgTreeIds){
                    org = orgDao.selectOne(new QueryWrapper<Org>().
                            eq(Org.ID, id));
                    if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                        provinceInt.add(org.getId().toString() + ".");
                        break;
                    }
                }
            }
        }

        return provinceInt;
    }

    /**
     * 获取市总经理对应部门节点
     * @param userId
     * @return
     */
    private List<String> getCityManager(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        List<OrgCommissioner> city = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            if ("省总经理".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省总经理秘书".equals(comm.getPosition())){
                province.add(comm);
            }else if ("市总经理".equals(comm.getPosition())){
                city.add(comm);
            }else if ("市总经理秘书".equals(comm.getPosition())){
                city.add(comm);
            }
        }

        List<String> cityInt = new ArrayList<>();
        //市总经理+市总经理秘书
        for (OrgCommissioner comm : city){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在市级下被任命，则直接记录；否则层层寻找其市级节点
            if ("市级分公司".equals(org.getOrgLevel())){
                cityInt.add(org.getId().toString() + ".");
            }else {
                List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
                for (Integer id : orgTreeIds){
                    org = orgDao.selectOne(new QueryWrapper<Org>().
                            eq(Org.ID, id));
                    if ("市级分公司".equals(org.getOrgLevel())){
                        cityInt.add(org.getId().toString() + ".");
                        break;
                    }
                }
            }
        }

        return cityInt;
    }
}

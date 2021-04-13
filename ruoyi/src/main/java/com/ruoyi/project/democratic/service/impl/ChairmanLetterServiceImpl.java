package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.mapper.ChairmanLetterMapper;
import com.ruoyi.project.democratic.mapper.ManagerLetterMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.ChairmanLetterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.tool.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主席信箱表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
@Service
public class ChairmanLetterServiceImpl extends ServiceImpl<ChairmanLetterMapper, ChairmanLetterBox> implements ChairmanLetterService {

    @Autowired
    private ChairmanLetterMapper chairmanLetterMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;
    @Autowired
    private ManagerLetterMapper managerLetterMapper;
    @Autowired
    private OrgCommissionerDao orgCommissionerDao;
    @Autowired
    private OrgDao orgDao;

    @Resource
    private ToolUtils toolUtils;

    /**
     * 获取写信对象id
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getLetterMan(Integer userId) {
        try {
            Map<String, List<Integer>> map = toolUtils.getChairmanId(userId);
            List<Integer> province = map.get("省主席");
            List<Integer> city = map.get("市主席");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("省主席", province.size()==0 ? null : province.get(0));
            jsonObject.put("市主席", city.size()==0 ? null : city.get(0));

            return AjaxResult.success("获取成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("获取失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 发送信件
     * @param letterBox
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertChairmanLetter(ChairmanLetterBox letterBox) {
        try {
            letterBox.setCreateDate(new Date());
            save(letterBox);

            if (letterBox.getFileList() != null && letterBox.getFileList().size() > 0){
                List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, letterBox.getFileList()));
                for (UploadFiles file : filesList){
                    file.setTargetId(letterBox.getId());
                    file.setTargetType("chairman");
                    uploadFilesMapper.updateById(file);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查询首页列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopChairmanList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<ChairmanLetterBox> letterList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.FROMID, userId).
                    eq(ChairmanLetterBox.PARENTID, 0).
                    orderByDesc(ChairmanLetterBox.CREATEDATE));
            PageInfo pageInfo = new PageInfo<>(letterList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", pageInfo.getList());
            jsonObject.put("total", pageInfo.getTotal());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 查询记录详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getChairmanDetailById(Integer id) {
        try {
            //查记录详情
            ChairmanLetterBox letter = chairmanLetterMapper.selectOne(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.ID, id));
            //查回复列表
            List<ChairmanLetterBox> replyList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.PARENTID, id).
                    orderByDesc(ChairmanLetterBox.CREATEDATE));
            //查附件
            List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETTYPE, "chairman").
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", letter);
            jsonObject.put("require", replyList);
            jsonObject.put("file", filesList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页-回复的评价
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    @Override
    public AjaxResult evaluate(String evaluate, String evaluateContent, Integer requireId) {
        try {
            ChairmanLetterBox letterBox = chairmanLetterMapper.selectOne(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.ID, requireId));
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
     * 条件查询后台列表
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
            List<String> provinceInt = getChairmanProvince(userId);
            List<String> cityInt = getChairmanCity(userId);
            Integer isAdmin = getIsAdmin(userId);

            return AjaxResult.success("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }


    /**
     * 判断是否为超管，1为超管，2不为超管
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
     * 获取省主席/省副主席/省主席信箱管理人 对应部门节点
     * @param userId
     * @return
     */
    private List<String> getChairmanProvince(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            if ("省主席".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省副主席".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省主席信箱管理人".equals(comm.getPosition())){
                province.add(comm);
            }else if ("主席".equals(comm.getPosition())){

            }else if ("副主席".equals(comm.getPosition())){

            }
        }

        List<String> provinceInt = new ArrayList<>();
        //省主席+省副主席+省主席信箱管理人
        for (OrgCommissioner comm : province){

        }

        return provinceInt;
    }

    /**
     * 获取市主席/市副主席/市主席信箱管理人 对应部门节点
     * @param userId
     * @return
     */
    private List<String> getChairmanCity(Integer userId){


        List<String> cityInt = new ArrayList<>();

        return cityInt;
    }
}

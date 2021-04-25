package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ExamMember;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.democratic.entity.VO.MemberInfoVO;
import com.ruoyi.project.democratic.entity.VoteGroupMember;
import com.ruoyi.project.democratic.mapper.ExamMemberMapper;
import com.ruoyi.project.democratic.mapper.VoteGroupMemberMapper;
import com.ruoyi.project.democratic.service.IExamMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考试成员表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
@Service
public class ExamMemberServiceImpl extends ServiceImpl<ExamMemberMapper, ExamMember> implements IExamMemberService {

    @Autowired
    private ExamMemberMapper examMemberMapper;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private VoteGroupMemberMapper voteGroupMemberMapper;

    /**
     * 获取组织树下人员
     * @param getMember
     * @return
     */
    @Override
    public AjaxResult getTreeMember(GetMemberVO getMember) {
        try {
            //获得组织树下人员id列表
            List<Integer> userIdList = new ArrayList<>();
            for (Integer id : getMember.getIdList()){
                userIdList.addAll(orgService.searchOrgMem(id));
            }
            if (userIdList.size() == 0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("total", 0);
                jsonObject.put("list", new ArrayList<>());

                return AjaxResult.success("获取成功", jsonObject);
            }

            getMember.setMobile(Str.fuzzyQuery(getMember.getMobile()));
            getMember.setName(Str.fuzzyQuery(getMember.getName()));
            getMember.setEmploymentForm(Str.fuzzyQuery(getMember.getEmploymentForm()));
            //查询人员信息
            PageHelper.startPage(getMember.getPageNum(), getMember.getPageSize());
            List<MemberInfoVO> userList0 = examMemberMapper.selectMemberInfo(userIdList, getMember.getName(),
                    getMember.getEmploymentForm(), getMember.getMobile());
            PageInfo pageInfo = new PageInfo<>(userList0);

            List<MemberInfoVO> memberList = pageInfo.getList();
            for (MemberInfoVO member : memberList){
                //查询成员是否已在考试中
                ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                        eq(ExamMember.USERID, member.getId()).
                        eq(ExamMember.EXAMID, getMember.getExamId()));
                if (examMember == null){
                    member.setIsAdd(0);
                }else {
                    member.setIsAdd(1);
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", memberList);

            return AjaxResult.success("获取成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("获取失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 添加单个/多个人员到考试
     * @param treeMember
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult addMember(AddTreeMemberVO treeMember) {
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer userId : treeMember.getUserIdList()){
                //查询该成员是否已添加至考试
                ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                        eq(ExamMember.EXAMID, treeMember.getExamId()).
                        eq(ExamMember.USERID, userId));
                if (examMember == null){
                    idList.add(userId);
                }
            }

            if (idList.size() > 0){
                List<UserProfile> profileList = userProfileDao.selectList(new QueryWrapper<UserProfile>().
                        in(UserProfile.ID, idList));
                List<ExamMember> memberList = new ArrayList<>();
                for (UserProfile profile : profileList){
                    ExamMember examMember = new ExamMember();
                    examMember = new ExamMember();
                    examMember.setExamId(treeMember.getExamId());
                    examMember.setUserId(profile.getId());
                    examMember.setTel(profile.getMobile());
                    memberList.add(examMember);
                }
                saveBatch(memberList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("添加失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 添加全部人员至考试
     * @param getMember
     * @return
     */
    @Override
    public AjaxResult addAllMember(GetMemberVO getMember) {
        try {
            //获得组织树下人员id列表
            List<Integer> userIdList = new ArrayList<>();
            for (Integer id : getMember.getIdList()){
                userIdList.addAll(orgService.searchOrgMem(id));
            }

            getMember.setMobile(Str.fuzzyQuery(getMember.getMobile()));
            getMember.setName(Str.fuzzyQuery(getMember.getName()));
            getMember.setEmploymentForm(Str.fuzzyQuery(getMember.getEmploymentForm()));
            //查询人员信息
            List<MemberInfoVO> userList0 = examMemberMapper.selectMemberInfo(userIdList, getMember.getName(),
                    getMember.getEmploymentForm(), getMember.getMobile());

            List<MemberInfoVO> userIdList0 = new ArrayList<>();
            for (MemberInfoVO user : userList0){
                //查询该人员是否已经加入考试
                ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                        eq(ExamMember.USERID, user.getId()).
                        eq(ExamMember.EXAMID, getMember.getExamId()));
                if (examMember == null){
                    userIdList0.add(user);
                }
            }

            List<ExamMember> memberList = new ArrayList<>();
            for (MemberInfoVO member : userIdList0){
                ExamMember examMember = new ExamMember();
                examMember = new ExamMember();
                examMember.setExamId(getMember.getExamId());
                examMember.setUserId(member.getId());
                examMember.setTel(member.getMobile());
                memberList.add(examMember);
            }
            saveBatch(memberList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("添加失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 添加单个/多个人员到分组
     * @param treeMember
     * @return
     */
    @Override
    public AjaxResult addMemberGroup(AddTreeMemberVO treeMember) {
        try{
            for (Integer id : treeMember.getUserIdList()){
                //查询是否有人员已在分组
                VoteGroupMember groupMember = voteGroupMemberMapper.selectOne(new QueryWrapper<VoteGroupMember>().
                        eq(VoteGroupMember.GROUPID, treeMember.getGroupId()).
                        eq(VoteGroupMember.USERID, id));
                if (groupMember == null){
                    groupMember = new VoteGroupMember();
                    groupMember.setUserId(id);
                    groupMember.setGroupId(treeMember.getGroupId());
                    voteGroupMemberMapper.insert(groupMember);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("添加失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 添加全部人员到分组
     * @param getMember
     * @return
     */
    @Override
    public AjaxResult addAllMemberGroup(GetMemberVO getMember) {
        try {
            //获得组织树下人员id列表
            List<Integer> userIdList = new ArrayList<>();
            for (Integer id : getMember.getIdList()){
                userIdList.addAll(orgService.searchOrgMem(id));
            }

            getMember.setMobile(Str.fuzzyQuery(getMember.getMobile()));
            getMember.setName(Str.fuzzyQuery(getMember.getName()));
            getMember.setEmploymentForm(Str.fuzzyQuery(getMember.getEmploymentForm()));
            //查询人员信息
            List<MemberInfoVO> userList0 = examMemberMapper.selectMemberInfo(userIdList, getMember.getName(),
                    getMember.getEmploymentForm(), getMember.getMobile());

            for (MemberInfoVO member : userList0){
                //查询该人员是否已经加入分组
                VoteGroupMember groupMember = voteGroupMemberMapper.selectOne(new QueryWrapper<VoteGroupMember>().
                        eq(VoteGroupMember.USERID, member.getId()).
                        eq(VoteGroupMember.GROUPID, getMember.getGroupId()));
                if (groupMember == null){
                    groupMember = new VoteGroupMember();
                    groupMember.setGroupId(getMember.getGroupId());
                    groupMember.setUserId(member.getId());
                    voteGroupMemberMapper.insert(groupMember);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("添加失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 人员导入
     * @param file
     * @param request
     * @return
     */
    @Override
    public AjaxResult importMember(MultipartFile file, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        try {
            List<String> msg = new ArrayList<>();

            Workbook wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            ExcelTool.excelCheck(sheet, msg, rowStart, rowEnd);
            if (msg.size() != 0){
                return AjaxResult.error("第一行没有数据或没有填写数据");
            }

            List<Integer> rowMsg = new ArrayList<>();
            List<String> mobileMsg = new ArrayList<>();
            List<MemberInfoVO> memberList = new ArrayList<>();
            for (int i = rowStart; i < rowEnd; i++){
                Row row = sheet.getRow(i);

                //校验手机号对应用户是否存在
                String name = ExcelTool.getCellValue(row.getCell(0));
                String mobile = ExcelTool.getCellValue(row.getCell(1));
                if (StringUtils.isBlank(name) && StringUtils.isBlank(mobile)){
                    continue;
                }else if (StringUtils.isBlank(name) || StringUtils.isBlank(mobile)){
                    rowMsg.add(i + 1);
                    continue;
                }
                MemberInfoVO member = examMemberMapper.selectUserByMobile(mobile);
                if (member == null){
                    mobileMsg.add(mobile);
                    continue;
                }
                memberList.add(member);
            }

            //分页
            List<MemberInfoVO> memberInfoList = new ArrayList<>();
            int total = memberList.size();
            int maxRow = pageNum * pageSize;
            int minRow = (pageNum - 1) * pageSize ;
            if(total != 0 && total > minRow)  {
                maxRow = total >= maxRow ? maxRow : total;
                memberInfoList = memberList.subList(minRow, maxRow);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalList", memberList);
            jsonObject.put("total", total);
            jsonObject.put("list", memberInfoList);

            //判断返回的消息
            String result = "";
            String rowNum = "";
            for (int i = 0; i < rowMsg.size(); i++){
                rowNum += rowMsg.get(i).toString();
                if (i+1 != rowMsg.size()){
                    rowNum += ",";
                }
            }
            if (!"".equals(rowNum)){
                result += "第【" + rowNum + "】行的数据填写不完整，请重新填写\n";
            }
            rowNum = "";
            for (int i = 0; i < mobileMsg.size(); i++){
                rowNum += mobileMsg.get(i);
                if (i+1 != mobileMsg.size()){
                    rowNum += ",";
                }
            }
            if (!"".equals(rowNum)){
                result += "手机号为【" + rowNum + "】的用户不在数据库中或被禁用暂时无法导入";
            }

            return AjaxResult.success("".equals(result) ? "导入成功" : result, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导入失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 条件查询参考人员列表
     * @param examId
     * @param name
     * @param employmentForm
     * @param mobile
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getExamMemberList(Integer examId, String name, String employmentForm, String mobile, Integer pageNum, Integer pageSize) {
        try {
            name = Str.fuzzyQuery(name);
            employmentForm = Str.fuzzyQuery(employmentForm);
            mobile = Str.fuzzyQuery(mobile);

            PageHelper.startPage(pageNum, pageSize);
            List<MemberInfoVO> memberList = examMemberMapper.selectExamMemberList(examId, name, employmentForm, mobile);
            PageInfo pageInfo = new PageInfo<>(memberList);
            List<MemberInfoVO> memberList0 = examMemberMapper.selectExamMemberList(examId, name, employmentForm, mobile);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());
            jsonObject.put("totalList", memberList0);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 删除参考人员
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult deleteExamMember(Integer examId, Integer userId) {
        try {
            examMemberMapper.delete(new QueryWrapper<ExamMember>().
                    eq(ExamMember.EXAMID, examId).
                    eq(ExamMember.USERID, userId));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 加入考试成员
     * @param memberList
     * @param examId
     * @return
     */
    @Override
    public boolean insertExamMember(List<UserProfile> memberList, Integer examId) {
        if (memberList == null || memberList.size() == 0){
            return true;
        }
        List<ExamMember> examMembers = new ArrayList<>();
        for (UserProfile profile : memberList){
            ExamMember examMember = new ExamMember();
            examMember.setExamId(examId);
            examMember.setUserId(profile.getId());
            examMember.setTel(profile.getMobile());
            examMembers.add(examMember);
        }
        boolean flag = saveBatch(examMembers);
        return flag;
    }
}

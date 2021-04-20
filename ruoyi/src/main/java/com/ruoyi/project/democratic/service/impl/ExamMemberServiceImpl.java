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
import com.ruoyi.project.democratic.mapper.ExamMemberMapper;
import com.ruoyi.project.democratic.service.IExamMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

            //查询人员信息
            getMember.setMobile(Str.fuzzyQuery(getMember.getMobile()));
            getMember.setName(Str.fuzzyQuery(getMember.getName()));
            getMember.setEmploymentForm(Str.fuzzyQuery(getMember.getEmploymentForm()));
            PageHelper.startPage(getMember.getPageNum(), getMember.getPageSize());
            List<User> userList0 = userDao.selectList(new QueryWrapper<User>().
                    in(User.ID, userIdList).
                    eq(User.LOCKED, 0).
                    orderByDesc(User.ID));
            PageInfo pageInfo = new PageInfo<>(userList0);
            List<User> userList1 = pageInfo.getList();
            List<Integer> intList = new ArrayList<>();
            for (User user : userList1){
                intList.add(user.getId());
            }

            //查询真实姓名
            List<UserProfile> profileList = new ArrayList<>();
            if (intList.size() > 0) {
                profileList = userProfileDao.selectList(new QueryWrapper<UserProfile>().
                        in(UserProfile.ID, intList).
                        orderByDesc(UserProfile.ID));
            }
            List<MemberInfoVO> memberList = new ArrayList<>();
            for (UserProfile profile : profileList){
                MemberInfoVO member = new MemberInfoVO();
                member.setId(profile.getId());
                member.setTruename(profile.getTruename());
                member.setMobile(profile.getMobile());
                member.setEmploymentForm(profile.getEmploymentForm());
                //查询成员是否已在考试中
                ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                        eq(ExamMember.USERID, profile.getId()).
                        eq(ExamMember.EXAMID, getMember.getExamId()));
                if (examMember == null){
                    member.setIsAdd(0);
                }else {
                    member.setIsAdd(1);
                }

                memberList.add(member);
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

            List<Integer> userIdList0 = new ArrayList<>();
            for (Integer userId : userIdList){
                //查询该人员是否已经加入考试
                ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                        eq(ExamMember.USERID, userId).
                        eq(ExamMember.EXAMID, getMember.getExamId()));
                if (examMember == null){
                    userIdList0.add(userId);
                }
            }
            if (userIdList0.size() > 0){
                List<UserProfile> profileList = userProfileDao.selectList(new QueryWrapper<UserProfile>().
                        in(UserProfile.ID, userIdList0));
                List<ExamMember> memberList = new ArrayList<>();
                for (UserProfile profile : profileList){
                    ExamMember examMember = new ExamMember();
                    examMember = new ExamMember();
                    examMember.setExamId(getMember.getExamId());
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

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
import com.ruoyi.project.democratic.entity.VoteMember;
import com.ruoyi.project.democratic.mapper.ExamMemberMapper;
import com.ruoyi.project.democratic.mapper.VoteMemberMapper;
import com.ruoyi.project.democratic.service.IVoteMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 投票/问卷成员表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-29
 */
@Service
public class VoteMemberServiceImpl extends ServiceImpl<VoteMemberMapper, VoteMember> implements IVoteMemberService {

    @Autowired
    private VoteMemberMapper voteMemberMapper;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private ExamMemberMapper examMemberMapper;

    /**
     * 添加单个/多个人员至问卷
     * @param treeMember
     * @return
     */
    @Override
    public AjaxResult addMemberQ(AddTreeMemberVO treeMember) {
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer userId : treeMember.getUserIdList()){
                //查询该成员是否已添加至问卷
                VoteMember voteMember = voteMemberMapper.selectOne(new QueryWrapper<VoteMember>().
                        eq(VoteMember.VOTEID, treeMember.getEqvId()).
                        eq(VoteMember.USERID, userId).
                        eq(VoteMember.TYPE, "questionnaire"));
                if (voteMember == null){
                    idList.add(userId);
                }
            }

            if (idList.size() > 0){
                List<UserProfile> profileList = userProfileDao.selectList(new QueryWrapper<UserProfile>().
                        in(UserProfile.ID, idList));
                List<VoteMember> memberList = new ArrayList<>();
                for (UserProfile profile : profileList){
                    VoteMember voteMember = new VoteMember();
                    voteMember.setVoteId(treeMember.getEqvId());
                    voteMember.setUserId(profile.getId());
                    voteMember.setTel(profile.getMobile());
                    voteMember.setType("questionnaire");
                    memberList.add(voteMember);
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
     * 添加全部人员至问卷
     * @param getMember
     * @return
     */
    @Override
    public AjaxResult addAllMemberQ(GetMemberVO getMember) {
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
                //查询该人员是否已经加入问卷
                VoteMember voteMember = voteMemberMapper.selectOne(new QueryWrapper<VoteMember>().
                        eq(VoteMember.USERID, user.getId()).
                        eq(VoteMember.VOTEID, getMember.getEqvId()).
                        eq(VoteMember.TYPE, "questionnaire"));
                if (voteMember == null){
                    userIdList0.add(user);
                }
            }

            List<VoteMember> memberList = new ArrayList<>();
            for (MemberInfoVO member : userIdList0){
                VoteMember voteMember = new VoteMember();
                voteMember.setVoteId(getMember.getEqvId());
                voteMember.setUserId(member.getId());
                voteMember.setTel(member.getMobile());
                voteMember.setType("questionnaire");
                memberList.add(voteMember);
            }
            saveBatch(memberList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("添加失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 条件查询参考人员列表
     * @param quId
     * @param name
     * @param employmentForm
     * @param mobile
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getQuMemberList(Integer quId, String name, String employmentForm, String mobile, Integer pageNum, Integer pageSize) {
        try {
            name = Str.fuzzyQuery(name);
            employmentForm = Str.fuzzyQuery(employmentForm);
            mobile = Str.fuzzyQuery(mobile);

            PageHelper.startPage(pageNum, pageSize);
            List<MemberInfoVO> memberList = voteMemberMapper.selectQuMemberList(quId, name, employmentForm, mobile);
            PageInfo pageInfo = new PageInfo<>(memberList);
            List<MemberInfoVO> memberList0 = voteMemberMapper.selectQuMemberList(quId, name, employmentForm, mobile);

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
     * @param quId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult deleteQuMember(Integer quId, Integer userId) {
        try {
            voteMemberMapper.delete(new QueryWrapper<VoteMember>().
                    eq(VoteMember.VOTEID, quId).
                    eq(VoteMember.USERID, userId).
                    eq(VoteMember.TYPE, "questionnaire"));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 加入问卷成员
     * @param memberList
     * @param quId
     * @return
     */
    @Override
    public boolean insertQuMember(List<UserProfile> memberList, Integer quId) {
        if (memberList == null || memberList.size() == 0){
            return true;
        }
        List<VoteMember> quMembers = new ArrayList<>();
        for (UserProfile profile : memberList){
            VoteMember voteMember = new VoteMember();
            voteMember.setVoteId(quId);
            voteMember.setUserId(profile.getId());
            voteMember.setTel(profile.getMobile());
            voteMember.setType("questionnaire");
            quMembers.add(voteMember);
        }
        boolean flag = saveBatch(quMembers);
        return flag;
    }
}

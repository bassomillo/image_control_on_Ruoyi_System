package com.ruoyi.project.democratic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ExamMember;
import com.ruoyi.project.democratic.entity.VO.GroupToExamVO;
import com.ruoyi.project.democratic.entity.VoteGroup;
import com.ruoyi.project.democratic.entity.VoteGroupMember;
import com.ruoyi.project.democratic.mapper.ExamMemberMapper;
import com.ruoyi.project.democratic.mapper.VoteGroupMapper;
import com.ruoyi.project.democratic.mapper.VoteGroupMemberMapper;
import com.ruoyi.project.democratic.service.IExamMemberService;
import com.ruoyi.project.democratic.service.IVoteGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 投票人员分组表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-19
 */
@Service
public class VoteGroupServiceImpl extends ServiceImpl<VoteGroupMapper, VoteGroup> implements IVoteGroupService {

    @Autowired
    private VoteGroupMapper voteGroupMapper;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private VoteGroupMemberMapper voteGroupMemberMapper;
    @Autowired
    private ExamMemberMapper examMemberMapper;
    @Autowired
    private IExamMemberService examMemberService;

    /**
     * 新增分组
     * @param groupName
     * @param userId
     * @return
     */
    @Override
    public AjaxResult insertGroup(String groupName, Integer userId) {
        try {
            VoteGroup group = new VoteGroup();
            group.setCreateUserId(userId);
            group.setName(groupName);
            //获得时间戳
            long time = System.currentTimeMillis() / 1000;
            group.setCreatedTime(Integer.valueOf(Long.toString(time)));

            save(group);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("新增失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("新增成功");
    }

    /**
     * 查询分组
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getGroup(Integer userId) {
        try {
            List<VoteGroup> groupList = voteGroupMapper.selectList(new QueryWrapper<VoteGroup>().
                    eq(VoteGroup.CREATEUSERID, userId));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (VoteGroup group : groupList){
                //转换时间戳
                long time = group.getCreatedTime().longValue();
                String date = sdf.format(new Date(time * 1000L));
                group.setCreateDate(date);
                //获取人员信息
                List<VoteGroupMember> userList = voteGroupMemberMapper.selectList(new QueryWrapper<VoteGroupMember>().
                        eq(VoteGroupMember.GROUPID, group.getId()));
                for (VoteGroupMember user : userList){
                    UserProfile profile = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                            eq(UserProfile.ID, user.getUserId()));
                    user.setTruename(profile.getTruename());
                }
                group.setUserList(userList);
            }

            return AjaxResult.success("查询成功", groupList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 重命名分组
     * @param groupName
     * @param groupId
     * @return
     */
    @Override
    public AjaxResult renameGroup(String groupName, Integer groupId) {
        try {
            VoteGroup group = voteGroupMapper.selectOne(new QueryWrapper<VoteGroup>().
                    eq(VoteGroup.ID, groupId));
            group.setName(groupName);
            updateById(group);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("重命名失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("重命名成功");
    }

    /**
     * 删除分组
     * @param groupId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult deleteGroup(Integer groupId) {
        try {
            voteGroupMapper.deleteById(groupId);
            voteGroupMemberMapper.delete(new QueryWrapper<VoteGroupMember>().
                    eq(VoteGroupMember.GROUPID, groupId));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 删除分组人员
     * @param groupId
     * @param memberId
     * @return
     */
    @Override
    public AjaxResult deleteGroupMember(Integer groupId, Integer memberId) {
        try {
            voteGroupMemberMapper.delete(new QueryWrapper<VoteGroupMember>().
                    eq(VoteGroupMember.GROUPID, groupId).
                    eq(VoteGroupMember.USERID, memberId));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 加入考试
     * @param group
     * @return
     */
    @Override
    public AjaxResult addToExam(GroupToExamVO group) {
        try {
            List<Integer> groupList = group.getGroupIdList();
            List<Integer> idList = new ArrayList<>();

            for (Integer groupId : groupList){
                //查询分组下人员
                List<VoteGroupMember> groupMemberList = voteGroupMemberMapper.selectList(new QueryWrapper<VoteGroupMember>().
                        eq(VoteGroupMember.GROUPID, groupId));
                for (VoteGroupMember groupMember : groupMemberList){
                    //查是否有用户已被加入考试
                    ExamMember examMember = examMemberMapper.selectOne(new QueryWrapper<ExamMember>().
                            eq(ExamMember.EXAMID, group.getExamId()).
                            eq(ExamMember.USERID, groupMember.getUserId()));
                    //不存在，放入新增列表中
                    if (examMember == null){
                        idList.add(groupMember.getUserId());
                    }
                }
            }
            //查这部分新增人员的信息
            if (idList.size() > 0) {
                List<UserProfile> profileList = userProfileDao.selectList(new QueryWrapper<UserProfile>().
                        in(UserProfile.ID, idList));

                boolean flag = examMemberService.insertExamMember(profileList, group.getExamId());
                if (!flag) {
                    return AjaxResult.error("加入失败，请联系管理员");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("加入失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("加入成功");
    }
}

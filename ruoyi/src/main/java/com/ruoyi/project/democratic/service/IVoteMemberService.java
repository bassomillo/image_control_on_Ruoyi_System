package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.democratic.entity.VoteMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.union.entity.UserProfile;

import java.util.List;

/**
 * <p>
 * 投票/问卷成员表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-29
 */
public interface IVoteMemberService extends IService<VoteMember> {

    /**
     * 添加单个/多个人员至问卷
     * @param treeMember
     * @return
     */
    AjaxResult addMemberQ(AddTreeMemberVO treeMember);

    /**
     * 添加全部人员至问卷
     * @param getMember
     * @return
     */
    AjaxResult addAllMemberQ(GetMemberVO getMember);

    /**
     * 条件查询问卷参考人员列表
     * @param quId
     * @param name
     * @param employmentForm
     * @param mobile
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getQuMemberList(Integer quId,
                               String name,
                               String employmentForm,
                               String mobile,
                               Integer pageNum,
                               Integer pageSize);

    /**
     * 删除问卷参考人员
     * @param quId
     * @param userId
     * @return
     */
    AjaxResult deleteQuMember(Integer quId,
                              Integer userId);

    /**
     * 加入问卷成员
     * @param memberList
     * @param quId
     * @return
     */
    boolean insertQuMember(List<UserProfile> memberList,
                           Integer quId);

    /**************************投票****************************/

    /**
     * 添加单个/多个人员至投票
     * @param treeMember
     * @return
     */
    AjaxResult addMemberVote(AddTreeMemberVO treeMember);

    /**
     * 添加全部人员至投票
     * @param getMember
     * @return
     */
    AjaxResult addAllMemberVote(GetMemberVO getMember);

    /**
     * 条件查询投票参考人员列表
     * @param voteId
     * @param name
     * @param employmentForm
     * @param mobile
     * @param pageNum
     * @param pageSize
     * @return
     */
    AjaxResult getVoteMemberList(Integer voteId,
                                 String name,
                                 String employmentForm,
                                 String mobile,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 删除投票参考人员
     * @param voteId
     * @param userId
     * @return
     */
    AjaxResult deleteVoteMember(Integer voteId,
                                Integer userId);

    /**
     * 加入投票成员
     * @param memberList
     * @param voteId
     * @return
     */
    boolean insertVoteMember(List<UserProfile> memberList,
                             Integer voteId);
}

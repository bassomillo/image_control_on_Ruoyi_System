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
     * 条件查询参考人员列表
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
     * 删除参考人员
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
}

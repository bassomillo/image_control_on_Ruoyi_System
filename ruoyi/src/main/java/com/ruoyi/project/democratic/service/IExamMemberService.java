package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ExamMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.union.entity.UserProfile;

import java.util.List;

/**
 * <p>
 * 考试成员表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
public interface IExamMemberService extends IService<ExamMember> {

    /**
     * 查询组织树下人员
     * @param getMember
     * @return
     */
    AjaxResult getTreeMember(GetMemberVO getMember);

    /**
     * 添加单个/多个人员到考试
     * @param treeMember
     * @return
     */
    AjaxResult addMember(AddTreeMemberVO treeMember);

    /**
     * 添加全部人员到考试
     * @param getMember
     * @return
     */
    AjaxResult addAllMember(GetMemberVO getMember);

    /**
     * 加入考试成员
     * @param memberList
     * @param examId
     * @return
     */
    boolean insertExamMember(List<UserProfile> memberList,
                             Integer examId);
}

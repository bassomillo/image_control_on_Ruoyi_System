package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ExamMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.union.entity.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
     * 添加单个/多个人员到分组
     * @param treeMember
     * @return
     */
    AjaxResult addMemberGroup(AddTreeMemberVO treeMember);

    /**
     * 添加全部人员到分组
     * @param getMember
     * @return
     */
    AjaxResult addAllMemberGroup(GetMemberVO getMember);

    /**
     * 人员导入
     * @param file
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    AjaxResult importMember(MultipartFile file,
                            Integer pageNum,
                            Integer pageSize,
                            HttpServletRequest request);

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
    AjaxResult getExamMemberList(Integer examId,
                                 String name,
                                 String employmentForm,
                                 String mobile,
                                 Integer pageNum,
                                 Integer pageSize);

    /**
     * 删除参考人员
     * @param examId
     * @param userId
     * @return
     */
    AjaxResult deleteExamMember(Integer examId,
                                Integer userId);

    /**
     * 加入考试成员
     * @param memberList
     * @param examId
     * @return
     */
    boolean insertExamMember(List<UserProfile> memberList,
                             Integer examId);
}

package com.ruoyi.project.democratic.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.VO.GroupToExamVO;
import com.ruoyi.project.democratic.entity.VoteGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 投票人员分组表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-19
 */
public interface IVoteGroupService extends IService<VoteGroup> {

    /**
     * 新增分组
     * @param groupName
     * @param userId
     * @return
     */
    AjaxResult insertGroup(String groupName,
                           Integer userId);

    /**
     * 查询分组
     * @param userId
     * @return
     */
    AjaxResult getGroup(Integer userId);

    /**
     * 重命名分组
     * @param groupName
     * @param groupId
     * @return
     */
    AjaxResult renameGroup(String groupName,
                           Integer groupId);

    /**
     * 删除分组
     * @param groupId
     * @return
     */
    AjaxResult deleteGroup(Integer groupId);

    /**
     * 删除分组人员
     * @param groupId
     * @param memberId
     * @return
     */
    AjaxResult deleteGroupMember(Integer groupId,
                                 Integer memberId);

    /**
     * 加入考试
     * @param group
     * @return
     */
    AjaxResult addToExam(GroupToExamVO group);

    /**
     * 加入问卷
     * @param group
     * @return
     */
    AjaxResult addToQu(GroupToExamVO group);
}

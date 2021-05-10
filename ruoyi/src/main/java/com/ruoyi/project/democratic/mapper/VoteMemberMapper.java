package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.VO.MemberInfoVO;
import com.ruoyi.project.democratic.entity.VoteMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 投票/问卷成员表 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-29
 */
public interface VoteMemberMapper extends BaseMapper<VoteMember> {

    /**
     * 条件查询参考人员列表
     * @param quId
     * @param name
     * @param employmentForm
     * @param mobile
     * @return
     */
    List<MemberInfoVO> selectQuMemberList(@Param("quId") Integer quId,
                                          @Param("name") String name,
                                          @Param("employmentForm") String employmentForm,
                                          @Param("mobile") String mobile);

}

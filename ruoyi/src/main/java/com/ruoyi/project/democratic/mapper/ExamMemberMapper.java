package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.ExamMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.democratic.entity.VO.MemberInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试成员表 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
public interface ExamMemberMapper extends BaseMapper<ExamMember> {

    /**
     * 条件查询组织树人员信息
     * @param idList
     * @param name
     * @param employmentForm
     * @param mobile
     * @return
     */
    List<MemberInfoVO> selectMemberInfo(@Param("idList") List<Integer> idList,
                                        @Param("name") String name,
                                        @Param("employmentForm") String employmentForm,
                                        @Param("mobile") String mobile);
}

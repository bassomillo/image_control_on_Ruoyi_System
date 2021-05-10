package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.DO.ExamPaperExportDO;
import com.ruoyi.project.democratic.entity.VO.MemberInfoVO;
import com.ruoyi.project.democratic.entity.Vote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 投票表 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
public interface VoteMapper extends BaseMapper<Vote> {

    /**
     * 条件查询问卷列表
     * @param title
     * @return
     */
    List<Vote> getQuestionList(@Param("title") String title);

    /**
     * 查找时间段内问卷
     * @param startTime
     * @param endTime
     * @return
     */
    List<Vote> getPublishList(@Param("startTime") String startTime,
                              @Param("endTime") String endTime);

    /**
     * 查询首页问卷列表
     * @param title
     * @param voteIdList
     * @return
     */
    List<Vote> getTopQuList(@Param("title") String title,
                            @Param("voteIdList") List<Integer> voteIdList);

    /**
     * 获取用户导出数据
     * @param userId
     * @return
     */
    ExamPaperExportDO getUserExportData(@Param("userId") Integer userId);

    /**
     * 获取问卷人员情况
     * @param quId
     * @param orgIds
     * @return
     */
    List<MemberInfoVO> getMemberList(@Param("quId") Integer quId,
                                     @Param("orgIds") List<Integer> orgIds);
}

package com.ruoyi.project.democratic.mapper;

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

}

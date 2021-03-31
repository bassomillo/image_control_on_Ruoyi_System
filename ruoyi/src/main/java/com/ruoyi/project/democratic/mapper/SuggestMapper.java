package com.ruoyi.project.democratic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.democratic.entity.SuggestBox;
import com.ruoyi.project.democratic.entity.VO.SuggestBackVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */

public interface SuggestMapper extends BaseMapper<SuggestBox> {

    /**
     * 条件查询建言列表
     * @param content
     * @param year
     * @return
     */
    List<SuggestBackVO> getBackSuggestList(@Param("content") String content,
                                           @Param("year") String year);
}

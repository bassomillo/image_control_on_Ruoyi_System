package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试表 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-15
 */
public interface ExamMapper extends BaseMapper<Exam> {

    /**
     * 条件查询后台考试列表
     * @param title
     * @return
     */
    List<Exam> getBackExamList(@Param("title") String title);
}

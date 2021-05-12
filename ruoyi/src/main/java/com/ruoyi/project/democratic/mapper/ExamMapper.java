package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.DO.ExamPaperExportDO;
import com.ruoyi.project.democratic.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.democratic.entity.ExamPaper;
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

    /**
     * 获取用户导出数据
     * @param users
     * @return
     */
    List<ExamPaperExportDO> getUserExportData(@Param("users") List<ExamPaper> users);

    /**
     * 获取时间段内发布的考试
     * @param startTime
     * @param endTime
     * @return
     */
    List<Exam> getPublishList(@Param("startTime") String startTime,
                              @Param("endTime") String endTime);

    /**
     * 条件查询首页考试列表
     * @param title
     * @param examIdList
     * @return
     */
    List<Exam> getTopExamList(@Param("title") String title,
                              @Param("examIdList") List<Integer> examIdList);
}

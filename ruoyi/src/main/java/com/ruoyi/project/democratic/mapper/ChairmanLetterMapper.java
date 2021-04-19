package com.ruoyi.project.democratic.mapper;

import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.democratic.entity.DO.ManageExportDO;
import com.ruoyi.project.democratic.entity.VO.ChairmanLetterBackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主席信箱表 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
public interface ChairmanLetterMapper extends BaseMapper<ChairmanLetterBox> {

    /**
     * 条件查询信件列表
     * @param content
     * @param year
     * @param intList
     * @param isAdmin
     * @return
     */
    List<ChairmanLetterBackVO> selectLetterList(@Param("content") String content,
                                                @Param("year") Integer year,
                                                @Param("intList") List<String> intList,
                                                @Param("isAdmin") Integer isAdmin);

    /**
     * 获取导出数据
     * @param content
     * @param year
     * @param intList
     * @param isAdmin
     * @return
     */
    List<ManageExportDO> getExportData(@Param("content") String content,
                                       @Param("year") Integer year,
                                       @Param("intList") List<String> intList,
                                       @Param("isAdmin") Integer isAdmin);
}

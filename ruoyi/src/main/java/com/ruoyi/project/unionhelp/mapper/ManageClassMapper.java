package com.ruoyi.project.unionhelp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.unionhelp.entity.DO.ManageClassExportDO;
import com.ruoyi.project.unionhelp.entity.ManageClass;
import com.ruoyi.project.unionhelp.entity.VO.ManageClassVO;
import com.ruoyi.project.unionhelp.entity.VO.ManageClassVO1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 托管班 Mapper 接口
 * </p>
 *
 * @author zm
 * @since 2021-04-25
 */
public interface ManageClassMapper extends BaseMapper<ManageClass> {
    Boolean InsertManageClass(ManageClass manageClass);

    ManageClassVO1 SearchManageClassById(@Param("id") Integer id);

    Integer GetManageClassNum(@Param("company")String company,
                              @Param("trueName")String trueName,
                              @Param("mobile")String mobile);

    Boolean UpdateManageClass(ManageClass manageClass);

    List<ManageClassVO> GetManageClasses(@Param("pagesize") Integer pagesize,
                                         @Param("index") Integer index);

    List<ManageClassVO> SelectManageClasses(@Param("company")String company,
                                            @Param("trueName")String trueName,
                                            @Param("mobile")String mobile,
                                            @Param("pagesize") Integer pagesize,
                                            @Param("index") Integer index);

    List<ManageClassExportDO> GetExportData();
}

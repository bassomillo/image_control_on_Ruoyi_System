package com.ruoyi.project.democratic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;
import com.ruoyi.project.democratic.entity.VO.ManagerLetterBackVO;
import com.ruoyi.project.system.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 总经理信箱 Mapper 接口
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
public interface ManagerLetterMapper extends BaseMapper<ManagerLetterBox> {

    /**
     * 条件查询
     * @param content
     * @param year
     * @param provinceInt
     * @param cityInt
     * @return
     */
    List<ManagerLetterBackVO> getLetterList(@Param("content") String content,
                                            @Param("year") Integer year,
                                            @Param("provinceInt") List<String> provinceInt,
                                            @Param("cityInt") List<String> cityInt,
                                            @Param("isAdmin") Integer isAdmin);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    SysRole getUserRole(@Param("userId") Integer userId);
}

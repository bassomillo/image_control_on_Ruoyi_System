package com.ruoyi.project.chairmanOnline.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.chairmanOnline.entity.IMUserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (UserProfile)表数据库访问层
 *
 * @author weide
 * @since 2021-05-14 10:37:42
 */
@Mapper
public interface IMUserProfileDao extends BaseMapper<IMUserProfile> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IMUserProfile queryById(Object id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<IMUserProfile> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userProfile 实例对象
     * @return 对象列表
     */
    List<IMUserProfile> queryAll(IMUserProfile userProfile);

    /**
     * 新增数据
     *
     * @param userProfile 实例对象
     * @return 影响行数
     */
    @Override
    int insert(IMUserProfile userProfile);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserProfile> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<IMUserProfile> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserProfile> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<IMUserProfile> entities);

    /**
     * 修改数据
     *
     * @param userProfile 实例对象
     * @return 影响行数
     */
    int update(IMUserProfile userProfile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}


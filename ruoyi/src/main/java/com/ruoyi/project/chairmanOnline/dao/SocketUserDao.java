package com.ruoyi.project.chairmanOnline.dao;

import com.ruoyi.project.chairmanOnline.entity.SocketUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author weide
 * @since 2021-04-29 11:22:38
 */
@Mapper
public interface SocketUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<SocketUser> queryAll(SocketUser user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(SocketUser user);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SocketUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *SocketUser
    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(SocketUser user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<SocketUser> selectPsychologicalCounselors();

}


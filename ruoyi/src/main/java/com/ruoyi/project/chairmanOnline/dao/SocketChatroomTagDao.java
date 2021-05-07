package com.ruoyi.project.chairmanOnline.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天室标签表(SocketChatroomTag)表数据库访问层
 *
 * @author weide
 * @since 2021-05-06 16:09:24
 */
@Mapper
public interface SocketChatroomTagDao extends BaseMapper<SocketChatroomTag> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatroomTag queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatroomTag> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param socketChatroomTag 实例对象
     * @return 对象列表
     */
    List<SocketChatroomTag> queryAll(SocketChatroomTag socketChatroomTag);

    /**
     * 新增数据
     *
     * @param socketChatroomTag 实例对象
     * @return 影响行数
     */
    @Override
    int insert(SocketChatroomTag socketChatroomTag);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatroomTag> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SocketChatroomTag> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatroomTag> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SocketChatroomTag> entities);

    /**
     * 修改数据
     *
     * @param socketChatroomTag 实例对象
     * @return 影响行数
     */
    int update(SocketChatroomTag socketChatroomTag);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}


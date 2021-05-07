package com.ruoyi.project.chairmanOnline.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天记录(SocketChatroomRecord)表数据库访问层
 *
 * @author weide
 * @since 2021-05-06 10:11:47
 */
@Mapper
public interface SocketChatroomRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatroomRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatroomRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param socketChatroomRecord 实例对象
     * @return 对象列表
     */
    List<SocketChatroomRecord> queryAll(SocketChatroomRecord socketChatroomRecord);

    /**
     * 新增数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 影响行数
     */

    int insert(SocketChatroomRecord socketChatroomRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatroomRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SocketChatroomRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatroomRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SocketChatroomRecord> entities);

    /**
     * 修改数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 影响行数
     */
    int update(SocketChatroomRecord socketChatroomRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<SocketChatroomRecord> queryRecordBytagId (int tagId);

}


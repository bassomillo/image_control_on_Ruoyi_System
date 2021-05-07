package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketChatroomRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天记录(SocketChatroomRecord)表服务接口
 *
 * @author weide
 * @since 2021-05-06 10:11:47
 */
public interface SocketChatroomRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatroomRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatroomRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 实例对象
     */
    SocketChatroomRecord insert(SocketChatroomRecord socketChatroomRecord);

    /**
     * 修改数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 实例对象
     */
    SocketChatroomRecord update(SocketChatroomRecord socketChatroomRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<SocketChatroomRecord> queryRecordBytagId (int tagId);

}

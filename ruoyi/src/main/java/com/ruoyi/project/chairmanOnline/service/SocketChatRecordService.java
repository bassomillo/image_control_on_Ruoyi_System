package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;

import java.util.List;

/**
 * 聊天记录(SocketChatRecord)表服务接口
 *
 * @author weide
 * @since 2021-04-15 15:50:29
 */
public interface SocketChatRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param socketChatRecord 实例对象
     * @return 实例对象
     */
    SocketChatRecord insert(SocketChatRecord socketChatRecord);

    /**
     * 修改数据
     *
     * @param socketChatRecord 实例对象
     * @return 实例对象
     */
    SocketChatRecord update(SocketChatRecord socketChatRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    List<SocketChatRecord> queryChatRecord(Integer senderId, Integer receiverId);

    /**
     * 查询未读信息
     *
     * @param
     * @return 对象列表
     */
    List<SocketChatRecord> queryUnsentRecord(int userid);

    /**
     * 插入或者更新
     *
     * @param
     * @return 变动条数
     */

    int insertOrUpdateRecord(SocketChatRecord socketChatRecord);

}

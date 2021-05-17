package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketChatroomTag;

import java.util.List;

/**
 * 聊天室标签表(SocketChatroomTag)表服务接口
 *
 * @author weide
 * @since 2021-05-06 16:09:24
 */
public interface SocketChatroomTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatroomTag queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatroomTag> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param socketChatroomTag 实例对象
     * @return 实例对象
     */
    SocketChatroomTag insert(SocketChatroomTag socketChatroomTag);

    /**
     * 修改数据
     *
     * @param socketChatroomTag 实例对象
     * @return 实例对象
     */
    SocketChatroomTag update(SocketChatroomTag socketChatroomTag);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

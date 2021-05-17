package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketUser;

import java.util.List;

/**
 * (SocketUser)表服务接口
 *
 * @author weide
 * @since 2021-04-29 11:22:39
 */
public interface SocketUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    SocketUser insert(SocketUser user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    SocketUser update(SocketUser user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<SocketUser> selectPsychologicalCounselors();



}

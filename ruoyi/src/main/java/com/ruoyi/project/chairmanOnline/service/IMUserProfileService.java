package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.IMUserProfile;

import java.util.List;

/**
 * (UserProfile)表服务接口
 *
 * @author weide
 * @since 2021-05-14 10:37:43
 */
public interface IMUserProfileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IMUserProfile queryById(Object id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<IMUserProfile> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userProfile 实例对象
     * @return 实例对象
     */
    IMUserProfile insert(IMUserProfile userProfile);

    /**
     * 修改数据
     *
     * @param userProfile 实例对象
     * @return 实例对象
     */
    IMUserProfile update(IMUserProfile userProfile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Object id);

}

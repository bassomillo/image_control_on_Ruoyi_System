package com.ruoyi.project.chairmanOnline.service.impl;

import com.ruoyi.project.chairmanOnline.dao.IMUserProfileDao;
import com.ruoyi.project.chairmanOnline.entity.IMUserProfile;
import com.ruoyi.project.chairmanOnline.service.IMUserProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserProfile)表服务实现类
 *
 * @author weide
 * @since 2021-05-14 10:37:43
 */
@Service("imUserProfileService")
public class IMUserProfileServiceImpl implements IMUserProfileService {
    @Resource
    private IMUserProfileDao imUserProfileDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public IMUserProfile queryById(Object id) {
        return this.imUserProfileDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<IMUserProfile> queryAllByLimit(int offset, int limit) {
        return this.imUserProfileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userProfile 实例对象
     * @return 实例对象
     */
    @Override
    public IMUserProfile insert(IMUserProfile userProfile) {
        this.imUserProfileDao.insert(userProfile);
        return userProfile;
    }

    /**
     * 修改数据
     *
     * @param userProfile 实例对象
     * @return 实例对象
     */
    @Override
    public IMUserProfile update(IMUserProfile userProfile) {
        this.imUserProfileDao.update(userProfile);
        return this.queryById(userProfile.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Object id) {
        return this.imUserProfileDao.deleteById(id) > 0;
    }
}

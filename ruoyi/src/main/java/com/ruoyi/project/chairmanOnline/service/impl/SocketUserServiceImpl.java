package com.ruoyi.project.chairmanOnline.service.impl;

import com.ruoyi.project.chairmanOnline.dao.SocketUserDao;
import com.ruoyi.project.chairmanOnline.entity.SocketUser;
import com.ruoyi.project.chairmanOnline.service.SocketUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author weide
 * @since 2021-04-29 11:22:39
 */
@Service("userService")
public class SocketUserServiceImpl implements SocketUserService {
    @Resource
    private SocketUserDao socketUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SocketUser queryById(Integer id) {
        return this.socketUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SocketUser> queryAllByLimit(int offset, int limit) {
        return this.socketUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public SocketUser insert(SocketUser user) {
        this.socketUserDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public SocketUser update(SocketUser user) {
        this.socketUserDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.socketUserDao.deleteById(id) > 0;
    }

    @Override
    public List<SocketUser> selectPsychologicalCounselors() {
        return socketUserDao.selectPsychologicalCounselors();
    }
}

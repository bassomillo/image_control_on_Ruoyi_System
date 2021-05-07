package com.ruoyi.project.chairmanOnline.service.impl;

import com.ruoyi.project.chairmanOnline.entity.SocketChatroomTag;
import com.ruoyi.project.chairmanOnline.dao.SocketChatroomTagDao;
import com.ruoyi.project.chairmanOnline.service.SocketChatroomTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天室标签表(SocketChatroomTag)表服务实现类
 *
 * @author weide
 * @since 2021-05-06 16:09:24
 */
@Service("socketChatroomTagService")
public class SocketChatroomTagServiceImpl implements SocketChatroomTagService {
    @Resource
    private SocketChatroomTagDao socketChatroomTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SocketChatroomTag queryById(Integer id) {
        return this.socketChatroomTagDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SocketChatroomTag> queryAllByLimit(int offset, int limit) {
        return this.socketChatroomTagDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param socketChatroomTag 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatroomTag insert(SocketChatroomTag socketChatroomTag) {
        this.socketChatroomTagDao.insert(socketChatroomTag);
        return socketChatroomTag;
    }

    /**
     * 修改数据
     *
     * @param socketChatroomTag 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatroomTag update(SocketChatroomTag socketChatroomTag) {
        this.socketChatroomTagDao.update(socketChatroomTag);
        return this.queryById(socketChatroomTag.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.socketChatroomTagDao.deleteById(id) > 0;
    }
}

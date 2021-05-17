package com.ruoyi.project.chairmanOnline.service.impl;

import com.ruoyi.project.chairmanOnline.entity.Tag;
import com.ruoyi.project.chairmanOnline.dao.TagDao;
import com.ruoyi.project.chairmanOnline.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author weide
 * @since 2021-04-25 15:57:07
 */
@Service("tagService")
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Tag queryById(Object id) {
        return this.tagDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Tag> queryAllByLimit(int offset, int limit) {
        return this.tagDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag insert(Tag tag) {
        this.tagDao.insert(tag);
        return tag;
    }

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag update(Tag tag) {
        this.tagDao.update(tag);
        return this.queryById(tag.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Object id) {
        return this.tagDao.deleteById(id) > 0;
    }
}

package com.ruoyi.project.chairmanOnline.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.SocketChatroomRecord;
import com.ruoyi.project.chairmanOnline.dao.SocketChatroomRecordDao;
import com.ruoyi.project.chairmanOnline.entity.SocketUser;
import com.ruoyi.project.chairmanOnline.service.SocketChatroomRecordService;
import com.ruoyi.project.chairmanOnline.service.SocketUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天记录(SocketChatroomRecord)表服务实现类
 *
 * @author weide
 * @since 2021-05-06 10:11:48
 */
@Service("socketChatroomRecordService")
public class SocketChatroomRecordServiceImpl implements SocketChatroomRecordService {
    @Resource
    private SocketChatroomRecordDao socketChatroomRecordDao;

    @Resource
    private SocketUserService socketUserService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SocketChatroomRecord queryById(Integer id) {
        return this.socketChatroomRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SocketChatroomRecord> queryAllByLimit(int offset, int limit) {
        return this.socketChatroomRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatroomRecord insert(SocketChatroomRecord socketChatroomRecord) {
        this.socketChatroomRecordDao.insert(socketChatroomRecord);
        return socketChatroomRecord;
    }

    /**
     * 修改数据
     *
     * @param socketChatroomRecord 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatroomRecord update(SocketChatroomRecord socketChatroomRecord) {
        this.socketChatroomRecordDao.update(socketChatroomRecord);
        return this.queryById(socketChatroomRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.socketChatroomRecordDao.deleteById(id) > 0;
    }


    /**
     * 群聊记录
     *
     * @param
     * @return
     */
    @Override
    public AjaxResult queryRecordBytagId(int tagId, int pageNum, int pageSize) {
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        return AjaxResult.success(socketChatroomRecordDao.queryRecordBytagId(tagId), (int) page.getTotal());
    }


    /**
     * 获取用户头像和姓名等信息
     *
     * @param
     * @return
     */
    @Override
    public SocketChatroomRecord getUserInfo(SocketChatroomRecord socketChatroomRecord) {
        SocketUser user = socketUserService.queryById(socketChatroomRecord.getSenderid());
        socketChatroomRecord.setNickname(user.getNickname());
        socketChatroomRecord.setSmallAvatar(user.getSmallavatar());
        return socketChatroomRecord;
    }
}

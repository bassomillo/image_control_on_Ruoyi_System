package com.ruoyi.project.chairmanOnline.service.impl;

import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天记录(SocketChatRecord)表服务实现类
 *
 * @author weide
 * @since 2021-04-15 15:50:31
 */
@Service("socketChatRecordService")
public class SocketChatRecordServiceImpl implements SocketChatRecordService {
    @Resource
    private SocketChatRecordDao socketChatRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SocketChatRecord queryById(Integer id) {
        return this.socketChatRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SocketChatRecord> queryAllByLimit(int offset, int limit) {
        return this.socketChatRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param socketChatRecord 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatRecord insert(SocketChatRecord socketChatRecord) {
        this.socketChatRecordDao.insert(socketChatRecord);
        return socketChatRecord;
    }

    /**
     * 修改数据
     *
     * @param socketChatRecord 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatRecord update(SocketChatRecord socketChatRecord) {
        this.socketChatRecordDao.update(socketChatRecord);
        return this.queryById(socketChatRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.socketChatRecordDao.deleteById(id) > 0;
    }


    /**
     * 查询聊天记录
     *
     * @param id 主键
     * @return 是否成功
     */

    @Override
    public List<SocketChatRecord> queryChatRecord(Integer fromId, Integer toId) {
        return this.socketChatRecordDao.queryChatRecord(fromId,toId);
    }

    /**
     * 通过主键删除数据
     *
     * @param userid 用户id  ， socketChatRecordQM 查询条件
     * @return 是否成功
     */

    @Override
    public List<SocketChatRecord> queryUnsentRecord(int userid) {
        SocketChatRecord socketChatRecordQM = new SocketChatRecord();
        socketChatRecordQM.setReceiverid(userid);
        socketChatRecordQM.setIssent(0);
        return socketChatRecordDao.queryAll(socketChatRecordQM);
    }

    /**
     * 聊天记录插入或者更新
     *
     * @param userid 用户id  ， socketChatRecordQM 查询条件
     * @return 是否成功
     */

    @Override
    public int insertOrUpdateRecord(SocketChatRecord socketChatRecord){
        List<SocketChatRecord> socketChatRecords = new ArrayList<>();
        socketChatRecords.add(socketChatRecord);
        return  socketChatRecordDao.insertOrUpdateBatch(socketChatRecords);
    }

}

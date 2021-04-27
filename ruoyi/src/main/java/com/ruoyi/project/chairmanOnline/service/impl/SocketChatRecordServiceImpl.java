package com.ruoyi.project.chairmanOnline.service.impl;

import com.github.pagehelper.PageHelper;
import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
import com.ruoyi.project.chairmanOnline.entity.DTO.SocketChatRecordDTO;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatRecordQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private SocketChatConversationService socketChatConversationService;

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
     * @return 对象列表
     */
    @Override
    public List<SocketChatRecord> queryAll(SocketChatRecord socketChatRecord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return this.socketChatRecordDao.queryAll(socketChatRecord);
    }

    /**
     * 新增数据
     *
     * @param socketChatRecord 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatRecord insert(SocketChatRecord socketChatRecord) {
        socketChatRecord.setCreatedtime(new Date());
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
     * @param
     * @return 是否成功
     */

    @Override
    public List<SocketChatRecord> queryChatRecord(Integer senderId, Integer recriverId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return this.socketChatRecordDao.queryChatRecord(senderId, recriverId);
    }

    /**
     * 查询未发送信息
     *
     * @param
     * @return
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
     * @param socketChatRecord 消息
     * @return 是否成功
     */

    @Override
    public int insertOrUpdateRecord(SocketChatRecord socketChatRecord) {
        List<SocketChatRecord> socketChatRecords = new ArrayList<>();
        socketChatRecords.add(socketChatRecord);
        return socketChatRecordDao.insertOrUpdateBatch(socketChatRecords);
    }

    @Override
    public List<SocketChatRecord> selectChatRecordsByCondition(SocketChatRecordQO socketChatRecordQO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return socketChatRecordDao.selectChatRecordsByCondition(socketChatRecordQO);
    }

    @Override
    public int chatRecordsIsRead(List<Integer> recordIds) {
        int total = 0;
        for (int recordId : recordIds) {
            SocketChatRecord socketChatRecord = new SocketChatRecord();
            socketChatRecord.setId(recordId);
            socketChatRecord.setIsread(1);
            socketChatRecordDao.update(socketChatRecord);
            total++;
            //更新对话已读信息数量统计
            Integer id = socketChatConversationService.queryById(this.queryById(recordId).getConversationid()).getId();
            socketChatConversationService.setConversationUnreadnum(id, -1);
        }
        return total;
    }


    /**
     * 检查对话是否有存在未读信息，并赋值
     *
     * @param
     * @Author
     * @description
     **/

    @Override
    public List<SocketChatConversation> conversationUnreadRecords(int userId, List<SocketChatConversation> socketChatConversations) {

        List<SocketChatRecordDTO> socketChatRecordDTOS = socketChatRecordDao.selectUnreadRecordsByUserId(userId);
        //temp
        for (SocketChatRecordDTO socketChatRecordDTO : socketChatRecordDTOS) {
            for (SocketChatConversation socketChatConversation : socketChatConversations) {
                if (socketChatRecordDTO.getConversationId().equals(socketChatConversation.getId())) {
                    socketChatConversation.setUnreadnum(socketChatRecordDTO.getUnredNum());
                }
            }
        }
        return socketChatConversations;
    }

}

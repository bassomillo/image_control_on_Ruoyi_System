package com.ruoyi.project.chairmanOnline.service.impl;

import com.github.pagehelper.PageHelper;
import com.ruoyi.project.chairmanOnline.dao.SocketChatConversationDao;
import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 聊天会话表(SocketChatConversation)表服务实现类
 *
 * @author weide
 * @since 2021-04-15 15:50:11
 */
@Service("socketChatConversationService")
public class SocketChatConversationServiceImpl implements SocketChatConversationService {
    @Resource
    private SocketChatConversationDao socketChatConversationDao;

    @Resource
    SocketChatConversationDao SocketChatConversationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SocketChatConversation queryById(Integer id) {
        return this.socketChatConversationDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SocketChatConversation> queryAllByLimit(int offset, int limit) {
        return this.socketChatConversationDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param socketChatConversation 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatConversation insert(SocketChatConversation socketChatConversation) {
        this.socketChatConversationDao.insert(socketChatConversation);
        return socketChatConversation;
    }

    /**
     * 修改数据
     *
     * @param socketChatConversation 实例对象
     * @return 实例对象
     */
    @Override
    public SocketChatConversation update(SocketChatConversation socketChatConversation) {
        this.socketChatConversationDao.update(socketChatConversation);
        return this.queryById(socketChatConversation.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.socketChatConversationDao.deleteById(id) > 0;
    }


    /**
     * @param
     * @Author
     * @description 对话新增或者更新
     **/

    @Override
    public int insertOrUpdate(SocketChatConversation socketChatConversation) {
        SocketChatConversation chatConversationQM = new SocketChatConversation();
        chatConversationQM.setReceiverid(socketChatConversation.getReceiverid());
        chatConversationQM.setSenderid(socketChatConversation.getSenderid());
        List<SocketChatConversation> socketChatConversations = SocketChatConversationDao.queryAll(chatConversationQM);
        if (socketChatConversations.size() > 0) {
            SocketChatConversation chatConversation = new SocketChatConversation();
            chatConversation.setMessagenum(1 + 1);
            this.update(chatConversation);
            return socketChatConversations.get(0).getId();
        } else {
            return this.insert(socketChatConversation).getId();
        }
    }

    /**
     * @param
     * @Author
     * @description 查询用户所有对话
     **/
    @Override
    public List<SocketChatConversation> queryConversation(int userId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return SocketChatConversationDao.queryConversationByUserId(userId);

    }


    /**
     * @param
     * @Author
     * @description 创建对话
     **/

    @Override
    public int createConversation(SocketChatRecord socketChatRecord) {

        //查看当前对话的两人是否已有创建对话框
        List<SocketChatConversation> socketChatConversations = SocketChatConversationDao.queryChatConversation(socketChatRecord.getSenderid(), socketChatRecord.getReceiverid());
        if (socketChatConversations.size() == 0) {
            SocketChatConversation socketChatConversation = new SocketChatConversation();
            socketChatConversation.setSenderid(socketChatRecord.getSenderid());
            socketChatConversation.setReceiverid(socketChatRecord.getReceiverid());
            socketChatConversation.setIsdelete(0);
            socketChatConversation.setCreatedtime(new Date());
            SocketChatConversation chatConversation = this.insert(socketChatConversation);
            return chatConversation.getId();
        } else {
            //会话已存在刷新一下isDelete为0
            SocketChatConversation conversation = socketChatConversations.get(0);
            conversation.setIsdelete(0);
            this.update(conversation);
            return socketChatConversations.get(0).getId();
        }

    }


}

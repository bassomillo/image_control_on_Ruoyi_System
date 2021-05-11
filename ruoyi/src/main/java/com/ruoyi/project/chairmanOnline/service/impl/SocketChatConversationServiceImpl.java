package com.ruoyi.project.chairmanOnline.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.dao.SocketChatConversationDao;
import com.ruoyi.project.chairmanOnline.dao.SocketChatRecordDao;
import com.ruoyi.project.chairmanOnline.entity.DTO.SocketChatRecordDTO;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatConversationQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.entity.VO.SocketChatConversationVO;
import com.ruoyi.project.chairmanOnline.service.SocketChatConversationService;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
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
    private SocketChatRecordService socketChatRecordService;

    @Resource
    SocketChatRecordDao socketChatRecordDao;


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
     * @description 查询用户所有对话
     **/

    @Override
    public AjaxResult queryConversation(int userId, int pageNum, int pageSize, SocketChatConversationQO socketChatConversationQO) {
//        int total = socketChatConversationDao.queryConversationByUserId(userId, socketChatConversationQO).size();
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<SocketChatConversationVO> socketChatConversations = socketChatConversationDao.queryConversationByUserId(userId, socketChatConversationQO);
        return AjaxResult.success(this.conversationUnreadRecords(userId,socketChatConversations), (int)page.getTotal());
    }

    /**
     * @param
     * @Author
     * @description 创建对话
     **/
    @Override
    public int createConversation(SocketChatRecord socketChatRecord) {
        //查看当前对话的两人是否已有创建对话框
        List<SocketChatConversation> socketChatConversations = socketChatConversationDao.queryChatConversation(socketChatRecord.getSenderid(), socketChatRecord.getReceiverid());
        if (socketChatConversations.size() == 0) {
            SocketChatConversation socketChatConversation = new SocketChatConversation();
            socketChatConversation.setSenderid(socketChatRecord.getSenderid());
            socketChatConversation.setReceiverid(socketChatRecord.getReceiverid());
            socketChatConversation.setIsdelete(0);
            socketChatConversation.setMessagenum(0);
            socketChatConversation.setUnreadnum(0);
            socketChatConversation.setCreatedtime(new Date());
            SocketChatConversation chatConversation = this.insert(socketChatConversation);
            return chatConversation.getId();
        } else {
            return socketChatConversations.get(0).getId();
        }
    }

    /**
     * 接收消息时，更新对话中的一些数值总量
     *
     * @param
     * @description
     **/
    @Override
    public void conversationStatistics(int id) {
        SocketChatConversation conversation = this.queryById(id);
        SocketChatConversation socketChatConversation = new SocketChatConversation();
        socketChatConversation.setId(conversation.getId());
        socketChatConversation.setIsdelete(0);
        socketChatConversation.setMessagenum(conversation.getMessagenum() + 1);
        this.update(socketChatConversation);
    }

    /**
     * 检查对话是否有存在未读信息，并计算数值
     *
     * @param
     * @Author
     * @description
     **/
    @Override
    public List<SocketChatConversationVO> conversationUnreadRecords(int userId, List<SocketChatConversationVO> socketChatConversationVOs) {

        List<SocketChatRecordDTO> socketChatRecordDTOS = socketChatRecordDao.selectUnreadRecordsByUserId(userId);
        //temp
        for (SocketChatRecordDTO socketChatRecordDTO : socketChatRecordDTOS) {
            for (SocketChatConversationVO socketChatConversationVO : socketChatConversationVOs) {
                if (socketChatRecordDTO.getConversationId().equals(socketChatConversationVO.getId())) {
                    socketChatConversationVO.setUnreadnum(socketChatRecordDTO.getUnredNum());
                }
            }
        }
        return socketChatConversationVOs;
    }


}

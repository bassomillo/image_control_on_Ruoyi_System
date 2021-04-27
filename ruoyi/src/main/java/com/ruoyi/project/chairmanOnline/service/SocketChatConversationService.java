package com.ruoyi.project.chairmanOnline.service;

import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;

import java.util.List;

/**
 * 聊天会话表(SocketChatConversation)表服务接口
 *
 * @author weide
 * @since 2021-04-15 15:50:10
 */
public interface SocketChatConversationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatConversation queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatConversation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param socketChatConversation 实例对象
     * @return 实例对象
     */
    SocketChatConversation insert(SocketChatConversation socketChatConversation);

    /**
     * 修改数据
     *
     * @param socketChatConversation 实例对象
     * @return 实例对象
     */
    SocketChatConversation update(SocketChatConversation socketChatConversation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     *查询对话
     *@Author
     *@description
     *@param
     *
     **/
    List<SocketChatConversation> queryConversation(int userId,int pageNum,int pageSize);

    int createConversation(SocketChatRecord socketChatRecord);


    void conversationStatistics(int id);

    /**
     *已读数据统计
     *@Author
     *@description
     *@param
     *
     **/
    void setConversationUnreadnum(int id,int num);

}

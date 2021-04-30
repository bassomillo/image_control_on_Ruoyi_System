package com.ruoyi.project.chairmanOnline.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatConversationQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatConversation;
import com.ruoyi.project.chairmanOnline.entity.VO.SocketChatConversationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 聊天会话表(SocketChatConversation)表数据库访问层
 *
 * @author weide
 * @since 2021-04-15 15:50:10
 */
@Mapper
public interface SocketChatConversationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatConversation queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatConversation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param socketChatConversation 实例对象
     * @return 对象列表
     */
    List<SocketChatConversation> queryAll(SocketChatConversation socketChatConversation);

    /**
     * 新增数据
     *
     * @param socketChatConversation 实例对象
     * @return 影响行数
     */

    int insert(SocketChatConversation socketChatConversation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatConversation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SocketChatConversation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatConversation> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SocketChatConversation> entities);

    /**
     * 修改数据
     *
     * @param socketChatConversation 实例对象
     * @return 影响行数
     */
    int update(SocketChatConversation socketChatConversation);

    /**
     * 通过主键逻辑删除
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id")Integer id);


    /**
     *
     *@description 用户的所有对话查询，可加条件
     *@param
     *
     **/


    List<SocketChatConversationVO> queryConversationByUserId(@Param("userId") int userId, @Param("socketChatConversationQO")SocketChatConversationQO socketChatConversationQO);


    /**
     *
     *@description 根据发送者和接收者查询对话,可用来确认两个用户之间是否存在对话
     *@param
     *
     **/
    List<SocketChatConversation> queryChatConversation(@Param("senderId") int senderId,@Param("receiverId") int receiverId);


}


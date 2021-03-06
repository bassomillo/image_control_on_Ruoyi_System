package com.ruoyi.project.chairmanOnline.dao;

import com.ruoyi.project.chairmanOnline.entity.DTO.SocketChatRecordDTO;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatRecordQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天记录(SocketChatRecord)表数据库访问层
 *
 * @author weide
 * @since 2021-04-15 15:50:29
 */
@Mapper
public interface SocketChatRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SocketChatRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SocketChatRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param socketChatRecord 实例对象
     * @return 对象列表
     */
    List<SocketChatRecord> queryAll(SocketChatRecord socketChatRecord);

    /**
     * 新增数据
     *
     * @param socketChatRecord 实例对象
     * @return 影响行数
     */
    int insert(SocketChatRecord socketChatRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SocketChatRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SocketChatRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SocketChatRecord> entities);

    /**
     * 修改数据
     *
     * @param socketChatRecord 实例对象
     * @return 影响行数
     */
    int update(SocketChatRecord socketChatRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 查询聊天记录
     *
     * @param
     * @return 对象列表
     */
    List<SocketChatRecord> queryChatRecord(@Param("senderId")Integer senderId,@Param("receiverId")Integer receiverId);

    /**
     * 自定义条件查询聊天记录
     *
     * @param
     * @return 对象列表
     */

    List<SocketChatRecord> selectChatRecordsByCondition(SocketChatRecordQO socketChatRecordQO);

    /**
     * 查询用户的未读信息条数
     *
     * @param userId
     * @return 对象列表
     */

    List<SocketChatRecordDTO> selectUnreadRecordsByUserId(@Param("userId") int userId);

    /**
     * 已读信息标记更新
     *
     * @param userId
     * @return 对象列表
     */
    int chatRecordsIsRead(@Param("userId")int userId,@Param("conversationId") int conversationId);
}


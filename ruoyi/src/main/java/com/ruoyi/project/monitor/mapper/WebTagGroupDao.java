package com.ruoyi.project.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.monitor.domain.WebTagGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 标签组表 Mapper 接口
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
public interface WebTagGroupDao extends BaseMapper<WebTagGroup> {
    @Insert("INSERT INTO tag_group(name, scope,tagNum, tagIds, resident, updatedTime, createdTime) VALUES(#{name}, " +
            "#{scope}, #{tagNum}, #{tagIds}, #{resident}, #{updatedTime}, #{createdTime})")
    Boolean InsertWebTagGroup(WebTagGroup webTagGroup);

    @Select("SELECT max(id) FROM tag_group")
    Integer GetMaxId();

    @Insert("INSERT INTO tag_group_tag(tagId, groupId) VALUES(#{tagId}, #{groupId})")
    Boolean InsertWebTagGroupTag(@Param("tagId") Integer tagId,
                                 @Param("groupId") Integer groupId);

    @Delete("DELETE FROM tag_group WHERE id=#{id}")
    Boolean DeleteWebTagGroup(@Param("id") Integer id);

    @Delete("DELETE FROM tag_group_tag WHERE groupId=#{groupId}")
    Boolean DeleteWebTagGroupTag(@Param("groupId") Integer groupId);

    @Select("SELECT * FROM tag_group WHERE id=#{id}")
    WebTagGroup SearchTagGroupById(@Param("id") Integer id);

    @Update("UPDATE tag_group SET name=#{name}, scope=#{scope}, tagNum=#{tagNum} tagIds=#{tagIds}, resident=#{resident}, updatedTime=#{updatedTime} WHERE id=#{id}")
    Boolean UpdateWebTagGroup(WebTagGroup webTagGroup);

    @Select("SELECT * FROM tag_group ORDER BY createdTime DESC  limit ${pagesize} OFFSET ${index}")
    List<WebTagGroup> GetWebTagGroups(@Param("pagesize") Integer pagesize, @Param("index") Integer index);

    @Select("SELECT COUNT(*) FROM tag_group")
    Integer GetTagGroupNum();
}

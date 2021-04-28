package com.ruoyi.project.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.monitor.domain.VO.WebTagVO2;
import com.ruoyi.project.monitor.domain.WebTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 标签 Mapper 接口
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
public interface WebTagDao extends BaseMapper<WebTag> {
    @Insert(" INSERT INTO tag(name,createdTime,orgId,orgCode) VALUES(#{name},#{createdTime},#{orgId},#{orgCode})")
    Boolean InsertWebTag(WebTag webTag);

    @Delete("DELETE FROM tag WHERE id=#{id}")
    Boolean DeleteWebTag(@Param("id") Integer id);

    @Select("SELECT * FROM tag WHERE id=#{id}")
    WebTag SearchWebTagById(@Param("id") Integer id);

    @Update("UPDATE tag SET name=#{name} WHERE id=#{id}")
    Boolean UpdateWebTag(WebTag webTag);

    @Select("SELECT tag.id,tag.name,tag.createdTime,GROUP_CONCAT(tag_group.name) as groupName, tag.orgId, tag.orgCode " +
            "FROM (tag LEFT JOIN tag_group_tag ON tag.id=tag_group_tag.tagId) LEFT JOIN tag_group ON tag_group_tag.groupId=tag_group.id " +
            "GROUP BY tag.name " +
            "ORDER BY id " +
            "limit ${pagesize} OFFSET ${index}")
    List<WebTagVO2> GetWebTags(@Param("pagesize") Integer pagesize, @Param("index") Integer index);

    @Select("SELECT name FROM tag limit ${pagesize} OFFSET ${index}")
    List<String> GetWebTagNames(@Param("pagesize") Integer pagesize, @Param("index") Integer index);

    @Select("SELECT COUNT(id) FROM tag")
    Integer GetTagNum();
}

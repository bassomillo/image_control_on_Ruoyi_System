package com.ruoyi.project.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.monitor.domain.WebArticleCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 资讯表 Mapper 接口
 * </p>
 *
 * @author zm
 * @since 2021-04-23
 */
public interface WebArticleCategoryMapper extends BaseMapper<WebArticleCategory> {
    List<String> GetTopArticleCategory(@Param("pagesize") Integer pagesize,
                                       @Param("index") Integer index,
                                       @Param("name")String name);

    @Insert("INSERT INTO article_category(name, weight,publishArticle, published, parentId, createdTime) VALUES" +
            "(#{name}, #{weight}, #{publishArticle}, #{published}, #{parentId}, #{createdTime})")
    Boolean InsertWebArticleCategory(WebArticleCategory webArticleCategory);

    @Select("SELECT COUNT(parentId) FROM article_category WHERE parentId=#{id}")
    Integer KidArticleNum(@Param("id") Integer id);

    @Delete("DELETE FROM article_category WHERE id=#{id}")
    Boolean DeleteWebArticleCategory(@Param("id") Integer id);

    @Select("SELECT * FROM article_category WHERE id=#{id}")
    WebArticleCategory SearchArticleCategoryById(@Param("id") Integer id);

    @Update("UPDATE article_category SET name=#{name}, publishArticle=#{publishArticle}, published=#{published}, " +
            "parentId=#{parentId} WHERE id=#{id}")
    Boolean UpdateWebArticleCategory(WebArticleCategory webArticleCategory);

    @Select("SELECT * FROM article_category ORDER BY id DESC limit ${pagesize} OFFSET ${index}")
    List<WebArticleCategory> GetWebArticleCategorys(@Param("pagesize") Integer pagesize, @Param("index") Integer index);

    @Select("SELECT COUNT(*) FROM article_category")
    Integer GetArticleCategoryNum();
}

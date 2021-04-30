package com.ruoyi.project.monitor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯栏目表
 * </p>
 *
 * @author zm
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebArticleCategory extends Model<WebArticleCategory> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 栏目名称
     */
    private String name;

    private Integer weight;

    /**
     * 是否允许发布文章
     */
    @TableField("publishArticle")
    private Integer publishArticle;

    /**
     * 栏目标题
     */
    @TableField("seoTitle")
    private String seoTitle;

    /**
     * SEO 关键字
     */
    @TableField("seoKeyword")
    private String seoKeyword;

    /**
     * 栏目描述（SEO）
     */
    @TableField("seoDesc")
    private String seoDesc;

    /**
     * 是否启用（1：启用 0：停用)
     */
    private Integer published;

    @TableField("parentId")
    private Integer parentId;

    @TableField("createdTime")
    private Integer createdTime;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String WEIGHT = "weight";

    public static final String PUBLISHARTICLE = "publishArticle";

    public static final String SEOTITLE = "seoTitle";

    public static final String SEOKEYWORD = "seoKeyword";

    public static final String SEODESC = "seoDesc";

    public static final String PUBLISHED = "published";

    public static final String PARENTID = "parentId";

    public static final String CREATEDTIME = "createdTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 投票/问卷题目选项表
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VoteOption extends Model<VoteOption> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "选项id")
    private Integer id;

    /**
     * 题目id
     */
    @TableField("voteQuestionId")
    @ApiModelProperty(value = "题目id")
    private Integer voteQuestionId;

    /**
     * 选项文字
     */
    @ApiModelProperty(value = "选项文字")
    private String content;

    /**
     * 选项图片地址
     */
    @TableField("imgUrl")
    @ApiModelProperty(value = "选项图片地址")
    private String imgUrl;

    /**
     * 选项图片id
     */
    @TableField("imgId")
    @ApiModelProperty(value = "选项图片id")
    private Integer imgId;


    public static final String ID = "id";

    public static final String VOTEQUESTIONID = "voteQuestionId";

    public static final String CONTENT = "content";

    public static final String IMGURL = "imgUrl";

    public static final String IMGID = "imgId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

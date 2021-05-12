package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 投票/问卷题目表
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VoteQuestion extends Model<VoteQuestion> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "题目id")
    private Integer id;

    /**
     * 投票/问卷id
     */
    @TableField("voteId")
    @ApiModelProperty(value = "投票/问卷id")
    private Integer voteId;

    /**
     * 题型，single单选，multiple多选，completion填空
     */
    @ApiModelProperty(value = "题型，single单选，multiple多选，completion填空")
    private String type;

    /**
     * 题目文字
     */
    @ApiModelProperty(value = "题目文字")
    private String content;

    /**
     * 题目图片地址
     */
    @TableField("imgUrl")
    @ApiModelProperty(value = "题目图片地址")
    private String imgUrl;

    /**
     * 题目图片id
     */
    @TableField("imgId")
    @ApiModelProperty(value = "题目图片id")
    private Integer imgId;

    /**
     * 必做1，非必做0
     */
    @ApiModelProperty(value = "必做1，非必做0")
    private Integer must;

    /**
     * 最多选择个数
     */
    @TableField("maxChecked")
    @ApiModelProperty(value = "最多选择个数")
    private Integer maxChecked;

    @TableField(exist = false)
    @ApiModelProperty(value = "选项列表")
    private List<VoteOption> optionList;

    @TableField(exist = false)
    @ApiModelProperty(value = "计数", hidden = true)
    private Integer count = 0;


    public static final String ID = "id";

    public static final String VOTEID = "voteId";

    public static final String TYPE = "type";

    public static final String CONTENT = "content";

    public static final String IMGURL = "imgUrl";

    public static final String IMGID = "imgId";

    public static final String MUST = "must";

    public static final String MAXCHECKED = "maxChecked";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

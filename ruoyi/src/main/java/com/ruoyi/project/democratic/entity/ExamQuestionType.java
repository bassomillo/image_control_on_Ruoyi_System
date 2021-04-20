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
 * 考试分值表
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamQuestionType extends Model<ExamQuestionType> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "分值id，页面中有返回必填")
    private Integer id;

    /**
     * 考试id
     */
    @TableField("examId")
    @ApiModelProperty(value = "考试id，必填")
    private Integer examId;

    /**
     * 题型
     */
    @ApiModelProperty(value = "题型，必填，single单选，judge判断，multiple多选，completion填空")
    private String type;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值，必填")
    private Integer score;


    public static final String ID = "id";

    public static final String EXAMID = "examId";

    public static final String TYPE = "type";

    public static final String SCORE = "score";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

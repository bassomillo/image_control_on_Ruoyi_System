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
 * 考试成员表
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamMember extends Model<ExamMember> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "考试成员id")
    private Integer id;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String tel;

    /**
     * 考试id
     */
    @TableField("examId")
    @ApiModelProperty(value = "考试id")
    private Integer examId;


    public static final String ID = "id";

    public static final String USERID = "userId";

    public static final String TEL = "tel";

    public static final String EXAMID = "examId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

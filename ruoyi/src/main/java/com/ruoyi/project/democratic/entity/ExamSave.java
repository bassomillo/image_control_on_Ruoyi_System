package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.ruoyi.project.democratic.entity.VO.ExamSaveVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 考试保存表
 * </p>
 *
 * @author cxr
 * @since 2021-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamSave extends Model<ExamSave> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "保存答题记录id")
    private Integer id;

    /**
     * 考试id
     */
    @TableField("examId")
    @ApiModelProperty(value = "考试id")
    private Integer examId;

    /**
     * 用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 提交内容
     */
    @TableField("submitContent")
    @ApiModelProperty(value = "提交内容")
    private String submitContent;

    /**
     * 剩余时间
     */
    @TableField("remainTime")
    @ApiModelProperty(value = "剩余时间")
    private String remainTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "保存的已答题列表，每次都全部传过来，必填")
    private List<ExamSaveVO> saveList;

    public static final String ID = "id";

    public static final String EXAMID = "examId";

    public static final String USERID = "userId";

    public static final String SUBMITCONTENT = "submitContent";

    public static final String REMAINTIME = "remainTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

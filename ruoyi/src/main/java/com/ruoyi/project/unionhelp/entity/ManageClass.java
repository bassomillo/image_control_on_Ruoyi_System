package com.ruoyi.project.unionhelp.entity;

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
 * 托管班
 * </p>
 *
 * @author zm
 * @since 2021-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManageClass extends Model<ManageClass> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 举办单位
     */
    private String company;

    /**
     * 负责人
     */
    @TableField("leaderUserId")
    private Integer leaderUserId;

    /**
     * 开班时间
     */
    @TableField("startTime")
    private Integer startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private Integer endTime;

    /**
     * 开班数量
     */
    @TableField("classNum")
    private Integer classNum;

    /**
     * 报名人数
     */
    @TableField("willNum")
    private Integer willNum;

    /**
     * 费用
     */
    private String money;

    /**
     * 参加人数
     */
    @TableField("joinNum")
    private Integer joinNum;

    /**
     * 举办形式
     */
    @TableField("holdForm")
    private String holdForm;

    /**
     * 师资来源
     */
    @TableField("teacherSource")
    private String teacherSource;

    /**
     * 场地
     */
    private String site;

    /**
     * 资金来源
     */
    @TableField("moneySource")
    private String moneySource;

    /**
     * 刊发新闻稿外部媒体数量
     */
    @TableField("outNewNum")
    private Integer outNewNum;

    /**
     * 刊发新闻稿内部媒体数量
     */
    @TableField("inNewNum")
    private Integer inNewNum;

    /**
     * 刊发新闻稿其他媒体数量
     */
    @TableField("otherNewNum")
    private Integer otherNewNum;

    /**
     * 课程内容
     */
    private String content;

    /**
     * 补充说明
     */
    private String remark;

    /**
     * 是否提交
     */
    @TableField("isCommit")
    private Boolean isCommit;

    /**
     * 所属机构
     */
    @TableField("orgId")
    private Integer orgId;

    /**
     * 创建用户id
     */
    @TableField("createUserId")
    private Integer createUserId;

    /**
     * 更新用户id
     */
    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Integer createdTime;

    /**
     * 更新时间
     */
    @TableField("updatedTime")
    private Integer updatedTime;


    public static final String ID = "id";

    public static final String COMPANY = "company";

    public static final String LEADERUSERID = "leaderUserId";

    public static final String STARTTIME = "startTime";

    public static final String ENDTIME = "endTime";

    public static final String CLASSNUM = "classNum";

    public static final String WILLNUM = "willNum";

    public static final String MONEY = "money";

    public static final String JOINNUM = "joinNum";

    public static final String HOLDFORM = "holdForm";

    public static final String TEACHERSOURCE = "teacherSource";

    public static final String SITE = "site";

    public static final String MONEYSOURCE = "moneySource";

    public static final String OUTNEWNUM = "outNewNum";

    public static final String INNEWNUM = "inNewNum";

    public static final String OTHERNEWNUM = "otherNewNum";

    public static final String CONTENT = "content";

    public static final String REMARK = "remark";

    public static final String ISCOMMIT = "isCommit";

    public static final String ORGID = "orgId";

    public static final String CREATEUSERID = "createUserId";

    public static final String UPDATEUSERID = "updateUserId";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

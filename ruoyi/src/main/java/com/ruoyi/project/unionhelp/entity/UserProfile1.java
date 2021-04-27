package com.ruoyi.project.unionhelp.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author crl
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserProfile1 extends Model<UserProfile1> {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * MSS账号
     */
    private String mss;

    /**
     * 真实姓名
     */
    private String truename;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 性别
     */
    private String gender;

    /**
     * 我是谁
     */
    private String iam;

    /**
     * 城市
     */
    private String city;

    /**
     * 手机
     */
    private String mobile;

    /**
     * QQ
     */
    private String qq;

    /**
     * 签名
     */
    private String signature;

    /**
     * 自我介绍
     */
    private String about;

    /**
     * 公司
     */
    private String company;

    /**
     * 工作
     */
    private String job;

    /**
     * 学校
     */
    private String school;


    /**
     * 微博
     */
    private String weibo;

    /**
     * 微信
     */
    private String weixin;

    /**
     * QQ号是否公开
     */
    @TableField("isQQPublic")
    private Integer isQQPublic;

    /**
     * 微信是否公开
     */
    @TableField("isWeixinPublic")
    private Integer isWeixinPublic;

    /**
     * 微博是否公开
     */
    @TableField("isWeiboPublic")
    private Integer isWeiboPublic;

    /**
     * 网站
     */
    private String site;

    /**
     * 党内职务
     */
    @TableField("partPost")
    private String partPost;

    /**
     * 困难员工
     */
    private String hardship;

    /**
     * 职工代表
     */
    @TableField("workerRepresentative")
    private String workerRepresentative;

    /**
     * 岗位层级
     */
    @TableField("postLevel")
    private String postLevel;

    /**
     * 最高学历
     */
    @TableField("highestEducation")
    private String highestEducation;

    /**
     * 岗位名称
     */
    @TableField("postName")
    private String postName;

    /**
     * 学位
     */
    private String degree;

    /**
     * 政治面貌
     */
    @TableField("politicalAffiliation")
    private String politicalAffiliation;

    /**
     * 技术职称
     */
    private String professional;

    /**
     * 荣誉
     */
    @TableField("modelWorkers")
    private String modelWorkers;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;

    /**
     * 民族
     */
    private String nation;

    /**
     * 重大疾病互助会会员
     */
    @TableField("concurMember")
    private String concurMember;

    /**
     * 工会职务
     */
    @TableField("unionJob")
    private String unionJob;

    /**
     * 工会小组划分
     */
    @TableField("unionGroup")
    private String unionGroup;

    /**
     * 党群机构
     */
    @TableField("partyGroup")
    private String partyGroup;

    /**
     * 户口地址
     */
    @TableField("accountAddress")
    private String accountAddress;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 兴趣爱好
     */
    private String hobbies;

    /**
     * 个人荣誉
     */
    @TableField("honoraryTitle")
    private String honoraryTitle;

    /**
     * 致困原因
     */
    @TableField("sympathyRecord")
    private String sympathyRecord;

    /**
     * 备注
     */
    @TableField("projectProgress")
    private String projectProgress;

    /**
     * 用工形式
     */
    @TableField("employmentForm")
    private String employmentForm;

    /**
     * 工作起始时间
     */
    @TableField("joinWorkDate")
    private LocalDate joinWorkDate;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 是否会员
     */
    @TableField("isDerafa")
    private Boolean isDerafa;

    /**
     * 是否临时人员
     */
    @TableField("isTemp")
    private Integer isTemp;


    public static final String ID = "id";

    public static final String MSS = "mss";

    public static final String TRUENAME = "truename";

    public static final String IDCARD = "idcard";

    public static final String GENDER = "gender";

    public static final String IAM = "iam";

    public static final String CITY = "city";

    public static final String MOBILE = "mobile";

    public static final String QQ = "qq";

    public static final String SIGNATURE = "signature";

    public static final String ABOUT = "about";

    public static final String COMPANY = "company";

    public static final String JOB = "job";

    public static final String SCHOOL = "school";

    public static final String CLASS = "class";

    public static final String WEIBO = "weibo";

    public static final String WEIXIN = "weixin";

    public static final String ISQQPUBLIC = "isQQPublic";

    public static final String ISWEIXINPUBLIC = "isWeixinPublic";

    public static final String ISWEIBOPUBLIC = "isWeiboPublic";

    public static final String SITE = "site";

    public static final String PARTPOST = "partPost";

    public static final String HARDSHIP = "hardship";

    public static final String WORKERREPRESENTATIVE = "workerRepresentative";

    public static final String POSTLEVEL = "postLevel";

    public static final String HIGHESTEDUCATION = "highestEducation";

    public static final String POSTNAME = "postName";

    public static final String DEGREE = "degree";

    public static final String POLITICALAFFILIATION = "politicalAffiliation";

    public static final String PROFESSIONAL = "professional";

    public static final String MODELWORKERS = "modelWorkers";

    public static final String EMAIL = "email";

    public static final String DEPARTMENT = "department";

    public static final String NATION = "nation";

    public static final String CONCURMEMBER = "concurMember";

    public static final String UNIONJOB = "unionJob";

    public static final String UNIONGROUP = "unionGroup";

    public static final String PARTYGROUP = "partyGroup";

    public static final String ACCOUNTADDRESS = "accountAddress";

    public static final String ADDRESS = "address";

    public static final String HOBBIES = "hobbies";

    public static final String HONORARYTITLE = "honoraryTitle";

    public static final String SYMPATHYRECORD = "sympathyRecord";

    public static final String PROJECTPROGRESS = "projectProgress";

    public static final String EMPLOYMENTFORM = "employmentForm";

    public static final String JOINWORKDATE = "joinWorkDate";

    public static final String BIRTHDAY = "birthday";

    public static final String ISDERAFA = "isDerafa";

    public static final String ISTEMP = "isTemp";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

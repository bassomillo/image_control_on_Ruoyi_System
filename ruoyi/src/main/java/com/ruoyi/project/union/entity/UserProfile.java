package com.ruoyi.project.union.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserProfile extends Model<UserProfile> {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id，编辑用户时必填")
    private Integer id;

    @ApiModelProperty(value = "MSS账号")
    private String mss;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "用户姓名，必填")
    private String truename;

    @ApiModelProperty(value = "身份证号码，必填")
    private String idcard;

    @ApiModelProperty(value = "用户性别，必填")
    private String gender;

    /**
     * 我是谁
     */
    private String iam;

    /**
     * 城市
     */
    private String city;

    @ApiModelProperty(value = "手机号码，必填")
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
    @ApiModelProperty(value = "工作单位？ ")
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
     * 班级
     */
    @TableField("class")
    private String className;

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

    @TableField("partPost")
    @ApiModelProperty(value = "党内职务")
    private String partPost;

    @ApiModelProperty(value = "困难员工")
    private String hardship;

    @TableField("workerRepresentative")
    @ApiModelProperty(value = "职工代表")
    private String workerRepresentative;

    /**
     * 岗位层级
     */
    @TableField("postLevel")
    private String postLevel;

    @TableField("highestEducation")
    @ApiModelProperty(value = "最高学历")
    private String highestEducation;

    @TableField("postName")
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "学位")
    private String degree;

    @TableField("politicalAffiliation")
    @ApiModelProperty(value = "政治面貌")
    private String politicalAffiliation;

    @ApiModelProperty(value = "技术职称")
    private String professional;

    @TableField("modelWorkers")
    @ApiModelProperty(value = "荣誉")
    private String modelWorkers;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "民族")
    private String nation;

    @TableField("concurMember")
    @ApiModelProperty(value = "重大疾病互助会会员", example = "否")
    private String concurMember;

    /**
     * 工会职务
     */
    @TableField("unionJob")
    private String unionJob;

    @TableField("unionGroup")
    @ApiModelProperty(value = "工会小组划分，必填")
    private String unionGroup;

    @TableField("partyGroup")
    @ApiModelProperty(value = "党群机构")
    private String partyGroup;

    @TableField("accountAddress")
    @ApiModelProperty(value = "户口地址")
    private String accountAddress;

    @ApiModelProperty(value = "居住地址")
    private String address;

    @ApiModelProperty(value = "兴趣爱好")
    private String hobbies;

    @TableField("honoraryTitle")
    @ApiModelProperty(value = "个人荣誉")
    private String honoraryTitle;

    @TableField("sympathyRecord")
    @ApiModelProperty(value = "致困原因")
    private String sympathyRecord;

    @TableField("projectProgress")
    @ApiModelProperty(value = "b欸住")
    private String projectProgress;

    @TableField("employmentForm")
    @ApiModelProperty(value = "用工形式")
    private String employmentForm;

    /**
     * 工作起始时间
     */
    @TableField("joinWorkDate")
    @ApiModelProperty(value = "工作时间")
    private LocalDate joinWorkDate;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @TableField("isDerafa")
    @ApiModelProperty(value = "是否会员", example = "否")
    private Integer isDerafa;

    @TableField("isTemp")
    @ApiModelProperty(value = "是否临时人员", example = "否")
    private Integer isTemp;

    /******************************************************************************************************************/
    @TableField(exist = false)
    @ApiModelProperty(value = "所属机构，归类user表，必填")
    private Integer orgId;


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

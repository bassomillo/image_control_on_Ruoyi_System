package com.ruoyi.project.unionhelp.entity;

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
 * 困难员工记录表
 * </p>
 *
 * @author crl
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DifficultEmployeesRecord extends Model<DifficultEmployeesRecord> {

    private static final long serialVersionUID=1L;

    /**
     * id号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id号")
    private Integer id;

    /**
     * 申请用户id
     */
    @TableField("userId")
    @ApiModelProperty(value = "申请用户id")
    private Integer userId;

    /**
     * 姓名
     */
    @TableField("truename")
    @ApiModelProperty(value = "姓名")
    private String truename;

    /**
     * 工作单位
     */
    @TableField("company")
    @ApiModelProperty(value = "工作单位")
    private String company;

    /**
     * 性别
     */
    @TableField("gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 所在部门
     */
    @TableField("department")
    @ApiModelProperty(value = "所在部门")
    private String department;

    /**
     * 身份证号
     */
    @TableField("idcard")
    @ApiModelProperty(value = "身份证号")
    private String idcard;

    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构")
    @TableField("orgId")
    private Integer orgId;

    /**
     * 建档标准
     */
    @TableField("standard")
    @ApiModelProperty(value = "建档标准")
    private String standard;

    /**
     * 困难类型
     */
    @TableField("difficultType")
    @ApiModelProperty(value = "困难类型")
    private String difficultType;

    /**
     * 劳模类型
     */
    @TableField("modelType")
    @ApiModelProperty(value = "劳模类型")
    private String modelType;

    /**
     * 困难员工类型
     */
    @TableField("difficultStaffType")
    @ApiModelProperty(value = "困难员工类型")
    private String difficultStaffType;

    /**
     * 主要致困原因
     */
    @TableField("primaryCause")
    @ApiModelProperty(value = "主要致困原因")
    private String primaryCause;

    /**
     * 户口所在地行政区划
     */
    @TableField("registeredAdministrativeArea")
    @ApiModelProperty(value = "户口所在地行政区划")
    private String registeredAdministrativeArea;

    /**
     * 次要致困原因
     */
    @TableField("minorCause")
    @ApiModelProperty(value = "次要致困原因")
    private String minorCause;

    /**
     * 健康状态
     */
    @TableField("healthStatus")
    @ApiModelProperty(value = "健康状态")
    private String healthStatus;

    /**
     * 疾病类型
     */
    @TableField("diseaseType")
    @ApiModelProperty(value = "疾病类型")
    private String diseaseType;

    /**
     * 残疾类型
     */
    @TableField("disabilityType")
    @ApiModelProperty(value = "残疾类型")
    private String disabilityType;

    /**
     * 医保状况
     */
    @TableField("healthInsuranceStatus")
    @ApiModelProperty(value = "医保状况")
    private String healthInsuranceStatus;

    /**
     * 月平均收入
     */
    @TableField("averageMonthlyIncome")
    @ApiModelProperty(value = "月平均收入")
    private Float averageMonthlyIncome;

    /**
     * 家庭年度总收入
     */
    @TableField("familyTotalIncome")
    @ApiModelProperty(value = "家庭年度总收入")
    private Float familyTotalIncome;

    /**
     * 家庭其他非薪资年收入
     */
    @TableField("otherFamilyTotalIncome")
    @ApiModelProperty(value = "家庭其他非薪资年收入")
    private Float otherFamilyTotalIncome;

    /**
     * 家庭月人均收入
     */
    @TableField("familyPerMonthlyIncome")
    @ApiModelProperty(value = "家庭月人均收入")
    private Float familyPerMonthlyIncome;

    /**
     * 年度必要支出
     */
    @TableField("annualNecessaryExpend")
    @ApiModelProperty(value = "年度必要支出")
    private Float annualNecessaryExpend;

    /**
     * 家庭人数
     */
    @TableField("familySize")
    @ApiModelProperty(value = "家庭人数")
    private Integer familySize;

    /**
     * 是否具有自救能力 1 有 0 无
     */
    @TableField("isSaveAbility")
    @ApiModelProperty(value = "是否具有自救能力 1 有 0 无")
    private Boolean isSaveAbility;

    /**
     * 是否为零就业家庭1是0否
     */
    @TableField("isZeroEmploymentFamily")
    @ApiModelProperty(value = "是否为零就业家庭1是0否")
    private Boolean isZeroEmploymentFamily;

    /**
     * 是否为单亲家庭
     */
    @TableField("isSingleParent")
    @ApiModelProperty(value = "是否为单亲家庭")
    private Boolean isSingleParent;

    /**
     * 居民类型
     */
    @TableField("residentsType")
    @ApiModelProperty(value = "居民类型")
    private String residentsType;

    /**
     * 住房类型
     */
    @TableField("housingType")
    @ApiModelProperty(value = "住房类型")
    private String housingType;

    /**
     * 建筑面积
     */
    @TableField("buildingArea")
    @ApiModelProperty(value = "建筑面积")
    private String buildingArea;

    /**
     * 开户行银行
     */
    @TableField("bankType")
    @ApiModelProperty(value = "开户行银行")
    private String bankType;

    /**
     * 支行名称
     */
    @TableField("bankName")
    @ApiModelProperty(value = "支行名称")
    private String bankName;

    /**
     * 银行账户
     */
    @TableField("bankAccount")
    @ApiModelProperty(value = "银行账户")
    private String bankAccount;

    /**
     * 困难状况
     */
    @TableField("difficultStatus")
    @ApiModelProperty(value = "困难状况")
    private String difficultStatus;

    /**
     * 人员状态
     */
    @TableField("staffStatus")
    @ApiModelProperty(value = "人员状态")
    private String staffStatus;

    /**
     * 其他
     */
    @TableField("other")
    @ApiModelProperty(value = "其他")
    private String other;

    /**
     * 家庭成员
     */
    @TableField("familyMembers")
    @ApiModelProperty(value = "家庭成员")
    private String familyMembers;

    /**
     * 文档状态
     */
    @TableField("status")
    @ApiModelProperty(value = "文档状态")
    private String status;

    /**
     * 审核状态
     */
    @TableField("auditStatus")
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    /**
     * 审核用户id
     */
    @TableField("auditUserId")
    @ApiModelProperty(value = "审核用户id")
    private Integer auditUserId;

    /**
     * 建档时间
     */
    @TableField("filingTime")
    @ApiModelProperty(value = "建档时间")
    private Integer filingTime;

    /**
     * 驳回原因
     */
    @TableField("reason")
    @ApiModelProperty(value = "驳回原因")
    private String reason;

    /**
     * 创建时间
     */
    @TableField("createdTime")
    @ApiModelProperty(value = "创建时间")
    private Integer createdTime;

    /**
     * 更新时间
     */
    @TableField("updatedTime")
    @ApiModelProperty(value = "更新时间")
    private Integer updatedTime;

    /**
     * 手机号码
     */
    @TableField("mobile")
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    /**
     * 用工形式
     */
    @TableField("employmentForm")
    @ApiModelProperty(value = "用工形式")
    private String employmentForm;

    /**
     * 帮扶计划
     */
    @TableField("helpPlan")
    @ApiModelProperty(value = "帮扶计划")
    private String helpPlan;

    /**
     * 帮扶需求
     */
    @TableField("helpRequire")
    @ApiModelProperty(value = "帮扶需求")
    private String helpRequire;

    /**
     * 提交者id
     */
    @TableField("submitUserId")
    @ApiModelProperty(value = "提交者id")
    private Integer submitUserId;

    /**
     * 是否脱贫 0脱贫1贫困
     */
    @TableField("isPoverty")
    @ApiModelProperty(value = "是否脱贫 0脱贫1贫困")
    private Boolean isPoverty;

    /**
     * 是否注销
     */
    @TableField("isLogout")
    @ApiModelProperty(value = "是否注销")
    private Boolean isLogout;


    public static final String ID = "id";

    public static final String USERID = "userId";

    public static final String TRUENAME = "truename";

    public static final String COMPANY = "company";

    public static final String GENDER = "gender";

    public static final String DEPARTMENT = "department";

    public static final String IDCARD = "idcard";

    public static final String ORGID = "orgId";

    public static final String STANDARD = "standard";

    public static final String DIFFICULTTYPE = "difficultType";

    public static final String MODELTYPE = "modelType";

    public static final String DIFFICULTSTAFFTYPE = "difficultStaffType";

    public static final String PRIMARYCAUSE = "primaryCause";

    public static final String REGISTEREDADMINISTRATIVEAREA = "registeredAdministrativeArea";

    public static final String MINORCAUSE = "minorCause";

    public static final String HEALTHSTATUS = "healthStatus";

    public static final String DISEASETYPE = "diseaseType";

    public static final String DISABILITYTYPE = "disabilityType";

    public static final String HEALTHINSURANCESTATUS = "healthInsuranceStatus";

    public static final String AVERAGEMONTHLYINCOME = "averageMonthlyIncome";

    public static final String FAMILYTOTALINCOME = "familyTotalIncome";

    public static final String OTHERFAMILYTOTALINCOME = "otherFamilyTotalIncome";

    public static final String FAMILYPERMONTHLYINCOME = "familyPerMonthlyIncome";

    public static final String ANNUALNECESSARYEXPEND = "annualNecessaryExpend";

    public static final String FAMILYSIZE = "familySize";

    public static final String ISSAVEABILITY = "isSaveAbility";

    public static final String ISZEROEMPLOYMENTFAMILY = "isZeroEmploymentFamily";

    public static final String ISSINGLEPARENT = "isSingleParent";

    public static final String RESIDENTSTYPE = "residentsType";

    public static final String HOUSINGTYPE = "housingType";

    public static final String BUILDINGAREA = "buildingArea";

    public static final String BANKTYPE = "bankType";

    public static final String BANKNAME = "bankName";

    public static final String BANKACCOUNT = "bankAccount";

    public static final String DIFFICULTSTATUS = "difficultStatus";

    public static final String STAFFSTATUS = "staffStatus";

    public static final String OTHER = "other";

    public static final String FAMILYMEMBERS = "familyMembers";

    public static final String STATUS = "status";

    public static final String AUDITSTATUS = "auditStatus";

    public static final String AUDITUSERID = "auditUserId";

    public static final String FILINGTIME = "filingTime";

    public static final String REASON = "reason";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    public static final String MOBILE = "mobile";

    public static final String EMPLOYMENTFORM = "employmentForm";

    public static final String HELPPLAN = "helpPlan";

    public static final String HELPREQUIRE = "helpRequire";

    public static final String SUBMITUSERID = "submitUserId";

    public static final String ISPOVERTY = "isPoverty";

    public static final String ISLOGOUT = "isLogout";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

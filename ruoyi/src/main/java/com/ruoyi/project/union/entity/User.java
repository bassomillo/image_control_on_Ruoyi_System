package com.ruoyi.project.union.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("verifiedMobile")
    private String verifiedMobile;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 密码SALT
     */
    private String salt;

    /**
     * 支付密码
     */
    @TableField("payPassword")
    private String payPassword;

    /**
     * 支付密码Salt
     */
    @TableField("payPasswordSalt")
    private String payPasswordSalt;

    private String locale;

    /**
     * 用户URI
     */
    private String uri;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 头衔
     */
    private String title;

    /**
     * 标签
     */
    private String tags;

    /**
     * default默认为网站注册, weibo新浪微薄登录
     */
    private String type;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 金币
     */
    private Integer coin;

    /**
     * 小头像
     */
    @TableField("smallAvatar")
    private String smallAvatar;

    /**
     * 中头像
     */
    @TableField("mediumAvatar")
    private String mediumAvatar;

    /**
     * 大头像
     */
    @TableField("largeAvatar")
    private String largeAvatar;

    /**
     * 邮箱是否为已验证
     */
    @TableField("emailVerified")
    private Integer emailVerified;

    /**
     * 是否初始化设置的，未初始化的可以设置邮箱、用户名。
     */
    private Integer setup;

    /**
     * 用户角色
     */
    private String roles;

    /**
     * 是否为推荐
     */
    private Integer promoted;

    @TableField("promotedSeq")
    private Integer promotedSeq;

    /**
     * 推荐时间
     */
    @TableField("promotedTime")
    private Integer promotedTime;

    /**
     * 是否被禁止
     */
    private Integer locked;

    /**
     * 帐号锁定期限
     */
    @TableField("lockDeadline")
    private Integer lockDeadline;

    /**
     * 帐号密码错误次数
     */
    @TableField("consecutivePasswordErrorTimes")
    private Integer consecutivePasswordErrorTimes;

    @TableField("lastPasswordFailTime")
    private Integer lastPasswordFailTime;

    /**
     * 最后登录时间
     */
    @TableField("loginTime")
    private Integer loginTime;

    /**
     * 最后登录IP
     */
    @TableField("loginIp")
    private String loginIp;

    /**
     * 最后登录会话ID
     */
    @TableField("loginSessionId")
    private String loginSessionId;

    /**
     * 实名认证时间
     */
    @TableField("approvalTime")
    private Integer approvalTime;

    /**
     * 实名认证状态
     */
    @TableField("approvalStatus")
    private String approvalStatus;

    /**
     * 未读私信数
     */
    @TableField("newMessageNum")
    private Integer newMessageNum;

    /**
     * 未读消息数
     */
    @TableField("newNotificationNum")
    private Integer newNotificationNum;

    /**
     * 注册IP
     */
    @TableField("createdIp")
    private String createdIp;

    /**
     * 注册时间
     */
    @TableField("createdTime")
    private Integer createdTime;

    /**
     * 最后更新时间
     */
    @TableField("updatedTime")
    private Integer updatedTime;

    /**
     * 邀请码
     */
    @TableField("inviteCode")
    private String inviteCode;

    /**
     * 组织机构ID
     */
    @TableField("orgId")
    private Integer orgId;

    /**
     * 组织机构内部编码
     */
    @TableField("orgCode")
    private String orgCode;

    /**
     * 注册方式
     */
    @TableField("registeredWay")
    private String registeredWay;

    /**
     * 绑定状态
     */
    @TableField("bindStatus")
    private Integer bindStatus;

    /**
     * 确认时间
     */
    @TableField("confirmTime")
    private Integer confirmTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 签名照
     */
    @TableField("signatureImg")
    private String signatureImg;


    public static final String ID = "id";

    public static final String VERIFIEDMOBILE = "verifiedMobile";

    public static final String PASSWORD = "password";

    public static final String SALT = "salt";

    public static final String PAYPASSWORD = "payPassword";

    public static final String PAYPASSWORDSALT = "payPasswordSalt";

    public static final String LOCALE = "locale";

    public static final String URI = "uri";

    public static final String NICKNAME = "nickname";

    public static final String TITLE = "title";

    public static final String TAGS = "tags";

    public static final String TYPE = "type";

    public static final String POINT = "point";

    public static final String COIN = "coin";

    public static final String SMALLAVATAR = "smallAvatar";

    public static final String MEDIUMAVATAR = "mediumAvatar";

    public static final String LARGEAVATAR = "largeAvatar";

    public static final String EMAILVERIFIED = "emailVerified";

    public static final String SETUP = "setup";

    public static final String ROLES = "roles";

    public static final String PROMOTED = "promoted";

    public static final String PROMOTEDSEQ = "promotedSeq";

    public static final String PROMOTEDTIME = "promotedTime";

    public static final String LOCKED = "locked";

    public static final String LOCKDEADLINE = "lockDeadline";

    public static final String CONSECUTIVEPASSWORDERRORTIMES = "consecutivePasswordErrorTimes";

    public static final String LASTPASSWORDFAILTIME = "lastPasswordFailTime";

    public static final String LOGINTIME = "loginTime";

    public static final String LOGINIP = "loginIp";

    public static final String LOGINSESSIONID = "loginSessionId";

    public static final String APPROVALTIME = "approvalTime";

    public static final String APPROVALSTATUS = "approvalStatus";

    public static final String NEWMESSAGENUM = "newMessageNum";

    public static final String NEWNOTIFICATIONNUM = "newNotificationNum";

    public static final String CREATEDIP = "createdIp";

    public static final String CREATEDTIME = "createdTime";

    public static final String UPDATEDTIME = "updatedTime";

    public static final String INVITECODE = "inviteCode";

    public static final String ORGID = "orgId";

    public static final String ORGCODE = "orgCode";

    public static final String REGISTEREDWAY = "registeredWay";

    public static final String BINDSTATUS = "bindStatus";

    public static final String CONFIRMTIME = "confirmTime";

    public static final String EMAIL = "email";

    public static final String SIGNATUREIMG = "signatureImg";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

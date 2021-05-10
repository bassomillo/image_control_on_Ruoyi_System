package com.ruoyi.project.chairmanOnline.entity;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author weide
 * @since 2021-04-29 11:22:38
 */
public class SocketUser implements Serializable {
    private static final long serialVersionUID = 625018277999408428L;
    /**
     * 用户ID
     */
    private Integer id;

    private String verifiedmobile;
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
    private String paypassword;
    /**
     * 支付密码Salt
     */
    private String paypasswordsalt;

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
    private String smallavatar;
    /**
     * 中头像
     */
    private String mediumavatar;
    /**
     * 大头像
     */
    private String largeavatar;
    /**
     * 邮箱是否为已验证
     */
    private Integer emailverified;
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

    private Integer promotedseq;
    /**
     * 推荐时间
     */
    private Integer promotedtime;
    /**
     * 是否被禁止
     */
    private Integer locked;
    /**
     * 帐号锁定期限
     */
    private Integer lockdeadline;
    /**
     * 帐号密码错误次数
     */
    private Integer consecutivepassworderrortimes;

    private Integer lastpasswordfailtime;
    /**
     * 最后登录时间
     */
    private Integer logintime;
    /**
     * 最后登录IP
     */
    private String loginip;
    /**
     * 最后登录会话ID
     */
    private String loginsessionid;
    /**
     * 实名认证时间
     */
    private Integer approvaltime;
    /**
     * 实名认证状态
     */
    private Object approvalstatus;
    /**
     * 未读私信数
     */
    private Integer newmessagenum;
    /**
     * 未读消息数
     */
    private Integer newnotificationnum;
    /**
     * 注册IP
     */
    private String createdip;
    /**
     * 注册时间
     */
    private Integer createdtime;
    /**
     * 最后更新时间
     */
    private Integer updatedtime;
    /**
     * 邀请码
     */
    private String invitecode;
    /**
     * 组织机构ID
     */
    private Integer orgid;
    /**
     * 组织机构内部编码
     */
    private String orgcode;
    /**
     * 注册方式
     */
    private String registeredway;
    /**
     * 绑定状态
     */
    private Integer bindstatus;
    /**
     * 确认时间
     */
    private Integer confirmtime;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 签名照
     */
    private String signatureimg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerifiedmobile() {
        return verifiedmobile;
    }

    public void setVerifiedmobile(String verifiedmobile) {
        this.verifiedmobile = verifiedmobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public String getPaypasswordsalt() {
        return paypasswordsalt;
    }

    public void setPaypasswordsalt(String paypasswordsalt) {
        this.paypasswordsalt = paypasswordsalt;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getSmallavatar() {
        return smallavatar;
    }

    public void setSmallavatar(String smallavatar) {
        this.smallavatar = smallavatar;
    }

    public String getMediumavatar() {
        return mediumavatar;
    }

    public void setMediumavatar(String mediumavatar) {
        this.mediumavatar = mediumavatar;
    }

    public String getLargeavatar() {
        return largeavatar;
    }

    public void setLargeavatar(String largeavatar) {
        this.largeavatar = largeavatar;
    }

    public Integer getEmailverified() {
        return emailverified;
    }

    public void setEmailverified(Integer emailverified) {
        this.emailverified = emailverified;
    }

    public Integer getSetup() {
        return setup;
    }

    public void setSetup(Integer setup) {
        this.setup = setup;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getPromoted() {
        return promoted;
    }

    public void setPromoted(Integer promoted) {
        this.promoted = promoted;
    }

    public Integer getPromotedseq() {
        return promotedseq;
    }

    public void setPromotedseq(Integer promotedseq) {
        this.promotedseq = promotedseq;
    }

    public Integer getPromotedtime() {
        return promotedtime;
    }

    public void setPromotedtime(Integer promotedtime) {
        this.promotedtime = promotedtime;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getLockdeadline() {
        return lockdeadline;
    }

    public void setLockdeadline(Integer lockdeadline) {
        this.lockdeadline = lockdeadline;
    }

    public Integer getConsecutivepassworderrortimes() {
        return consecutivepassworderrortimes;
    }

    public void setConsecutivepassworderrortimes(Integer consecutivepassworderrortimes) {
        this.consecutivepassworderrortimes = consecutivepassworderrortimes;
    }

    public Integer getLastpasswordfailtime() {
        return lastpasswordfailtime;
    }

    public void setLastpasswordfailtime(Integer lastpasswordfailtime) {
        this.lastpasswordfailtime = lastpasswordfailtime;
    }

    public Integer getLogintime() {
        return logintime;
    }

    public void setLogintime(Integer logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getLoginsessionid() {
        return loginsessionid;
    }

    public void setLoginsessionid(String loginsessionid) {
        this.loginsessionid = loginsessionid;
    }

    public Integer getApprovaltime() {
        return approvaltime;
    }

    public void setApprovaltime(Integer approvaltime) {
        this.approvaltime = approvaltime;
    }

    public Object getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(Object approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public Integer getNewmessagenum() {
        return newmessagenum;
    }

    public void setNewmessagenum(Integer newmessagenum) {
        this.newmessagenum = newmessagenum;
    }

    public Integer getNewnotificationnum() {
        return newnotificationnum;
    }

    public void setNewnotificationnum(Integer newnotificationnum) {
        this.newnotificationnum = newnotificationnum;
    }

    public String getCreatedip() {
        return createdip;
    }

    public void setCreatedip(String createdip) {
        this.createdip = createdip;
    }

    public Integer getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Integer createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Integer updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getRegisteredway() {
        return registeredway;
    }

    public void setRegisteredway(String registeredway) {
        this.registeredway = registeredway;
    }

    public Integer getBindstatus() {
        return bindstatus;
    }

    public void setBindstatus(Integer bindstatus) {
        this.bindstatus = bindstatus;
    }

    public Integer getConfirmtime() {
        return confirmtime;
    }

    public void setConfirmtime(Integer confirmtime) {
        this.confirmtime = confirmtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignatureimg() {
        return signatureimg;
    }

    public void setSignatureimg(String signatureimg) {
        this.signatureimg = signatureimg;
    }

}

package com.ruoyi.project.chairmanOnline.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * (UserProfile)实体类
 *
 * @author weide
 * @since 2021-05-14 10:37:42
 */
public class IMUserProfile implements Serializable {
    private static final long serialVersionUID = -79826654738790200L;
    /**
     * 用户ID
     */
    private Object id;
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
     * 班级
     */
    @TableField("class")
    private String userClass;
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
    private Integer isqqpublic;
    /**
     * 微信是否公开
     */
    private Integer isweixinpublic;
    /**
     * 微博是否公开
     */
    private Integer isweibopublic;
    /**
     * 网站
     */
    private String site;
    /**
     * 党内职务
     */
    private String partpost;
    /**
     * 困难员工
     */
    private String hardship;
    /**
     * 职工代表
     */
    private String workerrepresentative;
    /**
     * 岗位层级（专业模块）
     */
    private String postlevel;
    /**
     * 最高学历
     */
    private String highesteducation;
    /**
     * 岗位名称
     */
    private String postname;
    /**
     * 学位
     */
    private String degree;
    /**
     * 政治面貌
     */
    private String politicalaffiliation;
    /**
     * 技术职称
     */
    private String professional;
    /**
     * 荣誉
     */
    private String modelworkers;
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
    private String concurmember;
    /**
     * 工会职务
     */
    private String unionjob;
    /**
     * 工会小组划分
     */
    private String uniongroup;
    /**
     * 党群机构
     */
    private String partygroup;
    /**
     * 户口地址
     */
    private String accountaddress;
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
    private String honorarytitle;
    /**
     * 致困原因
     */
    private String sympathyrecord;
    /**
     * 备注
     */
    private String projectprogress;
    /**
     * 用工形式
     */
    private String employmentform;
    /**
     * 工作起始时间
     */
    private Object joinworkdate;
    /**
     * 出生日期
     */
    private Object birthday;
    /**
     * 是否会员
     */
    private Integer isderafa;
    /**
     * 是否临时人员
     */
    private Integer istemp;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getMss() {
        return mss;
    }

    public void setMss(String mss) {
        this.mss = mss;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIam() {
        return iam;
    }

    public void setIam(String iam) {
        this.iam = iam;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getIsqqpublic() {
        return isqqpublic;
    }

    public void setIsqqpublic(Integer isqqpublic) {
        this.isqqpublic = isqqpublic;
    }

    public Integer getIsweixinpublic() {
        return isweixinpublic;
    }

    public void setIsweixinpublic(Integer isweixinpublic) {
        this.isweixinpublic = isweixinpublic;
    }

    public Integer getIsweibopublic() {
        return isweibopublic;
    }

    public void setIsweibopublic(Integer isweibopublic) {
        this.isweibopublic = isweibopublic;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPartpost() {
        return partpost;
    }

    public void setPartpost(String partpost) {
        this.partpost = partpost;
    }

    public String getHardship() {
        return hardship;
    }

    public void setHardship(String hardship) {
        this.hardship = hardship;
    }

    public String getWorkerrepresentative() {
        return workerrepresentative;
    }

    public void setWorkerrepresentative(String workerrepresentative) {
        this.workerrepresentative = workerrepresentative;
    }

    public String getPostlevel() {
        return postlevel;
    }

    public void setPostlevel(String postlevel) {
        this.postlevel = postlevel;
    }

    public String getHighesteducation() {
        return highesteducation;
    }

    public void setHighesteducation(String highesteducation) {
        this.highesteducation = highesteducation;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPoliticalaffiliation() {
        return politicalaffiliation;
    }

    public void setPoliticalaffiliation(String politicalaffiliation) {
        this.politicalaffiliation = politicalaffiliation;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getModelworkers() {
        return modelworkers;
    }

    public void setModelworkers(String modelworkers) {
        this.modelworkers = modelworkers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getConcurmember() {
        return concurmember;
    }

    public void setConcurmember(String concurmember) {
        this.concurmember = concurmember;
    }

    public String getUnionjob() {
        return unionjob;
    }

    public void setUnionjob(String unionjob) {
        this.unionjob = unionjob;
    }

    public String getUniongroup() {
        return uniongroup;
    }

    public void setUniongroup(String uniongroup) {
        this.uniongroup = uniongroup;
    }

    public String getPartygroup() {
        return partygroup;
    }

    public void setPartygroup(String partygroup) {
        this.partygroup = partygroup;
    }

    public String getAccountaddress() {
        return accountaddress;
    }

    public void setAccountaddress(String accountaddress) {
        this.accountaddress = accountaddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getHonorarytitle() {
        return honorarytitle;
    }

    public void setHonorarytitle(String honorarytitle) {
        this.honorarytitle = honorarytitle;
    }

    public String getSympathyrecord() {
        return sympathyrecord;
    }

    public void setSympathyrecord(String sympathyrecord) {
        this.sympathyrecord = sympathyrecord;
    }

    public String getProjectprogress() {
        return projectprogress;
    }

    public void setProjectprogress(String projectprogress) {
        this.projectprogress = projectprogress;
    }

    public String getEmploymentform() {
        return employmentform;
    }

    public void setEmploymentform(String employmentform) {
        this.employmentform = employmentform;
    }

    public Object getJoinworkdate() {
        return joinworkdate;
    }

    public void setJoinworkdate(Object joinworkdate) {
        this.joinworkdate = joinworkdate;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Integer getIsderafa() {
        return isderafa;
    }

    public void setIsderafa(Integer isderafa) {
        this.isderafa = isderafa;
    }

    public Integer getIstemp() {
        return istemp;
    }

    public void setIstemp(Integer istemp) {
        this.istemp = istemp;
    }

}

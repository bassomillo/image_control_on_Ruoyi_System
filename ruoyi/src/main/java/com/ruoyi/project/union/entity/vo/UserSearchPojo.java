package com.ruoyi.project.union.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
public class UserSearchPojo {

    @ApiModelProperty(value = "第几页", example = "1")
    private Integer current;

    @ApiModelProperty(value = "每页数据量", example = "10")
    private Integer size;

    @ApiModelProperty(value = "用户名 User", example = "超级管理员")
    private String nickname;

    @ApiModelProperty(value = "真实姓名 UserProfile", example = "张三")
    private String truename;

    @ApiModelProperty(value = "手机 UserProfile", example = "111")
    private String mobile;

    @ApiModelProperty(value = "身份证号码 UserProfile", example = "111")
    private String idcard;

    @ApiModelProperty(value = "职工代表 UserProfile", example = "111")
    private String workerRepresentative;

    @ApiModelProperty(value = "困难员工 UserProfile", example = "111")
    private String hardship;

    @ApiModelProperty(value = "用工形式 UserProfile", example = "在册")
    private String employmentForm;

    @ApiModelProperty(value = "荣誉 UserProfile", example = "111")
    private String modelWorkers;

    @ApiModelProperty(value = "政治面貌 UserProfile", example = "群众")
    private String politicalAffiliation;

    /**
     * 所属机构
     */

    @ApiModelProperty(value = "最高学历 UserProfile", example = "本科")
    private String highestEducation;

    @ApiModelProperty(value = "学位 UserProfile", example = "学士")
    private String degree;

    @ApiModelProperty(value = "党内职务 UserProfile", example = "支部书记")
    private String partPost;

    @ApiModelProperty(value = "绑定状态 User", example = "0")
    private Integer bindStatus;

    @ApiModelProperty(value = "工作起始时间开始范围 UserProfile", example = "yyyy-MM-dd")
    private LocalDate joinWorkDateStart;

    @ApiModelProperty(value = "工作起始时间结束范围 UserProfile", example = "yyyy-MM-dd")
    private LocalDate joinWorkDateEnd;

    @ApiModelProperty(value = "性别 UserProfile", example = "male-男，female-女")
    private String gender;

    @ApiModelProperty(value = "邮箱 UserProfile", example = "~@~")
    private String email;

    @ApiModelProperty(value = "是否会员 UserProfile", example = "0")
    private Integer isDerafa;

    @ApiModelProperty(value = "是否被禁止 User", example = "0")
    private Integer locked;
}

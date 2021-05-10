package com.ruoyi.project.unionhelp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DifficultEmployeesHelpRecordSearchRequire {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "第几页")
    private Integer index;

    @ApiModelProperty(value = "搜索内容-用户名/姓名/手机号/邮箱")
    private String searchUser;

    @ApiModelProperty(value = "搜索内容-帮扶人或组织")
    private String searchHelper;
}

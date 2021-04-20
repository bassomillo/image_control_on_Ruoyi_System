package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.democratic.entity.VO.GroupToExamVO;
import com.ruoyi.project.democratic.service.IExamMemberService;
import com.ruoyi.project.democratic.service.IVoteGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 考试成员表 前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/examMember")
@Api(tags = "问卷调查-成员——cxr")
@CrossOrigin
public class ExamMemberController {
    @Autowired
    private IExamMemberService examMemberService;
    @Autowired
    private IVoteGroupService voteGroupService;

    /*****************************************Member************************************************/
    @ApiOperation(value = "组织树-条件获取组织树下人员")
    @PostMapping("/getTreeMember")
    public AjaxResult getTreeMember(@RequestBody GetMemberVO getMember){

        return examMemberService.getTreeMember(getMember);
    }

    @ApiOperation(value = "组织树-添加单个/多个人员至考试")
    @PostMapping("/addMember")
    public AjaxResult addMember(@RequestBody AddTreeMemberVO treeMember){

        return examMemberService.addMember(treeMember);
    }

    @ApiOperation(value = "组织树-添加全部人员至考试")
    @PostMapping("/addAllMember")
    public AjaxResult addAllMember(@RequestBody GetMemberVO getMember){

        return examMemberService.addAllMember(getMember);
    }

    /*****************************************Group************************************************/
    @ApiOperation(value = "分组-新增/创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName", value = "分组名称", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/insertGroup")
    public AjaxResult insertGroup(@RequestParam("groupName") String groupName,
                                  @RequestParam("userId") Integer userId){

        return voteGroupService.insertGroup(groupName, userId);
    }

    @ApiOperation(value = "分组-查询")
    @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    @PostMapping("/getGroup")
    public AjaxResult getGroup(@RequestParam("userId") Integer userId){

        return voteGroupService.getGroup(userId);
    }

    @ApiOperation(value = "分组-重命名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName", value = "分组名字", required = true),
            @ApiImplicitParam(name = "groupId", value = "分组id", required = true)
    })
    @PostMapping("/renameGroup")
    public AjaxResult renameGroup(@RequestParam("groupName") String groupName,
                                  @RequestParam("groupId") Integer groupId){

        return voteGroupService.renameGroup(groupName, groupId);
    }

    @ApiOperation(value = "分组-删除组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "分组id", required = true)
    })
    @PostMapping("/deleteGroup")
    public AjaxResult deleteGroup(@RequestParam("groupId") Integer groupId){

        return voteGroupService.deleteGroup(groupId);
    }

    @ApiOperation(value = "分组-删除组内人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "分组id", required = true),
            @ApiImplicitParam(name = "memberId", value = "人员id", required = true)
    })
    @PostMapping("/deleteGroupMember")
    public AjaxResult deleteGroupMember(@RequestParam("groupId") Integer groupId,
                                        @RequestParam("memberId") Integer memberId){

        return voteGroupService.deleteGroupMember(groupId, memberId);
    }

    @ApiOperation(value = "分组-加入考试")
    @PostMapping("/addToExam")
    public AjaxResult addToExam(@RequestBody GroupToExamVO group){

        return voteGroupService.addToExam(group);
    }
}


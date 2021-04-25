package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.VO.AddTreeMemberVO;
import com.ruoyi.project.democratic.entity.VO.GetMemberVO;
import com.ruoyi.project.democratic.entity.VO.GroupToExamVO;
import com.ruoyi.project.democratic.service.IExamMemberService;
import com.ruoyi.project.democratic.service.IVoteGroupService;
import com.ruoyi.project.tool.ExcelTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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
@Api(tags = "问卷调查-人员限定——cxr")
@CrossOrigin
public class ExamMemberController {
    @Autowired
    private IExamMemberService examMemberService;
    @Autowired
    private IVoteGroupService voteGroupService;

    /*****************************************Tree************************************************/
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

    @ApiOperation(value = "组织树-添加全部人员至考试【若为导入的人员（即无组织树id），调用另一接口】")
    @PostMapping("/addAllMember")
    public AjaxResult addAllMember(@RequestBody GetMemberVO getMember){

        return examMemberService.addAllMember(getMember);
    }

    @ApiOperation(value = "组织树-添加单个/多个人员至分组")
    @PostMapping("/addMemberGroup")
    public AjaxResult addMemberGroup(@RequestBody AddTreeMemberVO treeMember){

        return examMemberService.addMemberGroup(treeMember);
    }

    @ApiOperation(value = "组织树-添加全部人员至分组【若为导入的人员（即无组织树id），调用另一接口】")
    @PostMapping("/addAllMemberGroup")
    public AjaxResult addAllMemberGroup(@RequestBody GetMemberVO getMember){

        return examMemberService.addAllMemberGroup(getMember);
    }

    @ApiOperation(value = "组织树-下载人员导入模板")
    @PostMapping("/downloadMemberModel")
    public AjaxResult downloadMemberModel(HttpServletResponse response){
        try {
            Workbook workbook = null;
            String path = "/static/excel/vote_member_import_example.xls";
            String name = "vote_member_import_example";

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            ExcelTool.export(workbook, name + ".xls", response);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出异常，请联系管理员", e.getMessage());
        }
        return null;
    }

    @ApiOperation(value = "组织树-人员导入")
    @PostMapping("/importMember")
    public AjaxResult importMember(MultipartFile file,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest request){

        return examMemberService.importMember(file, pageNum, pageSize, request);
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

    /*****************************************Member************************************************/
    @ApiOperation(value = "参考人员-条件查询参考人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "name", value = "真实姓名"),
            @ApiImplicitParam(name = "employmentForm", value = "用工形式"),
            @ApiImplicitParam(name = "mobile", value = "联系电话"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getExamMemberList")
    public AjaxResult getExamMemberList(@RequestParam("examId") Integer examId,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "employmentForm", required = false) String employmentForm,
                                        @RequestParam(value = "mobile", required = false) String mobile,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return examMemberService.getExamMemberList(examId, name, employmentForm, mobile, pageNum, pageSize);
    }

    @ApiOperation(value = "参考人员-删除人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "人员id", required = true)
    })
    @PostMapping("/deleteExamMember")
    public AjaxResult deleteExamMember(@RequestParam("examId") Integer examId,
                                       @RequestParam("userId") Integer userId){

        return examMemberService.deleteExamMember(examId, userId);
    }
}


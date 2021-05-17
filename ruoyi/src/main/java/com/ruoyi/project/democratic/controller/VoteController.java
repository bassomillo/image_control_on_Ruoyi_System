package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Vote;
import com.ruoyi.project.democratic.entity.VotePaper;
import com.ruoyi.project.democratic.entity.VoteQuestion;
import com.ruoyi.project.democratic.service.IVoteService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 投票表 前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/vote")
@Api(tags = "问卷调查-投票——cxr")
public class VoteController {

    @Autowired
    private IVoteService voteService;

    @ApiOperation(value = "后台-新增/创建投票")
    @PostMapping("/insertVote")
    public AjaxResult insertVote(@RequestBody Vote vote){

        return voteService.insertVote(vote);
    }

    @ApiOperation(value = "后台-条件查询投票列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "查询的标题内容"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getBackVoteList")
    public AjaxResult getBackVoteList(@RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return voteService.getBackVoteList(title, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-置顶/取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "sticky", value = "是否置顶，1置顶，0不置顶", required = true)
    })
    @PostMapping("/setTop")
    public AjaxResult setTop(@RequestParam("voteId") Integer voteId,
                             @RequestParam("sticky") Integer sticky){

        return voteService.setTop(voteId, sticky);
    }

    @ApiOperation(value = "后台-发布/取消发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "isPublish", value = "true发布，false取消发布", required = true)
    })
    @PostMapping("/publishOrUn")
    public AjaxResult publishOrUn(@RequestParam("voteId") Integer voteId,
                                  @RequestParam("userId") Integer userId,
                                  @RequestParam("isPublish") boolean isPublish){

        return voteService.publishOrUn(voteId, userId, isPublish);
    }

    @ApiOperation(value = "后台-回收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/over")
    public AjaxResult over(@RequestParam("voteId") Integer voteId,
                           @RequestParam("userId") Integer userId){

        return voteService.over(voteId, userId);
    }

    @ApiOperation(value = "后台-删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/deleteBack")
    public AjaxResult deleteBack(@RequestParam("voteId") Integer voteId,
                                 @RequestParam("userId") Integer userId){

        return voteService.deleteBack(voteId, userId);
    }

    @ApiOperation(value = "后台-重命名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "title", value = "重命名的标题", required = true)
    })
    @PostMapping("rename")
    public AjaxResult rename(@RequestParam("voteId") Integer voteId,
                             @RequestParam("userId") Integer userId,
                             @RequestParam("title") String title){

        return voteService.rename(voteId, userId, title);
    }

    @ApiOperation(value = "后台-根据id查询投票详情")
    @PostMapping("/getBackDetail")
    public AjaxResult getBackDetail(@RequestParam("voteId") Integer voteId){

        return voteService.getBackDetail(voteId);
    }

    @ApiOperation(value = "后台-修改基础信息")
    @PostMapping("/updateBaseData")
    public AjaxResult updateBaseData(@RequestBody Vote voteBase){

        return voteService.updateBaseData(voteBase);
    }

    @ApiOperation(value = "后台-编辑标题、封面")
    @PostMapping("/updateCover")
    public AjaxResult updateCover(@RequestBody Vote voteCover){

        return voteService.updateCover(voteCover);
    }

    @ApiOperation(value = "后台-添加题目")
    @PostMapping("/insertQuestion")
    public AjaxResult insertQuestion(@RequestBody VoteQuestion question){

        return voteService.insertQuestion(question);
    }

    @ApiOperation(value = "后台-编辑题目")
    @PostMapping("/updateQuestion")
    public AjaxResult updateQuestion(@RequestBody VoteQuestion question){

        return voteService.updateQuestion(question);
    }

    @ApiOperation(value = "后台-删除题目/选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "题目id，删题目时传"),
            @ApiImplicitParam(name = "optionId", value = "选项id，删选项时传")
    })
    @PostMapping("/deleteQuestionOrOption")
    public AjaxResult deleteQuestionOrOption(@RequestParam(value = "questionId", required = false) Integer questionId,
                                             @RequestParam(value = "optionId", required = false) Integer optionId){

        return voteService.deleteQuestionOrOption(questionId, optionId);
    }

    @ApiOperation(value = "后台-下载导题模板")
    @GetMapping("/downloadQuestionModel")
    public AjaxResult downloadQuestionModel(HttpServletResponse response){
        try {
            Workbook workbook = null;
            String path = "/static/excel/vote_import_example.xls";
            String name = "vote_import_example";

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

    @ApiOperation(value = "后台-校验导入")
    @PostMapping("/checkImportQuestion")
    public AjaxResult checkImportQuestion(MultipartFile file,
                                          @RequestParam("voteId") Integer voteId){

        return voteService.checkImportQuestion(file, voteId);
    }

    @ApiOperation(value = "后台-批量导题")
    @PostMapping("/importQuestion")
    public AjaxResult importQuestion(@RequestBody List<VoteQuestion> questionList){

        return voteService.importQuestion(questionList);
    }

    @ApiOperation(value = "删除文件/图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "附件id", required = true),
            @ApiImplicitParam(name = "type", value = "删除的类型，vote投票封面图片，voteQuestion题目图片，" +
                    "voteOption选项图片", required = true),
            @ApiImplicitParam(name = "id", value = "图片对应的投票/题目/选项id", required = true)
    })
    @PostMapping("/deleteFile")
    public AjaxResult deleteFile(@RequestParam("fileId") Integer fileId,
                                 @RequestParam("type") String type,
                                 @RequestParam("id") Integer id){

        return voteService.deleteFile(fileId, type, id);
    }

    @ApiOperation(value = "后台-发布情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间，格式yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "endTime", value = "结束时间，格式yyyy-MM-dd HH:mm:ss")
    })
    @PostMapping("/getPublishList")
    public AjaxResult getPublishList(@RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime){

        return voteService.getPublishList(startTime, endTime);
    }

    @ApiOperation(value = "后台-导出发布情况")
    @GetMapping("exportPublishList")
    public AjaxResult exportPublishList(@RequestParam(value = "startTime", required = false) String startTime,
                                        @RequestParam(value = "endTime", required = false) String endTime,
                                        HttpServletResponse response){

        return voteService.exportPublishList(startTime, endTime, response);
    }

    @ApiOperation(value = "后台-统计结果")
    @PostMapping("/analyseVote")
    public AjaxResult analyseVote(@RequestParam("voteId") Integer voteId){

        return voteService.analyseVote(voteId);
    }

    @ApiOperation(value = "后台-导出统计结果")
    @GetMapping("/exportAnalyse")
    public AjaxResult exportAnalyse(@RequestParam("voteId") Integer voteId,
                                    HttpServletResponse response){

        return voteService.exportAnalyse(voteId, response);
    }

    @ApiOperation(value = "后台-导出答题数据")
    @GetMapping("/exportPaperData")
    public AjaxResult exportPaperData(@RequestParam("voteId") Integer voteId,
                                      HttpServletResponse response){

        return voteService.exportPaperData(voteId, response);
    }

    @ApiOperation(value = "后台-人员情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "orgId", value = "组织机构id"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getMember")
    public AjaxResult getMember(@RequestParam("voteId") Integer voteId,
                                @RequestParam(value = "orgId", required = false) Integer orgId,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return voteService.getMember(voteId, orgId, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-导出人员情况")
    @GetMapping("/exportMember")
    public AjaxResult exportMember(@RequestParam("voteId") Integer voteId,
                                   @RequestParam(value = "orgId", required = false) Integer orgId,
                                   HttpServletResponse response){

        return voteService.exportMember(voteId, orgId, response);
    }

    /***********************首页***************************/
    @ApiOperation(value = "首页-条件查询投票列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "title", value = "要搜索的标题内容"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getTopVoteList")
    public AjaxResult getTopVoteList(@RequestParam("userId") Integer userId,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return voteService.getTopVoteList(userId, title, pageNum, pageSize);
    }

    @ApiOperation(value = "首页-根据投票id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录人id", required = true)
    })
    @PostMapping("/getTopDetail")
    public AjaxResult getTopDetail(@RequestParam("voteId") Integer voteId,
                                   @RequestParam("userId") Integer userId){

        return voteService.getTopDetail(voteId, userId);
    }

    @ApiOperation(value = "首页-提交投票")
    @PostMapping("/submitVote")
    public AjaxResult submitVote(@RequestBody List<VotePaper> paperList){

        return voteService.submitVote(paperList);
    }

}


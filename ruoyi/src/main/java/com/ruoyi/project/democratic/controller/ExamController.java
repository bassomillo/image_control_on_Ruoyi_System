package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.ruoyi.project.democratic.entity.ExamPaper;
import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.entity.ExamSave;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import com.ruoyi.project.democratic.service.IExamService;
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
 * 考试表 前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/exam")
@Api(tags = "问卷调查-考试——cxr")
@CrossOrigin
public class ExamController {

    @Autowired
    private IExamService examService;

    @ApiOperation(value = "后台-新增/创建考试")
    @PostMapping("/insertExam")
    public AjaxResult insertExam(@RequestBody Exam exam){

        return examService.insertExam(exam);
    }

    @ApiOperation(value = "后台-条件查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "查询的标题内容"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getBackExamList")
    public AjaxResult getBackExamList(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return examService.getBackExamList(title, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-置顶/取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "sticky", value = "是否置顶，1置顶，0不置顶", required = true)
    })
    @PostMapping("/setTop")
    public AjaxResult setTop(@RequestParam("examId") Integer examId,
                             @RequestParam("sticky") Integer sticky){

        return examService.setTop(examId, sticky);
    }

    @ApiOperation(value = "后台-发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/publish")
    public AjaxResult publish(@RequestParam("examId") Integer examId,
                              @RequestParam("userId") Integer userId){

        return examService.publish(examId, userId);
    }

    @ApiOperation(value = "后台-取消发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/unpublish")
    public AjaxResult unpublish(@RequestParam("examId") Integer examId,
                                @RequestParam("userId") Integer userId){

        return examService.unpublish(examId, userId);
    }

    @ApiOperation(value = "后台-回收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/over")
    public AjaxResult over(@RequestParam("examId") Integer examId,
                           @RequestParam("userId") Integer userId){

        return examService.over(examId, userId);
    }

    @ApiOperation(value = "后台-删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/deleteBack")
    public AjaxResult deleteBack(@RequestParam("examId") Integer examId,
                                 @RequestParam("userId") Integer userId){

        return examService.deleteBack(examId, userId);
    }

    @ApiOperation(value = "后台-重命名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "title", value = "重命名的标题", required = true)
    })
    @PostMapping("rename")
    public AjaxResult rename(@RequestParam("examId") Integer examId,
                             @RequestParam("userId") Integer userId,
                             @RequestParam("title") String title){

        return examService.rename(examId, userId, title);
    }

    @ApiOperation(value = "后台-根据id查询考试详情")
    @PostMapping("/getDetailById")
    public AjaxResult getDetailById(@RequestParam("examId") Integer examId){

        return examService.getDetailById(examId);
    }

    @ApiOperation(value = "后台-修改基础信息")
    @PostMapping("/updateBaseData")
    public AjaxResult updateBaseData(@RequestBody ExamBaseVO examBase){

        return examService.updateBaseData(examBase);
    }

    @ApiOperation(value = "后台-编辑标题、封面")
    @PostMapping("/updateExam")
    public AjaxResult updateExam(@RequestBody Exam exam){

        return examService.updateExam(exam);
    }

    @ApiOperation(value = "后台-添加题目")
    @PostMapping("/insertQuestion")
    public AjaxResult insertQuestion(@RequestBody ExamQuestion question){

        return examService.insertQuestion(question);
    }

    @ApiOperation(value = "后台-编辑题目")
    @PostMapping("/updateQuestion")
    public AjaxResult updateQuestion(@RequestBody ExamQuestion question){

        return examService.updateQuestion(question);
    }

    @ApiOperation(value = "后台-删除题目/选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "题目id，删题目时传"),
            @ApiImplicitParam(name = "optionId", value = "选项id，删选项时传")
    })
    @PostMapping("/deleteQuestionOrOption")
    public AjaxResult deleteQuestionOrOption(@RequestParam(value = "questionId", required = false) Integer questionId,
                                             @RequestParam(value = "optionId", required = false) Integer optionId){

        return examService.deleteQuestionOrOption(questionId, optionId);
    }

    @ApiOperation(value = "后台-校验导入")
    @PostMapping("/checkImportQuestion")
    public AjaxResult checkImportQuestion(MultipartFile file,
                                          @RequestParam("examId") Integer examId){

        return examService.checkImportQuestion(file, examId);
    }

    @ApiOperation(value = "后台-批量导题")
    @PostMapping("/importQuestion")
    public AjaxResult importQuestion(@RequestBody List<ExamQuestion> questionList){

        return examService.importQuestion(questionList);
    }

    @ApiOperation(value = "后台-下载导题模板")
    @GetMapping("/downloadQuestionModel")
    public AjaxResult downloadQuestionModel(HttpServletResponse response){
        try {
            Workbook workbook = null;
            String path = "/static/excel/exam_import_example.xls";
            String name = "exam_import_example";

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

    @ApiOperation(value = "上传文件/图片")
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file,
                             @RequestParam("userId") Integer userId){

        return examService.upload(file, userId);
    }

    @ApiOperation(value = "删除文件/图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "附件id", required = true),
            @ApiImplicitParam(name = "type", value = "删除的类型，exam考试封面图片，examQuestion题目图片，" +
                    "examOption选项图片", required = true),
            @ApiImplicitParam(name = "id", value = "图片对应的考试/题目/选项id", required = true)
    })
    @PostMapping("/deleteFile")
    public AjaxResult deleteFile(@RequestParam("fileId") Integer fileId,
                                 @RequestParam("type") String type,
                                 @RequestParam("id") Integer id){

        return examService.deleteFile(fileId, type, id);
    }

    @ApiOperation(value = "后台-统计结果")
    @PostMapping("/analyseExam")
    public AjaxResult analyseExam(@RequestParam("examId") Integer examId){

        return examService.analyseExam(examId);
    }

    @ApiOperation(value = "后台-导出答题数据")
    @GetMapping("/exportPaperData")
    public AjaxResult exportPaperData(@RequestParam("examId") Integer examId,
                                      HttpServletResponse response){

        return examService.exportPaperData(examId, response);
    }

    @ApiOperation(value = "后台-发布情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间，格式yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "endTime", value = "结束时间，格式yyyy-MM-dd HH:mm:ss")
    })
    @PostMapping("/getPublishList")
    public AjaxResult getPublishList(@RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime){

        return examService.getPublishList(startTime, endTime);
    }

    @ApiOperation(value = "后台-导出发布情况")
    @GetMapping("exportPublishList")
    public AjaxResult exportPublishList(@RequestParam(value = "startTime", required = false) String startTime,
                                        @RequestParam(value = "endTime", required = false) String endTime,
                                        HttpServletResponse response){

        return examService.exportPublishList(startTime, endTime, response);
    }

    /***********************首页***************************/
    @ApiOperation(value = "首页-条件查询考试列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "title", value = "要搜索的标题内容"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getTopExamList")
    public AjaxResult getTopExamList(@RequestParam("userId") Integer userId,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return examService.getTopExamList(userId, title, pageNum, pageSize);
    }

    @ApiOperation(value = "首页-保存答题记录")
    @PostMapping("saveExamPaper")
    public AjaxResult saveExamPaper(@RequestBody ExamSave examSave){

        return examService.saveExamPaper(examSave);
    }

    @ApiOperation(value = "首页-根据考试id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录人id", required = true)
    })
    @PostMapping("/getTopDetail")
    public AjaxResult getTopDetail(@RequestParam("examId") Integer examId,
                                   @RequestParam("userId") Integer userId){

        return examService.getTopDetail(examId, userId);
    }

    @ApiOperation(value = "首页-提交试卷")
    @PostMapping("/submitExam")
    public AjaxResult submitExam(@RequestBody List<ExamPaper> paperList){

        return examService.submitExam(paperList);
    }
}


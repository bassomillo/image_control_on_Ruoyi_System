package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import com.ruoyi.project.democratic.service.IExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "首页/后台-根据id查询考试详情")
    @PostMapping("/getDetailById")
    public AjaxResult getDetailById(@RequestParam("examId") Integer examId){

        return examService.getDetailById(examId);
    }

    @ApiOperation(value = "后台-修改基础信息")
    @PostMapping("/updateBaseData")
    public AjaxResult updateBaseData(@RequestBody ExamBaseVO examBase){

        return examService.updateBaseData(examBase);
    }

}


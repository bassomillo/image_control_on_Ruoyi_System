package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.service.ChairmanLetterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主席信箱 前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/chairmanLetter")
@Api(tags = "主席信箱——cxr")
@Slf4j
@CrossOrigin
public class ChairmanLetterController {

    @Autowired
    private ChairmanLetterService chairmanLetterService;

    @ApiOperation(value = "首页-获取写信对象id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者id")
    })
    @PostMapping("/getLetterMan")
    public AjaxResult getLetterMan(@RequestParam("userId") Integer userId){

        return chairmanLetterService.getLetterMan(userId);
    }

    @ApiOperation(value = "首页-发送信件")
    @PostMapping("/insertChairmanLetter")
    public AjaxResult insertChairmanLetter(@RequestBody ChairmanLetterBox chairmanLetter){

        return chairmanLetterService.insertChairmanLetter(chairmanLetter);
    }

    @ApiOperation(value = "首页-查询回复记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者编号", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页号，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getTopChairmanList")
    public AjaxResult getTopChairmanList(@RequestParam("userId") Integer userId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return chairmanLetterService.getTopChairmanList(userId, pageNum, pageSize);
    }

    @ApiOperation(value = "首页/后台-根据id查找单个记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", required = true)
    })
    @PostMapping("/getChairmanDetailById")
    public AjaxResult getChairmanDetailById(@RequestParam("id") Integer id){

        return chairmanLetterService.getChairmanDetailById(id);
    }

    @ApiOperation(value = "首页-评价回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluate", value = "评价，complete满意，fail不满意", required = true),
            @ApiImplicitParam(name = "evaluateContent", value = "评价内容", required = true),
            @ApiImplicitParam(name = "requireId", value = "回复id", required = true)
    })
    @PostMapping("/evaluate")
    public AjaxResult evaluate(@RequestParam("evaluate") String evaluate,
                               @RequestParam("evaluateContent") String evaluateContent,
                               @RequestParam("requireId") Integer requireId){

        return chairmanLetterService.evaluate(evaluate, evaluateContent, requireId);
    }

    @ApiOperation(value = "后台-条件查询信箱列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "搜索内容"),
            @ApiImplicitParam(name = "year", value = "年份，例如2021"),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true)
    })
    @PostMapping("/getBackLetterList")
    public AjaxResult getBackLetterList(@RequestParam(value = "content", required = false) String content,
                                        @RequestParam(value = "year", required = false) Integer year,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return chairmanLetterService.getBackLetterList(content, year, userId, pageNum, pageSize);
    }

}


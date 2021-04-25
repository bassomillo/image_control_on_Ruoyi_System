package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import com.ruoyi.project.democratic.service.IChairmanLetterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private IChairmanLetterService IChairmanLetterService;

    @ApiOperation(value = "首页-获取写信对象id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者id")
    })
    @PostMapping("/getLetterMan")
    public AjaxResult getLetterMan(@RequestParam("userId") Integer userId){

        return IChairmanLetterService.getLetterMan(userId);
    }

    @ApiOperation(value = "首页-发送信件")
    @PostMapping("/insertChairmanLetter")
    public AjaxResult insertChairmanLetter(@RequestBody ChairmanLetterBox chairmanLetter){

        return IChairmanLetterService.insertChairmanLetter(chairmanLetter);
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

        return IChairmanLetterService.getTopChairmanList(userId, pageNum, pageSize);
    }

    @ApiOperation(value = "首页/后台-根据id查找单个记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", required = true)
    })
    @PostMapping("/getChairmanDetailById")
    public AjaxResult getChairmanDetailById(@RequestParam("id") Integer id){

        return IChairmanLetterService.getChairmanDetailById(id);
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

        return IChairmanLetterService.evaluate(evaluate, evaluateContent, requireId);
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

        return IChairmanLetterService.getBackLetterList(content, year, userId, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-批量删除信件")
    @PostMapping("/deleteLetter")
    public AjaxResult deleteLetter(@RequestBody List<Integer> idList){

        return IChairmanLetterService.deleteLetter(idList);
    }

    @ApiOperation(value = "后台-批量回复")
    @PostMapping("/replyLetter")
    public AjaxResult replyLetter(@RequestBody ReplyLetterDO replyLetter){

        return IChairmanLetterService.replyLetter(replyLetter);
    }

    @ApiOperation(value = "首页-上传文件")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(MultipartFile file,
                                 @RequestParam("userId") Integer userId){

        return IChairmanLetterService.uploadFile(file, userId);
    }

    @ApiOperation(value = "首页-删除文件")
    @PostMapping("/deleteFile")
    public AjaxResult deleteFile(@RequestParam("id") Integer id){

        return IChairmanLetterService.deleteFile(id);
    }

    @ApiOperation(value = "首页/后台-下载文件")
    @PostMapping("/downloadFile")
    public AjaxResult downloadFile(@RequestParam("id") Integer id,
                                   HttpServletResponse response){
        return IChairmanLetterService.downloadFile(id, response);
    }

    @ApiOperation(value = "后台-批量导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "搜索内容"),
            @ApiImplicitParam(name = "year", value = "年份"),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/export")
    public AjaxResult export(@RequestParam(value = "content", required = false) String content,
                             @RequestParam(value = "year", required = false) Integer year,
                             @RequestParam("userId") Integer userId,
                             HttpServletResponse response,
                             HttpServletRequest request){
        return IChairmanLetterService.export(content, year, userId, response, request);
    }

}


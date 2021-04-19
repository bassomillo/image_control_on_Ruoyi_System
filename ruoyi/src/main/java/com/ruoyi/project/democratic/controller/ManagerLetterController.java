package com.ruoyi.project.democratic.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;
import com.ruoyi.project.democratic.service.IManagerLetterService;
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
 * 总经理信箱 前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/managerLetter")
@Api(tags = "总经理信箱——cxr")
@Slf4j
@CrossOrigin
public class ManagerLetterController {

    @Autowired
    private IManagerLetterService IManagerLetterService;

    @ApiOperation(value = "首页-获取写信对象id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者id")
    })
    @PostMapping("/getLetterMan")
    public AjaxResult getLetterMan(@RequestParam("userId") Integer userId){

        return IManagerLetterService.getLetterMan(userId);
    }

    @ApiOperation(value = "首页-发送信件")
    @PostMapping("/insertManagerLetter")
    public AjaxResult insertManagerLetter(@RequestBody ManagerLetterBox letterBox){

        return IManagerLetterService.insertManagerLetter(letterBox);
    }

    @ApiOperation(value = "首页-查询回复记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者编号", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页号，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getTopManagerList")
    public AjaxResult getTopManagerList(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return IManagerLetterService.getTopManagerList(userId, pageNum, pageSize);
    }

    @ApiOperation(value = "首页/后台-根据id查找单个记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", required = true)
    })
    @PostMapping("/getManagerDetailById")
    public AjaxResult getManagerDetailById(@RequestParam("id") Integer id){

        return IManagerLetterService.getManagerDetailById(id);
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

        return IManagerLetterService.evaluate(evaluate, evaluateContent, requireId);
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

        return IManagerLetterService.getBackLetterList(content, year, userId, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-批量删除信件")
    @PostMapping("/deleteLetter")
    public AjaxResult deleteLetter(@RequestBody List<Integer> idList){

        return IManagerLetterService.deleteLetter(idList);
    }

    @ApiOperation(value = "后台-批量回复")
    @PostMapping("/replyLetter")
    public AjaxResult replyLetter(@RequestBody ReplyLetterDO replyLetter){

        return IManagerLetterService.replyLetter(replyLetter);
    }

    @ApiOperation(value = "首页-上传文件")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(MultipartFile file,
                                 @RequestParam("userId") Integer userId){

        return IManagerLetterService.uploadFile(file, userId);
    }

    @ApiOperation(value = "首页-删除文件")
    @PostMapping("/deleteFile")
    public AjaxResult deleteFile(@RequestParam("id") Integer id){

        return IManagerLetterService.deleteFile(id);
    }

    @ApiOperation(value = "首页/后台-下载文件")
    @PostMapping("/downloadFile")
    public AjaxResult downloadFile(@RequestParam("id") Integer id,
                                   HttpServletResponse response){
        return IManagerLetterService.downloadFile(id, response);
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
        return IManagerLetterService.export(content, year, userId, response, request);
    }

}


package com.ruoyi.project.democratic.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.SuggestBox;
import com.ruoyi.project.democratic.service.SuggestService;
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
 *  前端控制器
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */
@RestController
@RequestMapping("/suggest")
@Api(tags = "建言献策——cxr")
@Slf4j
@CrossOrigin
public class SuggestController {

    @Autowired
    private SuggestService suggestService;

    @ApiOperation(value = "首页-提供建议", httpMethod = "POST")
    @PostMapping("/insertSuggest")
    public AjaxResult insertSuggest(@RequestBody SuggestBox suggest){

        return suggestService.insertSuggest(suggest);
    }

    @ApiOperation(value = "首页-查找回复记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录者编号", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页号，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getTopSuggestList")
    public AjaxResult getTopSuggestList(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return suggestService.getTopSuggestList(userId, pageNum, pageSize);
    }

    @ApiOperation(value = "首页/后台-根据id查找单个记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", required = true)
    })
    @PostMapping("/getSuggestDetailById")
    public AjaxResult getSuggestDetailById(@RequestParam("id") Integer id){

        return suggestService.getSuggestDetailById(id);
    }

    @ApiOperation(value = "后台-条件查询建言列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "查询内容"),
            @ApiImplicitParam(name = "year", value = "年份"),
            @ApiImplicitParam(name = "pageNum", value = "页号，默认1"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认10")
    })
    @PostMapping("/getBackSuggestList")
    public AjaxResult getBackSuggestList(@RequestParam(value = "content", required = false) String content,
                                         @RequestParam(value = "year", required = false) Integer year,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        return suggestService.getBackSuggestList(content, year, pageNum, pageSize);
    }

    @ApiOperation(value = "后台-批量删除建言")
    @PostMapping("/deleteSuggest")
    public AjaxResult deleteSuggest(@RequestBody List<Integer> idList){

        return suggestService.deleteSuggest(idList);
    }

    @ApiOperation(value = "后台-回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "回复内容", required = true),
            @ApiImplicitParam(name = "suggestId", value = "建言id", required = true),
            @ApiImplicitParam(name = "userId", value = "当前登录者id", required = true)
    })
    @PostMapping("/replySuggest")
    public AjaxResult replySuggest(@RequestParam("content") String content,
                                   @RequestParam("suggestId") Integer suggestId,
                                   @RequestParam("userId") Integer userId){

        return suggestService.replySuggest(content, suggestId, userId);
    }

    @ApiOperation(value = "首页-上传文件")
    @PostMapping("/uploadSuggestFile")
    public AjaxResult uploadSuggestFile(MultipartFile file,
                                        @RequestParam("userId") Integer userId){

        return suggestService.uploadSuggestFile(file, userId);
    }

    @ApiOperation(value = "首页-删除文件")
    @PostMapping("/deleteFile")
    public AjaxResult deleteFile(@RequestParam("id") Integer id){

        return suggestService.deleteFile(id);
    }

    @ApiOperation(value = "首页/后台-下载文件")
    @PostMapping("/downloadFile")
    public AjaxResult downloadFile(@RequestParam("id") Integer id,
                             HttpServletResponse response){
        return suggestService.downloadFile(id, response);
    }

    @ApiOperation(value = "后台-批量导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "搜索内容"),
            @ApiImplicitParam(name = "year", value = "年份")
    })
    @PostMapping("/export")
    public AjaxResult export(@RequestParam(value = "content", required = false) String content,
                       @RequestParam(value = "year", required = false) Integer year,
                       HttpServletResponse response,
                       HttpServletRequest request){
        return suggestService.export(content, year, response, request);
    }
}

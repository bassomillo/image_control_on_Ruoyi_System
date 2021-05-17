package com.ruoyi.project.unionhelp.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.ManageClass;
import com.ruoyi.project.unionhelp.service.IManageClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 托管班 前端控制器
 * </p>
 *
 * @author zm
 * @since 2021-04-25
 */
@Api(tags = "工会管理")
@RestController
@RequestMapping(value = "/union")
public class ManageClassController {
    @Autowired
    IManageClassService manageClassService;

    @ApiOperation("托管班-新增/编辑托管班-上传文件")
    @PostMapping("/uploadfile")
    AjaxResult uploadFile(@RequestParam("userId")Integer userId,
                          @RequestParam("multipartFile")MultipartFile multipartFile,
                          @RequestParam("targetId")Integer targetId){
        AjaxResult result = manageClassService.UploadFile(userId, multipartFile, targetId);
        return result;
    }

    @ApiOperation("托管班-新增/编辑托管班-删除文件")
    @PostMapping("deletefile")
    AjaxResult deleteFile(@RequestParam("id")Integer id){
        AjaxResult result = manageClassService.DeleteFile(id);
        return result;
    }

    @ApiOperation(value = "托管班-查看-下载文件")
    @GetMapping("downloadfile")
    public AjaxResult downloadFile(@RequestParam("id") Integer id,
                                   HttpServletResponse response){
        return manageClassService.downloadFile(id, response);
    }

    @ApiOperation(value = "托管班-导出")
    @PostMapping("/exportmanageclass")
    AjaxResult exportManageClass(HttpServletResponse response,
                                 HttpServletRequest request){
        AjaxResult result = manageClassService.export(response, request);
        return result;
    }

    /**
     * 新增托管班
     * @param manageClass
     * @return
     */
    @ApiOperation(value = "托管班-新增托管班")
    @PostMapping("/insertmanageclass")
    AjaxResult insertManageClass(@RequestBody ManageClass manageClass){
        AjaxResult result = manageClassService.InsertManageClass(manageClass);
        return result;
    }

    /**
     * 根据id得到托管班
     * @param id
     * @return
     */
    @ApiOperation(value = "托管班-根据id得到托管班")
    @GetMapping("/searchmanageclassbyid")
    AjaxResult searchManageClassById(@RequestParam("id") Integer id){
        AjaxResult result = manageClassService.SearchManageClassById(id);
        return result;
    }

    /**
     * 编辑托管班
     * @param manageClass
     * @return
     */
    @ApiOperation(value = "托管班-编辑托管班")
    @PostMapping("/updatemanageclass")
    AjaxResult updateManageClass(@RequestBody ManageClass manageClass){
        AjaxResult result = manageClassService.UpdateManageClass(manageClass);
        return result;
    }

    /**
     * 展示所有托管班
     * @param pagesize
     * @param page
     * @return
     */
    @ApiOperation(value = "托管班-展示所有托管班")
    @GetMapping("/getmanageclass")
    AjaxResult getManageClass(@RequestParam("pagesize") Integer pagesize,
                              @RequestParam("page") Integer page){
        AjaxResult result = manageClassService.GetManageClasses(pagesize, page);
        return result;
    }

    @ApiOperation(value = "条件查询")
    @GetMapping("/selectmanageclass")
    AjaxResult selectManageClass(@RequestParam(value = "company", required = false)String company,
                                 @RequestParam(value = "trueName", required = false)String trueName,
                                 @RequestParam(value = "mobile", required = false)String mobile,
                                 @RequestParam("pagesize") Integer pagesize,
                                 @RequestParam("page") Integer page){
        AjaxResult result = manageClassService.SelectManageClasses(company, trueName, mobile, pagesize, page);
        return result;
    }
}


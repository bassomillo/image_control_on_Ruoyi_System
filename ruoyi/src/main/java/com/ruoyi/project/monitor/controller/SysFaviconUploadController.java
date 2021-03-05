package com.ruoyi.project.monitor.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther:chent69
 * @date: 2020/1/21-10 :45
 */
@RestController
@Api(tags = "文件服务器相关接口")
@RequestMapping("file")
public class SysFaviconUploadController {
    @Resource
    FastdfsClientUtil fastdfsClientUtil;


    @PostMapping("/admin/setting/site/uploadFavicon")
    @ApiOperation("上传图片到服务器")
    @ApiImplicitParam(name = "file",value = "图片文件",required = true,dataType = "MultipartFile")
    public AjaxResult uploadImage(@RequestParam("file") MultipartFile file){
        String imageStorePath = null;
        try{
            imageStorePath = fastdfsClientUtil.uploadImage(file);
        }catch (IOException e){
            e.printStackTrace();
            return AjaxResult.error("上传失败");
        }
//        Map<String, String> result = new HashMap<String, String>();
        return AjaxResult.success("上传成功", imageStorePath);
    }


}


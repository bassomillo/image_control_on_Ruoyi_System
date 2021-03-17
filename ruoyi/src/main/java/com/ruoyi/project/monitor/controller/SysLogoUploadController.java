package com.ruoyi.project.monitor.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.domain.SysSiteSetting;
import com.ruoyi.project.monitor.mapper.SysSiteSettingMapper;
import com.ruoyi.project.monitor.service.SysSiteSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysLogoUploadController {
    @Autowired
    private SysSiteSettingService sysSiteSettingService;

    @Resource
    FastdfsClientUtil fastdfsClientUtil;


    @PostMapping("/admin/setting/site/uploadLogo")
    @ApiOperation("系统管理-站点设置-基础信息-上传Logo图片到服务器")
    @ApiImplicitParam(name = "file",value = "图片文件",required = true,dataType = "MultipartFile")
    public AjaxResult uploadImage(@RequestParam("file") MultipartFile file){
        String imageStorePath = null;
        String preImagePath = "http://182.254.171.122/";
        try{
            imageStorePath = fastdfsClientUtil.uploadImage(file);
        }catch (IOException e){
            e.printStackTrace();
            return AjaxResult.error("上传失败");
        }
//        Map<String, String> result = new HashMap<String, String>();
        String totalImagePath = preImagePath + imageStorePath;
        return AjaxResult.success("上传成功", totalImagePath);
    }
    @PostMapping("/admin/setting/site/deletePicture")
    @ApiOperation("系统管理-站点设置-基础信息-从服务器删除图片")
    //@ApiImplicitParam(name = "file",value = "图片文件",required = true,dataType = "MultipartFile")
    public AjaxResult deletLogo(String fileUrl) {

        String existLogo = sysSiteSettingService.SysSiteGettinglogo();
        String existFavicon = sysSiteSettingService.SysSiteGettingFavicon();
        try {
            if(!(fileUrl.equals(existFavicon)||fileUrl.equals(existLogo))) {
                fastdfsClientUtil.deleteFile(fileUrl);
            }
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("上传失败");
        }
        return AjaxResult.success("删除成功");
    }

}

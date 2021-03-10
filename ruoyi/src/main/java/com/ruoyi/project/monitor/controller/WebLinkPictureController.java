package com.ruoyi.project.monitor.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.monitor.service.SysSiteSettingService;
import com.ruoyi.project.monitor.service.WebLinkSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @auther:chent69
 * @date: 2020/1/21-10 :45
 */
@RestController
@Api(tags = "文件服务器相关接口")
@RequestMapping("file")
public class WebLinkPictureController {
    @Autowired
    private WebLinkSettingService webLinkSettingService;

    @Resource
    FastdfsClientUtil fastdfsClientUtil;


    @PostMapping("/admin/setting/webLink/uploadPicture")
    @ApiOperation("网站管理-友情链接管理-上传图片到服务器")
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

    @PostMapping("/admin/setting/webLink/deletePicture")
    @ApiOperation("网站管理-友情链接管理-从服务器删除图片")
    //@ApiImplicitParam(name = "file",value = "图片文件",required = true,dataType = "MultipartFile")
    public AjaxResult deletImage(String fileUrl) {

        String existLogo = webLinkSettingService.WebLinkGettingLogo();
        String existWeixinQrcode = webLinkSettingService.WebLinkGettingWeixinQrcode();
        String existIosQrcode = webLinkSettingService.WebLinkGettingIosQrcode();
        String existAndroidQrcode= webLinkSettingService.WebLinkGettingAndroidQrcode();
        try {
            if(!(fileUrl.equals(existWeixinQrcode)||fileUrl.equals(existLogo)||fileUrl.equals(existIosQrcode)||fileUrl.equals(existAndroidQrcode))) {
                fastdfsClientUtil.deleteFile(fileUrl);
            }
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("上传失败");
        }
        return AjaxResult.success("删除成功");
    }

}

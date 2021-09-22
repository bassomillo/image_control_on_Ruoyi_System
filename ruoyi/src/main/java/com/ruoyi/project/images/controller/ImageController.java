package com.ruoyi.project.images.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.images.domain.Images;
import com.ruoyi.project.images.service.ImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "image control system")
@RestController
@RequestMapping("/admin/setting/")
public class ImageController {
    @Autowired
    private ImagesService imagesService;
    @Resource
    FastdfsClientUtil fastdfsClientUtil;


    @PostMapping("/uploadImageFromServer")
    @ApiOperation("Upload the Photo to server")
    @ApiImplicitParam(name = "file",value = "images",required = true,dataType = "MultipartFile")
    public AjaxResult uploadImage(@RequestParam("file") MultipartFile file){
        String imageStorePath = null;
        String preImagePath = "http://182.254.171.122/";
        try{
            imageStorePath = fastdfsClientUtil.uploadImage(file);
        }catch (IOException e){
            e.printStackTrace();
            return AjaxResult.error("submit failed");
        }

        String totalImagePath = preImagePath + imageStorePath;
        return AjaxResult.success("submit successful", totalImagePath);
    }
    @PostMapping("/deleteImageFromServer")
    @ApiOperation("Delete the Photo from server")
    public AjaxResult deletLogo(String fileUrl) {

        try {

                fastdfsClientUtil.deleteFile(fileUrl);

        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("submit failed");
        }
        return AjaxResult.success("submit successful");
    }


    /**
     * insert a picture
     */

    @ApiOperation(value = "insert a picture")
    @PostMapping("/insertImage")
    public AjaxResult insertSiteSetting(@RequestBody Images images)
    {

        AjaxResult result = imagesService.ImageSetting(images);
        return result;

    }

    /**
     * Search the pictures and return the results
     */
    @ApiOperation(value = "Search the pictures and return the results")
    @PostMapping("/searchImages")
    public AjaxResult searchSiteSetting(@RequestParam(required = false) String imageTag, @RequestParam(required = false)String imageName, @RequestParam(required = false)String remark, @RequestParam(required = true)String imagePrivateUser){

        AjaxResult result = imagesService.ImageSearch(imageTag,imageName, remark,imagePrivateUser);
        return result;



    }

    /**
     * Update information of the image
     */
    @ApiOperation(value = "Update information of the image")
    @PostMapping("/updateImage")
    public AjaxResult updateImage(@RequestBody Images images){
        AjaxResult result = imagesService.ImageUpdate(images);
        return result;
    }


    /**
     * delete multiple of the images
     */
    @ApiOperation(value = "delete multiple of the images")
    @PostMapping("/deleteImages")
    public AjaxResult deleteImages(@RequestParam List<Integer> listId, @RequestParam String imagePrivateUser){
        AjaxResult result = imagesService.ImageDelete(listId, imagePrivateUser);
        return result;
    }


}

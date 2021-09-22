package com.ruoyi.project.images.service.Impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.images.domain.Images;
import com.ruoyi.project.images.mapper.ImagesMapper;
import com.ruoyi.project.images.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public AjaxResult ImageSetting(Images images) {
        try {

            Date time = new Date();
            images.setCreatedTime(time);
            images.setUpdatedTime(time);
            imagesMapper.ImageSetting(images);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("submit successfully");
    }

    @Override
    public AjaxResult ImageDelete(List<Integer> listId, String imagePrivateUser) {
        try {
            for (Integer id : listId) {
                imagesMapper.ImageDelete(id, imagePrivateUser);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("submit successfully");
    }

    @Override
    public AjaxResult ImageUpdate(Images images) {
        try {
            Date time = new Date();
            images.setUpdatedTime(time);
            imagesMapper.ImageUpdate(images);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("submit successfully");
    }

    @Override
    public AjaxResult ImageSearch(String imageTag, String imageName, String remark, String imagePrivateUser) {
        String imageTag1 = new String();

        if(imageTag != null) {
            imageTag1 = imageTag.replace("%", "/%");
        }
        List<Images> imagesList = imagesMapper.ImageSearch(imageTag, imageName, remark, imagePrivateUser);
        Integer count = imagesMapper.ImageSearchCounting(imageTag, imageName, remark, imagePrivateUser);
        Map<String, Object> map = new HashMap<>();
        map.put("list", imagesList);
        map.put("count", count);

        return AjaxResult.success("submit successfully", map);
    }

}
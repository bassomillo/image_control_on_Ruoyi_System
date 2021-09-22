package com.ruoyi.project.images.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.images.domain.Images;

import java.util.List;

public interface ImagesService {
    public AjaxResult ImageSetting(Images images);
    public AjaxResult ImageDelete(List<Integer> listId, String imagePrivateUser);
    public AjaxResult ImageSearch(String imageTag, String imageName, String remark, String imagePrivateUser);
    public AjaxResult ImageUpdate(Images images);
}

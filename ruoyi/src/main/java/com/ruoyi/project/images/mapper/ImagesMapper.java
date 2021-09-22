package com.ruoyi.project.images.mapper;

import com.ruoyi.project.images.domain.Images;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImagesMapper {

    public boolean ImageSetting(Images images);

    public  boolean ImageDelete(@Param("id") Integer id, @Param("imagePrivateUser") String imagePrivateUser);

    public List<Images> ImageSearch(@Param("imageTag") String imageTag, @Param("imageName") String imageName, @Param("remark") String remark, @Param("imagePrivateUser") String imagePrivateUser);

    public boolean ImageUpdate(Images images);

    public Integer ImageSearchCounting(@Param("imageTag") String imageTag, @Param("imageName") String imageName, @Param("remark") String remark, @Param("imagePrivateUser") String imagePrivateUser);
}

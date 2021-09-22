package com.ruoyi.project.images.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Images {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "File path of the image")
    private String imagePath;

    @ApiModelProperty(value = "Name of the image")
    private String imageName;

    @ApiModelProperty(value = "Tag of the image")
    private String imageTag;

    @ApiModelProperty(value = "Whether the image is private or public,0 for public, 1 for private")
    private Integer imageAttribute;

    @ApiModelProperty(value = "Private user of the image")
    private String imagePrivateUser;

    @ApiModelProperty(value = "remark of the image")
    private String remark;

    @ApiModelProperty(value = "remark of the image")
    private Date createdTime;

    @ApiModelProperty(value = "remark of the image")
    private Date updatedTime;
}

package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.Tag;
import com.ruoyi.project.chairmanOnline.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标签(Tag)表控制层
 *
 * @author weide
 * @since 2021-04-25 15:57:07
 */
@Api(tags = "web端-民主管理-总经理在线-hwd")
@RestController
@RequestMapping("tag")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;


    /**
     * 获取所有聊天话题标签
     *
     * @param
     * @return 单条数据
     */
   @PostMapping("selectAllTag")
    @ApiOperation("获取所有聊天话题标签")
    public AjaxResult selectAllTag() {
        return AjaxResult.success(this.tagService.queryAllByLimit(1,50));
    }

}

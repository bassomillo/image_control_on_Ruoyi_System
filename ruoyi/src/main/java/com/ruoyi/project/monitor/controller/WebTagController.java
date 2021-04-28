package com.ruoyi.project.monitor.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebTag;
import com.ruoyi.project.monitor.service.WebTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/tag")
public class WebTagController {
    @Autowired
    WebTagService webTagService;

    /**
     * 新建标签
     * @param name
     * @return
     */
    @ApiOperation("标签管理-新建标签")
    @PostMapping("/insertwebtag")
    public AjaxResult Insert(@RequestParam("name") String name){
        AjaxResult result = webTagService.WebTagInsert(name);
        return result;
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @ApiOperation("标签管理-删除标签")
    @PostMapping("/deletewebtag")
    public AjaxResult delete(@RequestParam("id") Integer id){
        AjaxResult result = webTagService.WebTagDelete(id);
        return result;
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @ApiOperation("标签管理-回显")
    @GetMapping("/serchbyid")
    public AjaxResult searchbyid(@RequestParam("id") Integer id){
        AjaxResult result = webTagService.SearchWebTagById(id);
        return result;
    }

    /**
     * 编辑
     * @param webTag
     * @return
     */
    @ApiOperation("标签管理-编辑标签")
    @PostMapping("updatewebtag")
    public AjaxResult update(@RequestBody WebTag webTag){
        AjaxResult result = webTagService.WebTagUpdate(webTag);
        return result;
    }

    /**
     * 展示标签
     * @param pagesize
     * @param page
     * @return
     */
    @ApiOperation("标签管理-展示")
    @GetMapping("/getwebtag")
    public AjaxResult gettags(@RequestParam("pagesize") Integer pagesize,
                              @RequestParam("page") Integer page){
        AjaxResult result = webTagService.WebTagGet(pagesize, page);
        return result;
    }

}


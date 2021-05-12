package com.ruoyi.project.monitor.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebTagGroup;
import com.ruoyi.project.monitor.service.WebTagGroupService;
import com.ruoyi.project.monitor.service.WebTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签组表 前端控制器
 * </p>
 *
 * @author zm
 * @since 2021-04-21
 */
@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/taggroup")
public class WebTagGroupController {
    @Autowired
    WebTagGroupService webTagGroupService;
    @Autowired
    WebTagService webTagService;

    /**
     * 创建/编辑标签组可选标签名称
     * @param
     * @return
     */
    @ApiOperation("标签组管理-创建/编辑标签组可选标签名称")
    @GetMapping("/gettagname")
    public AjaxResult gettagnames(@RequestParam("pagesize") Integer pagesize,
                                  @RequestParam("page") Integer page,
                                  @RequestParam(value = "name", required = false)String name){
        AjaxResult result = webTagService.TagNamesGet(name, pagesize, page);
        return result;
    }

    /**
     * 创建标签组
     * @param webTagGroup
     * @return
     */
    @ApiOperation("标签组管理-创建标签组")
    @PostMapping("/inserttaggroup")
    public AjaxResult inserttaggroup(@RequestBody WebTagGroup webTagGroup){
        AjaxResult result = webTagGroupService.WebTagGroupInsert(webTagGroup);
        return result;
    }

    /**
     * 删除标签组
     * @param id
     * @return
     */
    @ApiOperation("标签组管理-删除标签组")
    @PostMapping("/deletewebtaggroup")
    public AjaxResult deletetaggroup(@RequestParam("id") Integer id){
        AjaxResult result = webTagGroupService.WebTagGroupDelete(id);
        return result;
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @ApiOperation("标签组管理-回显")
    @GetMapping("/serchtaggroupbyid")
    public AjaxResult searchbyid(@RequestParam("id") Integer id){

        AjaxResult result = webTagGroupService.WebTagGroupSearchById(id);
        return result;
    }

    /**
     * 编辑
     * @param webTagGroup
     * @return
     */
    @ApiOperation("标签组管理-编辑标签组")
    @PostMapping("updatewebtaggroup")
    public AjaxResult update(@RequestBody WebTagGroup webTagGroup){
        AjaxResult result = webTagGroupService.WebTagGroupUpdate(webTagGroup);
        return result;
    }

    /**
     * 展示标签组
     * @param pagesize
     * @param page
     * @return
     */
    @ApiOperation("标签组管理-展示标签组")
    @GetMapping("/getwebtaggroups")
    public AjaxResult gettaggroups(@RequestParam("pagesize") Integer pagesize,
                                   @RequestParam("page") Integer page){
        AjaxResult result = webTagGroupService.WebTagGroupGet(pagesize, page);
        return result;
    }

}


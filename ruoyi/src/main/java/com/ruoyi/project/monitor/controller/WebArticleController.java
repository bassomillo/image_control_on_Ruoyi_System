package com.ruoyi.project.monitor.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.WebArticleCategory;
import com.ruoyi.project.monitor.service.WebArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 *
 * @author zm
 * @since 2021-04-23
 */
@Api(tags = "网页")
@RestController
@RequestMapping("/admin/setting/Article")
public class WebArticleController {
    @Autowired
    WebArticleCategoryService webArticleCategoryService;

    /**
     * 展示可选一级栏目
     * @param pagesize
     * @param page
     * @return
     */
    @ApiOperation("网站管理-栏目管理-展示所有一级栏目")
    @GetMapping("/gettopArticle")
    public AjaxResult getTopArticle(@RequestParam("pagesize") Integer pagesize,
                                    @RequestParam("page") Integer page,
                                    @RequestParam(value = "name", required = false)String name){
        AjaxResult result = webArticleCategoryService.TopArticleCategoryGet(pagesize, page, name);
        return result;
    }

    /**
     * 创建栏目
     * @param webArticleCategory
     * @return
     */
    @ApiOperation("网站管理-栏目管理-创建栏目")
    @PostMapping("/insertArticle")
    public AjaxResult insertArticle(@RequestBody WebArticleCategory webArticleCategory){
        AjaxResult result = webArticleCategoryService.WebArticleCategoryInsert(webArticleCategory);
        return result;
    }

    /**
     * 删除栏目
     * @param id
     * @return
     */
    @ApiOperation("网站管理-栏目管理-删除栏目")
    @PostMapping("/deletearticle")
    public AjaxResult deleteArticle(@RequestParam("id") Integer id,
                                    @RequestParam("parentId") Integer parentId){
        AjaxResult result = webArticleCategoryService.WebArticleCategoryDelete(id, parentId);
        return result;
    }

    /**
     * 展示所有栏目
     * @param pagesize
     * @param page
     * @return
     */
    @ApiOperation("网站管理-栏目管理-展示所有栏目")
    @GetMapping("/getallarticle")
    public AjaxResult getAllArticles(@RequestParam("pagesize") Integer pagesize,
                                     @RequestParam("page") Integer page){
        AjaxResult result = webArticleCategoryService.WebArticleCategoryGet(pagesize, page);
        return result;
    }

    /**
     * 根据id查询栏目
     * @param id
     * @return
     */
    @ApiOperation("网站管理-栏目管理-根据id查询栏目(回显)")
    @GetMapping("/getArticleById")
    public AjaxResult getArticleById(@RequestParam("id") Integer id){
        AjaxResult result = webArticleCategoryService.SearchArticleCategoryById(id);
        return result;
    }

    /**
     * 更新栏目
     * @param webArticleCategory
     * @return
     */
    @ApiOperation("网站管理-栏目管理-更新栏目")
    @PostMapping("/updateArticle")
    public AjaxResult updateArticle(@RequestBody WebArticleCategory webArticleCategory){
        AjaxResult result = webArticleCategoryService.WebArticleCategoryUpdate(webArticleCategory);
        return result;
    }

}


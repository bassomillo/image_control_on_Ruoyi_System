package com.ruoyi.project.org.controller;


import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.pojo.OrgRoleSearchPojo;
import com.ruoyi.project.org.pojo.OrgUserSearchPojo;
import com.ruoyi.project.org.service.IOrgCommissionerService;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/org")
@Api(tags = "工会管理 - 机构管理   zjy")
@Slf4j
public class OrgController {

    @Autowired
    private IOrgService orgService;

    @Autowired
    private IOrgCommissionerService orgCommissionerService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取组织机构树", httpMethod = "GET")
    @GetMapping("/searchOrgTree")
    public AjaxResult searchOrgTree() {
        return AjaxResult.success(orgService.searchOrgTree());
    }

    @ApiOperation(value = "新增子机构", httpMethod = "POST")
    @PostMapping("/createOrg")
    public AjaxResult createOrg(@RequestBody Org org) {
        if(orgService.isRepeat(org))
            return AjaxResult.error("当前机构下存在同名机构，机构名<" + org.getName() + ">不可用");
        orgService.createOrg(org);
        return AjaxResult.success();
    }

    @ApiOperation(value = "删除机构", httpMethod = "POST")
    @ApiImplicitParam(name = "orgId", value = "机构id", paramType = "query", dataType = "Integer")
    @PostMapping("/delOrgById")
    public AjaxResult delOrgById(@RequestParam("orgId") Integer orgId) {
        return orgService.delOrgById(orgId);
    }

    @ApiOperation(value = "编辑机构", httpMethod = "POST")
    @PostMapping("/updateOrg")
    public AjaxResult updateOrg(@RequestBody Org org) {
        return orgService.updateOrg(org);
    }

    @ApiOperation(value = "机构迁移", httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orgId", value = "被迁移的机构id", paramType = "query", dataType = "Integer"),
        @ApiImplicitParam(name = "parentId", value = "迁移目标父机构id", paramType = "query", dataType = "Integer")
    })
    @PostMapping("/removeOrg")
    public AjaxResult removeOrg(@RequestParam("orgId") Integer orgId, @RequestParam("parentId") Integer parentId) {
        return orgService.removeOrg(orgId, parentId);
    }

    @ApiOperation(value = "获取机构下角色分配情况", httpMethod = "GET")
    @ApiImplicitParam(name = "orgId", value = "机构id", paramType = "query", dataType = "Integer")
    @GetMapping("/searchOrgRole")
    public AjaxResult searchOrgRole(OrgRoleSearchPojo roleSearchPojo) {
        return AjaxResult.success(orgCommissionerService.searchOrgRole(roleSearchPojo));
    }

    @ApiOperation(value = "删除指定机构下某个角色", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "数据库唯一标识id", paramType = "query", dataType = "Integer")
    @PostMapping("/delOrgRoleById")
    public AjaxResult delOrgRoleById(@RequestParam("id") Integer id) {
        return orgCommissionerService.delOrgRoleById(id);
    }

    @ApiOperation(value = "角色分配", httpMethod = "POST")
    @PostMapping("/createOrgCommissioner")
    public AjaxResult createOrgCommissioner(@RequestBody OrgCommissioner orgCommissioner) {
        return orgCommissionerService.createOrgCommissioner(orgCommissioner);
    }

    @ApiOperation(value = "角色分配-用户模糊查询", httpMethod = "GET")
    @GetMapping("/searchOrgUser")
    public AjaxResult searchOrgUser(OrgUserSearchPojo orgUserSearchPojo) {
        return userService.searchOrgUser(orgUserSearchPojo);
    }

    @ApiOperation(value = "角色导入", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/orgRoleImport")
    public AjaxResult orgRoleImport(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return orgCommissionerService.orgRoleImport(file);
    }

    @ApiOperation(value = "角色导入校检", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/orgRoleImportCheck")
    public AjaxResult orgRoleImportCheck(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return orgCommissionerService.orgRoleImportCheck(file);
    }

    @ApiOperation(value = "角色导入模板导出", httpMethod = "GET")
    @GetMapping("/orgRoleModelExport")
    public void orgRoleModelExport(HttpServletResponse res) {
        try {
            Workbook workbook = null;
//            String ctxPath = req.getSession().getServletContext().getRealPath("");
            String path = "/static/excel/orgcomm_import_example.xlsx";
            String name = "角色导入模板";

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            ExcelTool.export(workbook, name + ".xlsx", res);
        } catch (Exception e) {
            log.error("导出异常：" + e);
        }
    }

    @ApiOperation(value = "机构导入", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/orgImport")
    public AjaxResult orgImport(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return orgCommissionerService.orgImport(file);
    }

    @ApiOperation(value = "机构导入校检", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/orgImportCheck")
    public AjaxResult orgImportCheck(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return orgCommissionerService.orgImportCheck(file);
    }

    @ApiOperation(value = "机构导入模板导出", httpMethod = "GET")
    @GetMapping("/orgModelExport")
    public void orgModelExport(HttpServletResponse res) {
        try {
            Workbook workbook = null;
            String path = "/static/excel/org_import_example.xlsx";
            String name = "机构导入模板";

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            ExcelTool.export(workbook, name + ".xlsx", res);
        } catch (Exception e) {
            log.error("导出异常：" + e);
        }
    }
}


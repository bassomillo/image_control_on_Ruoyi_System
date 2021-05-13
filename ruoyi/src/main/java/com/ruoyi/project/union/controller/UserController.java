package com.ruoyi.project.union.controller;


import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.vo.DisableUserVo;
import com.ruoyi.project.union.entity.vo.ResetPasswordVo;
import com.ruoyi.project.union.entity.vo.UserRoleVo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/user")
@Api(tags = "会员管理/账号管理  zjy")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /*************************************************账号管理**********************************************************/
    @ApiOperation(value = "账号管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchAccount")
    public AjaxResult searchAccount(@RequestBody AccountSearchVo accountSearchVo) {
        return userService.searchAccount(accountSearchVo);
    }

    @ApiOperation(value = "更新用户 - 角色", httpMethod = "POST")
    @Log(title = "更新用户角色", businessType = BusinessType.UPDATE)
    @PostMapping("/updateUserRole")
    public AjaxResult updateUserRole(@RequestBody UserRoleVo userRoleVo) {
        userService.updateUserRole(userRoleVo);
        return AjaxResult.success();
    }
    /*************************************************会员管理**********************************************************/
    @ApiOperation(value = "会员管理页面数据显示 + 搜索", httpMethod = "POST")
    @PostMapping("/searchUser")
    public AjaxResult searchUser(@RequestBody UserSearchPojo userSearchPojo) {
        return userService.searchUser(userSearchPojo);
    }

    @ApiOperation(value = "会员管理页面人员导出", httpMethod = "GET")
    @GetMapping("/orgExcelExport")
    public void orgExcelExport(UserSearchPojo userSearchPojo, HttpServletRequest req, HttpServletResponse res) {
        try {
            Workbook workbook = null;
            String path = null;
            String name = null;

            path = "/static/excel/users_export_example.xls";
            name = "users_export_" + LocalDate.now();

            workbook = userService.writeExcel(req, path, userSearchPojo);
            ExcelTool.export(workbook, name + ".xls", res);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
    @PostMapping("/createUser")
    public AjaxResult createUser(@RequestBody UserProfile userProfile) {
        if(userService.isExistMobile(userProfile.getMobile()))
            return AjaxResult.error("新增用户'" + userProfile.getTruename() + "'失败，手机号码已存在");
        if(userService.isExistEmail(userProfile.getEmail()))
            return AjaxResult.error("新增用户'" + userProfile.getTruename() + "'失败，邮箱已存在");
        userService.createUser(userProfile);
        return AjaxResult.success();
    }

    @ApiOperation(value = "用户导入模板导出", httpMethod = "GET")
    @GetMapping("/userModelExport")
    public void userModelExport(HttpServletResponse res) {
        try {
            Workbook workbook = null;
//            String ctxPath = req.getSession().getServletContext().getRealPath("");
            String path = "/static/excel/users_import_example.xls";
            String name = "用户导入模板";

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            ExcelTool.export(workbook, name + ".xls", res);
        } catch (Exception e) {
            log.error("导出异常：" + e);
        }
    }

    @ApiOperation(value = "用户导入校检", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/userImportCheck")
    public AjaxResult userImportCheck(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return userService.userImportCheck(file);
    }

    @ApiOperation(value = "用户导入（不存在则插入，存在则更新，判断依据为手机号）", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @Log(title = "机构角色导入", businessType = BusinessType.IMPORT)
    @PostMapping("/userImport")
    public AjaxResult userImport(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return userService.userImport(file);
    }

    @ApiOperation(value = "禁用账号，管理员密码需经过公钥加密，另外需要定时器解禁还没写", httpMethod = "POST")
    @Log(title = "禁用账号", businessType = BusinessType.OTHER)
    @PostMapping("/disableUser")
    public AjaxResult disableUser(@RequestBody DisableUserVo disableUserVo, HttpServletRequest request) {
        return userService.disableUser(disableUserVo, request);
    }

    @ApiOperation(value = "批量禁用，定时器解禁", httpMethod = "POST")
    @Log(title = "批量禁用", businessType = BusinessType.OTHER)
    @PostMapping("/batchDisableUser")
    public AjaxResult batchDisableUser(@RequestParam("userIds[]") List<Integer> userIds) {
        userService.batchDisableUser(userIds);
        return AjaxResult.success();
    }

    @ApiOperation(value = "批量禁用模板导出", httpMethod = "GET")
    @GetMapping("/disableModelExport")
    public void disableModelExport(HttpServletResponse res) {
        try {
            Workbook workbook = null;
//            String ctxPath = req.getSession().getServletContext().getRealPath("");
            String path = "/static/excel/lock_import_example.xlsx";
            String name = "批量禁用模板";

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            ExcelTool.export(workbook, name + ".xls", res);
        } catch (Exception e) {
            log.error("导出异常：" + e);
        }
    }

    @ApiOperation(value = "批量禁用导入校检", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @PostMapping("/disableImportCheck")
    public AjaxResult disableImportCheck(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return userService.disableImportCheck(file);
    }

    @ApiOperation(value = "批量禁用导入", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "excel文件", paramType = "query", dataType = "file")
    @Log(title = "批量禁用导入", businessType = BusinessType.IMPORT)
    @PostMapping("/disableImport")
    public AjaxResult disableImport(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        return userService.disableImport(file);
    }

    @ApiOperation(value = "编辑用户（个人设置-个人信息）", httpMethod = "POST")
    @Log(title = "编辑用户", businessType = BusinessType.UPDATE)
    @PostMapping("/updateUser")
    public AjaxResult updateUser(@RequestBody UserProfile userProfile) {
        userService.updateUser(userProfile);
        return AjaxResult.success();
    }

    @ApiOperation(value = "查询用户详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "body", dataType = "Integer")
    @GetMapping("/searchUserById")
    public AjaxResult searchUserById(@RequestParam("userId") Integer userId) {
        return userService.searchUserById(userId);
    }

    @ApiOperation(value = "设置用户头像，等文件上传确认", httpMethod = "POST")
    @Log(title = "设置头像", businessType = BusinessType.UPDATE)
    @PostMapping("/setAvatar")
    public AjaxResult setAvatar(@RequestBody UserProfile userProfile) {
        return AjaxResult.success();
    }

    @ApiOperation(value = "重置用户密码，管理员密码需经过公钥加密（个人设置-安全设置）", httpMethod = "POST")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestBody ResetPasswordVo resetPasswordVo, HttpServletRequest request) {
        return userService.resetPassword(resetPasswordVo, request);
    }

    /**************************************************信息审核相关接口**************************************************/

}


package com.ruoyi.project.cstest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.cstest.domain.CsTest;
import com.ruoyi.project.cstest.service.ICsTestService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 测试Controller
 *
 * @author Lion Li
 * @date 2020-02-14
 */
@AllArgsConstructor
@RestController
@RequestMapping("/demo/cstest" )
@Api(tags = "测试接口")
public class CsTestController extends BaseController {

    private final ICsTestService iCsTestService;

    @ApiOperation(value = "日志测试", httpMethod = "GET")
    @GetMapping("/logTest")
    @Log(title = "日志测试", businessType = BusinessType.OTHER)
    public AjaxResult clearMssPending(@RequestParam("x") Integer x) {
        System.out.println(x/2);
        return AjaxResult.success("success!");
    }

    /**
     * 查询测试列表
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(CsTest csTest) {
        startPage();
        LambdaQueryWrapper<CsTest> lqw = new LambdaQueryWrapper<CsTest>();
        if (StringUtils.isNotBlank(csTest.getTestKey())){
            lqw.like(CsTest::getTestKey ,csTest.getTestKey());
        }
        if (StringUtils.isNotBlank(csTest.getValue())){
            lqw.like(CsTest::getValue ,csTest.getValue());
        }
        if (csTest.getCreateTime() != null){
            lqw.eq(CsTest::getCreateTime ,csTest.getCreateTime());
        }
        List<CsTest> list = iCsTestService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出测试列表
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:export')" )
    @Log(title = "测试" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(CsTest csTest) {
        LambdaQueryWrapper<CsTest> lqw = new LambdaQueryWrapper<CsTest>(csTest);
        List<CsTest> list = iCsTestService.list(lqw);
        ExcelUtil<CsTest> util = new ExcelUtil<CsTest>(CsTest. class);
        return util.exportExcel(list, "cstest" );
    }

    /**
     * 获取测试详细信息
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Integer id) {
        return AjaxResult.success(iCsTestService.getById(id));
    }

    /**
     * 新增测试
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:add')" )
    @Log(title = "测试" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CsTest csTest) {
        return toAjax(iCsTestService.save(csTest) ? 1 : 0);
    }

    /**
     * 修改测试
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:edit')" )
    @Log(title = "测试" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CsTest csTest) {
        return toAjax(iCsTestService.updateById(csTest) ? 1 : 0);
    }

    /**
     * 删除测试
     */
    @PreAuthorize("@ss.hasPermi('cstest:cstest:remove')" )
    @Log(title = "测试" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(iCsTestService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

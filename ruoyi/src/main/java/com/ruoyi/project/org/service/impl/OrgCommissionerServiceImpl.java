package com.ruoyi.project.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.converters.LocalDateTimeConverters;
import com.ruoyi.framework.converters.LocalDateTimePattern;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.entity.pojo.OrgRoleSearchPojo;
import com.ruoyi.project.org.service.IOrgCommissionerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.org.service.IOrgService;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserProfileDao;
import com.ruoyi.project.union.service.LoginTokenService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
public class OrgCommissionerServiceImpl extends ServiceImpl<OrgCommissionerDao, OrgCommissioner> implements IOrgCommissionerService {

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private OrgCommissionerDao orgCommissionerDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private IOrgService orgService;

    @Override
    public AjaxResult searchOrgRole(OrgRoleSearchPojo roleSearchPojo) {
        roleSearchPojo.setCurrent((roleSearchPojo.getCurrent() - 1) * roleSearchPojo.getSize());
        return AjaxResult.success(orgCommissionerDao.searchOrgRole(roleSearchPojo));
    }

    @Override
    public AjaxResult delOrgRoleById(Integer id) {
        orgCommissionerDao.deleteById(id);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult createOrgCommissioner(OrgCommissioner orgCommissioner) {
        // 需要判断只能有一个市级经理

        if(null == orgCommissioner.getCreatedTime())
            orgCommissioner.setCreatedTime((int) (System.currentTimeMillis() / 1000));
        if(null == orgCommissioner.getUpdatedTime())
            orgCommissioner.setUpdatedTime((int) (System.currentTimeMillis() / 1000));

        orgCommissionerDao.insert(orgCommissioner);
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult orgRoleImport(MultipartFile file) {
        Workbook wb = null;
        try {
            wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            int dataNum = rowEnd - rowStart;
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // 遍历行
                Row row = sheet.getRow(rowNum);

                // 拼接工会组织的orgCode
                String[] orgs = ExcelTool.getCellValue(row.getCell(0)).split("/");
                Integer parentId = -1;
                String orgCode = "";
                for (String o : orgs) {
                    Org org = orgDao.selectOne(new QueryWrapper<Org>().eq(Org.NAME, o).eq(parentId != -1, Org.PARENTID, parentId));
                    if (org != null) {
                        parentId = org.getId();
                        orgCode = orgCode + org.getId() + ".";
                    } else {
                        break;
                    }
                }
                Org org = orgDao.selectOne(new QueryWrapper<Org>().eq(Org.ORGCODE, orgCode));

                String name1 = ExcelTool.getCellValue(row.getCell(1));
                String mobile1 = ExcelTool.getCellValue(row.getCell(2));
                roleImport(name1, mobile1, "主席", org);
                String name2 = ExcelTool.getCellValue(row.getCell(3));
                String mobile2 = ExcelTool.getCellValue(row.getCell(4));
                roleImport(name2, mobile2, "副主席", org);
                String name3 = ExcelTool.getCellValue(row.getCell(5));
                String mobile3 = ExcelTool.getCellValue(row.getCell(6));
                roleImport(name3, mobile3, "机构联系人", org);
            }
            return AjaxResult.success("导入成功，共" + dataNum + "条数据。");
        } catch (Exception e) {
            log.error("Excel文件异常，读取失败：" + e);
            return AjaxResult.error("Excel文件异常，读取失败：" + e);
        }
    }

    private void roleImport(String name, String mobile, String position, Org org) {
        UserProfile user = userProfileDao.selectOne(new QueryWrapper<UserProfile>().eq(UserProfile.TRUENAME, name).eq(UserProfile.MOBILE, mobile));
        OrgCommissioner orgCommissioner = new OrgCommissioner();
        orgCommissioner.setOrgId(org.getId());
        orgCommissioner.setUserId(user.getId());
        orgCommissioner.setPosition(position);
        orgCommissioner.setCreatedTime((int) (System.currentTimeMillis() / 1000));
        orgCommissioner.setUpdatedTime((int) (System.currentTimeMillis() / 1000));

        orgCommissionerDao.insert(orgCommissioner);
    }

    @Override
    @Transactional
    public AjaxResult orgRoleImportCheck(MultipartFile file) {
        List<String> errorMsg = new ArrayList<>();
        Workbook wb = null;
        try {
            wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            ExcelTool.excelCheck(sheet, errorMsg, rowStart, rowEnd);
            if(0 != errorMsg.size())
                return AjaxResult.success(errorMsg);
            int dataNum = rowEnd - rowStart;
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // 遍历行
                boolean isNotExist = false;
                Row row = sheet.getRow(rowNum);

                // 拼接工会组织的orgCode
                String[] orgs = ExcelTool.getCellValue(row.getCell(0)).split("/");
                Integer parentId = -1;
                for (String o : orgs) {
                    Org org = orgDao.selectOne(new QueryWrapper<Org>().eq(Org.NAME, o).eq(parentId != -1, Org.PARENTID, parentId));
                    if (org != null) {
                        parentId = org.getId();
                    } else {
                        isNotExist = true;
                        break;
                    }
                }
                if (isNotExist) {
                    String msg = "第" + rowNum + "行信息有误，所属机构不存在";
                    errorMsg.add(msg);
                }

                String name1 = ExcelTool.getCellValue(row.getCell(1));
                String mobile1 = ExcelTool.getCellValue(row.getCell(2));
                userProfileCheck(name1, mobile1, rowNum, errorMsg);
                String name2 = ExcelTool.getCellValue(row.getCell(3));
                String mobile2 = ExcelTool.getCellValue(row.getCell(4));
                userProfileCheck(name2, mobile2, rowNum, errorMsg);
                String name3 = ExcelTool.getCellValue(row.getCell(5));
                String mobile3 = ExcelTool.getCellValue(row.getCell(6));
                userProfileCheck(name3, mobile3, rowNum, errorMsg);
            }

            if(0 == errorMsg.size())
                errorMsg.add("校检成功，一共包含" + dataNum + "条数据");

            return AjaxResult.success(errorMsg);
        } catch (Exception e) {
            log.error("Excel文件异常，读取失败：" + e);
            errorMsg.add("Excel文件异常，读取失败：" + e);
            return AjaxResult.success(errorMsg);
        }
    }

    private void userProfileCheck(String name, String mobile, int rowNum, List<String> errorMsg) {
        if(null == mobile) {
            String msg = "第" + rowNum + "行信息有误，号码不能为空";
            errorMsg.add(msg);

            return ;
        }
        UserProfile user = userProfileDao.selectOne(new QueryWrapper<UserProfile>().eq(name != null, UserProfile.TRUENAME, name)
                .eq(UserProfile.MOBILE, mobile));
        if(null == user) {
            String msg = "第" + rowNum + "行信息有误，号码为" + mobile + "的用户不存在";
            errorMsg.add(msg);
        }
    }

    @Override
    @Transactional
    public AjaxResult orgImport(MultipartFile file) {
        Workbook wb = null;
        LocalDateTimeConverters localDateTimeConverters = new LocalDateTimeConverters();
        
        try {
            wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            int dataNum = rowEnd - rowStart;
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // 遍历行
                Row row = sheet.getRow(rowNum);

                // 拼接工会组织的orgCode
                Integer realParentId = null;
                orgsCheck(row, rowNum, realParentId, null, 0);
                Org org = new Org();
                org.setName(ExcelTool.getCellValue(row.getCell(1)));
                org.setParentId(realParentId);
                User loginUser = SpringUtils.getBean(LoginTokenService.class).getLoginUser(ServletUtils.getRequest());
                org.setCreatedUserId(loginUser.getId());
                org.setSimpleName(ExcelTool.getCellValue(row.getCell(1)));
                org.setOrgCategory(ExcelTool.getCellValue(row.getCell(2)));
                org.setOrgLevel(ExcelTool.getCellValue(row.getCell(3)));
                org.setLocation(ExcelTool.getCellValue(row.getCell(4)));
                org.setOrgStatus(ExcelTool.getCellValue(row.getCell(5)));
                org.setInAdminOrg(ExcelTool.getCellValue(row.getCell(6)));
                org.setSetTime(LocalDate.parse((ExcelTool.getCellValue(row.getCell(6))), DateTimeFormatter.ofPattern(LocalDateTimePattern.LOCAL_TIME)));
                org.setSocialCode(ExcelTool.getCellValue(row.getCell(8)));

                orgService.createOrg(org);
            }

            return AjaxResult.success("导入成功，共" + dataNum + "条数据。");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Excel文件异常，读取失败：" + e);
            return AjaxResult.error(e.toString());
        }
    }

    public void orgsCheck(Row row, Integer rowNum, Integer realParentId, List<String> errorMsg, Integer cellNum) {
        // 拼接工会组织的orgCode
        String[] orgs = ExcelTool.getCellValue(row.getCell(cellNum)).split("/");
        Integer parentId = -1;
        for (int i = 0; i < orgs.length; i++) {
            Org org = orgDao.selectOne(new QueryWrapper<Org>().eq(Org.NAME, orgs[i]).eq(parentId != -1, Org.PARENTID, parentId));
            if (org != null) {
                /**
                 * 在机构导入时导入的机构串是到机构的上一级父机构为止，此时realParentId的意义是上一级父机构id
                 * 在会员导入时导入的机构串则是直接到机构，此时realParentId的意义是会员所在机构id
                 */
                if(i == orgs.length - 1)
                    realParentId = org.getId();
                parentId = org.getId();
            } else {
                if(null != errorMsg)
                    errorMsg.add("第" + rowNum + "行上级工会组织与实际名称不符。");
                break;
            }
        }
    }

    @Override
    public AjaxResult orgImportCheck(MultipartFile file) {
        // 此处只进行非空校检
        List<String> errorMsg = new ArrayList<>();
        Workbook wb = null;
        try {
            wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            ExcelTool.excelCheck(sheet, errorMsg, rowStart, rowEnd);
            if(0 != errorMsg.size())
                return AjaxResult.success(errorMsg);
            int dataNum = rowEnd - rowStart;
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // 遍历
                Row row = sheet.getRow(rowNum);
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(0)))) {
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含上级工会组织名称。");
                } else {
                    Integer realParentId = null;
                    orgsCheck(row, rowNum, realParentId, errorMsg, 0);
                }
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(1))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含工会组织简称。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(2))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含组织类别。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(3))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含组织级别。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(4))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含所在地。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(5))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含工会组织情况。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(6))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含所在行政单位名称。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(7))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含成立时间。");
                if(StringUtils.isEmpty(ExcelTool.getCellValue(row.getCell(8))))
                    errorMsg.add("第" + rowNum + "行信息有误，导入必须包含统一社会信用代码。");
            }

            if(0 == errorMsg.size())
                errorMsg.add("校检成功，一共包含" + dataNum + "条数据");
            return AjaxResult.success(errorMsg);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Excel文件异常，读取失败：" + e);
            return AjaxResult.error(e.toString());
        }
    }
}

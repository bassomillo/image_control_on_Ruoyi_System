package com.ruoyi.project.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.converters.LocalDateTimeConverters;
import com.ruoyi.framework.converters.LocalDateTimePattern;
import com.ruoyi.framework.security.service.SysLoginService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.org.entity.pojo.OrgUserSearchPojo;
import com.ruoyi.project.org.service.impl.OrgCommissionerServiceImpl;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.SysRoleMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.MD5Utils;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.vo.ResetPasswordVo;
import com.ruoyi.project.union.entity.vo.UserRoleVo;
import com.ruoyi.project.union.mapper.UserProfileDao;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.UserVo;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.vo.DisableUserVo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import com.ruoyi.project.union.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.union.service.LoginTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  ???????????????
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private OrgCommissionerServiceImpl orgCommissionerService;

    @Override
    public AjaxResult searchUser(UserSearchPojo userSearchPojo) {
        Map<String, Object> map = new HashMap<>();

        if(null == userSearchPojo.getSize())
            userSearchPojo.setSize(20);
        if(null == userSearchPojo.getCurrent())
            userSearchPojo.setCurrent(1);
        List<Integer> ids = userDao.searchUser(userSearchPojo);

        IPage page = new Page<>(userSearchPojo.getCurrent(), userSearchPojo.getSize());
        IPage<User> user = userDao.selectPage(page, new QueryWrapper<User>().in(User.ID, ids));
        List<UserVo> userVos = new ArrayList<>();
        for(User u : user.getRecords()) {
            UserVo uv = new UserVo();
            uv.setId(u.getId());
            uv.setNickname(u.getNickname());
            uv.setOrganization(combinOrg(u.getOrgId()));
            uv.setLocked(u.getLocked());

            UserProfile up = userProfileDao.selectById(u.getId());
            uv.setTruename(up.getTruename());
            uv.setGender(up.getGender().equals("male") ? "???" : "???");
            uv.setMobile(up.getMobile());

            userVos.add(uv);
        }
        page.setRecords(userVos);
        map.put("info", page);

        List<User> userList = userDao.selectList(new QueryWrapper<>());
        map.put("total", userList.size()); // ??????
        List<User> userLoecked = userDao.selectList(new QueryWrapper<User>().eq(User.LOCKED, 1));
        map.put("locked", userLoecked.size()); // ????????????

        return AjaxResult.success(map);
    }

    @Override
    public Workbook writeExcel(HttpServletRequest req, String path, UserSearchPojo userSearchPojo) {
        Workbook workbook = null;
        try {
            workbook = ExcelTool.read(req, path);
            Sheet sheet = workbook.getSheetAt(0);

            CellStyle defaultStyle = ExcelTool.getStyle(workbook);
            List<Integer> ids = userDao.searchUser(userSearchPojo);
            List<User> userList = userDao.selectList(new QueryWrapper<User>().in(User.ID, ids));
            int i = 1;
            for (User item : userList) {
                Row row = sheet.createRow(i++);
                UserProfile up = userProfileDao.selectById(item.getId());
                ExcelTool.createCell(row, 0, defaultStyle, up.getTruename()); // ??????
                ExcelTool.createCell(row, 1, defaultStyle, up.getGender().equals("male") ? "???" : "???"); // ??????
                ExcelTool.createCell(row, 2, defaultStyle, up.getMobile()); // ????????????
                ExcelTool.createCell(row, 3, defaultStyle, up.getEmploymentForm()); // ????????????
                ExcelTool.createCell(row, 4, defaultStyle, up.getIsTemp() == 1 ? "???" : "???"); // ??????????????????
                ExcelTool.createCell(row, 5, defaultStyle, up.getIsDerafa() == 1 ? "???" : "???"); // ????????????
//                ExcelTool.createCell(row, 6, defaultStyle, up.getOrgId()); // ????????????
                ExcelTool.createCell(row, 7, defaultStyle, up.getUnionGroup()); // ??????????????????
                ExcelTool.createCell(row, 8, defaultStyle, item.getLocked() == 1 ? "???" : "???"); // ????????????
                ExcelTool.createCell(row, 9, defaultStyle, up.getHardship()); // ????????????
                ExcelTool.createCell(row, 10, defaultStyle, up.getTruename()); // ????????????
                ExcelTool.createCell(row, 11, defaultStyle, up.getBirthday()); // ??????
                ExcelTool.createCell(row, 12, defaultStyle, up.getIdcard()); // ????????????
                ExcelTool.createCell(row, 13, defaultStyle, up.getMss()); // MSS??????
                ExcelTool.createCell(row, 14, defaultStyle, up.getEmail()); // ??????
                ExcelTool.createCell(row, 15, defaultStyle, up.getPoliticalAffiliation()); // ????????????
                ExcelTool.createCell(row, 16, defaultStyle, up.getHighestEducation()); // ????????????
                ExcelTool.createCell(row, 17, defaultStyle, up.getDegree()); // ????????????
                ExcelTool.createCell(row, 18, defaultStyle, up.getCompany()); // ????????????
                ExcelTool.createCell(row, 19, defaultStyle, up.getPartPost()); // ????????????
                ExcelTool.createCell(row, 20, defaultStyle, up.getModelWorkers()); // ??????
                ExcelTool.createCell(row, 21, defaultStyle, up.getJoinWorkDate()); // ??????????????????
                ExcelTool.createCell(row, 22, defaultStyle, up.getPostName()); // ??????
                ExcelTool.createCell(row, 23, defaultStyle, up.getPostLevel()); // ????????????
                ExcelTool.createCell(row, 24, defaultStyle, up.getProfessional()); // ????????????
                ExcelTool.createCell(row, 25, defaultStyle, up.getConcurMember()); // ???????????????????????????????????????
                ExcelTool.createCell(row, 26, defaultStyle, up.getWorkerRepresentative()); // ????????????
                ExcelTool.createCell(row, 27, defaultStyle, up.getUnionJob()); // ???????????????
                ExcelTool.createCell(row, 28, defaultStyle, up.getPartyGroup()); // ????????????
                ExcelTool.createCell(row, 29, defaultStyle, up.getAccountAddress()); // ???????????????
                ExcelTool.createCell(row, 30, defaultStyle, up.getAddress()); // ????????????
                ExcelTool.createCell(row, 31, defaultStyle, up.getHobbies()); // ??????????????????
                ExcelTool.createCell(row, 32, defaultStyle, up.getHonoraryTitle()); // ????????????
                ExcelTool.createCell(row, 33, defaultStyle, up.getSympathyRecord()); // ????????????
//                ExcelTool.createCell(row, 34, defaultStyle, up.getTruename()); // ????????????
                ExcelTool.createCell(row, 35, defaultStyle, up.getProjectProgress()); // ??????
            }
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return workbook;
    }

    @Override
    public AjaxResult searchAccount(AccountSearchVo accountSearchVo) {
        Map<String, Object> map = new HashMap<>();
        accountSearchVo.setCurrent((accountSearchVo.getCurrent() - 1) * accountSearchVo.getSize());
        // ?????????????????? ????????????????????????id???1??? ?????????????????????
        List<SysUserRole> userIds = sysUserRoleMapper.selectList(new QueryWrapper<SysUserRole>().ne(SysRole.ROLE_ID, 1)
                .eq(accountSearchVo.getRoleId() != null, SysRole.ROLE_ID, accountSearchVo.getRoleId()));
        Set<Long> userIdsSet = userIds.stream().map(SysUserRole::getUserId).collect(Collectors.toSet());
        List<UserVo> userVoList = userDao.searchAccount(userIdsSet, accountSearchVo.getNickname(), accountSearchVo.getTruename(), accountSearchVo.getOrgId(),
                accountSearchVo.getCurrent(), accountSearchVo.getSize());
        userVoList.forEach(item -> {
            item.setOrganization(combinOrg(item.getOrgId()));
            item.setRole(sysRoleMapper.selectRolePermissionByUserId((long) item.getId()));
        });

        map.put("userVoList", userVoList);
        map.put("total", userVoList.size());

        return AjaxResult.success(map);
    }

    @Override
    @Transactional
    public void updateUserRole(UserRoleVo userRoleVo) {
        // ??????????????????????????????
        if(null != userRoleVo.getRoles() && 0 != userRoleVo.getRoles().size()) {
            // ???????????????user-role????????????
            sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq(SysUserRole.USER_ID, userRoleVo.getUserId()));
            // ?????????????????????user-role????????????
            for(Long roleId : userRoleVo.getRoles()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userRoleVo.getUserId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    private String combinOrg(Integer orgId) {
        List<String> orgList = new ArrayList<>();
        combinParentOrg(orgId, orgList);
        Collections.reverse(orgList); // ????????????
        StringBuilder org = new StringBuilder();
        for(int i = 0; i < orgList.size(); i++) {
            if(0 == i) {
                org.append(orgList.get(i));
            } else {
                org.append("/").append(orgList.get(i));
            }
        }

        return String.valueOf(org);
    }

    private void combinParentOrg(Integer orgId, List<String> orgList) {
        Org org = orgDao.selectById(orgId);
        orgList.add(org.getName());
        if(1 != org.getId()) {
            combinParentOrg(org.getParentId(), orgList);
        }
    }

    @Override
    public boolean isExistMobile(String mobile) {
        List<UserProfile> userProfile = userProfileDao.selectList(new QueryWrapper<UserProfile>().eq(mobile != null, UserProfile.MOBILE, mobile));
        return 0 != userProfile.size();
    }

    @Override
    public boolean isExistEmail(String email) {
        List<UserProfile> userProfile = userProfileDao.selectList(new QueryWrapper<UserProfile>().eq(email != null, UserProfile.EMAIL, email));
        return 0 != userProfile.size();
    }

    @Override
    public boolean isExistNickname(String nickname) {
        List<User> user = userDao.selectList(new QueryWrapper<User>().eq(nickname != null, User.NICKNAME, nickname));
        return 0 != user.size();
    }

    @Override
    @Transactional
    public void createUser(UserProfile userProfile) {
        // ??????user????????????????????????????????????id????????????user_profile???id
        User user = new User();
        user.setNickname("user" + IdUtils.simpleUUID());
        user.setPassword(MD5Utils.calc(Str.INITIAL_PASSWORD));
        user.setCreatedTime((int) (System.currentTimeMillis() / 1000));
        user.setUpdatedTime((int) (System.currentTimeMillis() / 1000));
        user.setOrgId(userProfile.getOrgId());
        Org org = orgDao.selectById(userProfile.getOrgId());
        user.setOrgCode(org != null ? org.getOrgCode() : "");
        userDao.insert(user);

        userProfile.setId(user.getId());
        userProfileDao.insert(userProfile);
    }

    @Override
    @Transactional
    public AjaxResult userImportCheck(MultipartFile file) {
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
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // ?????????
                boolean isNotExist = false;
                Row row = sheet.getRow(rowNum);

                // ????????????: ???????????????????????????????????????????????????????????????????????????????????????????????????
                String name = ExcelTool.getCellValue(row.getCell(0));
                if(StringUtils.isEmpty(name))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????");
                String gender = ExcelTool.getCellValue(row.getCell(1));
                if(StringUtils.isEmpty(gender))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????");
                String mobile = ExcelTool.getCellValue(row.getCell(2));
                if(StringUtils.isEmpty(mobile)) {
                    errorMsg.add("???" + rowNum + "???????????????????????????????????????");
                } else {
                    if(!Str.isNumeric(mobile)) {
                        errorMsg.add("???" + rowNum + "???????????????????????????????????????");
                    }
//                    else {
//                        List<UserProfile> up = userProfileDao.selectList(new QueryWrapper<UserProfile>().eq(UserProfile.MOBILE, mobile));
//                        if(0 != up.size())
//                            errorMsg.add("???" + rowNum + "??????????????????????????????" + mobile + "??????????????????");
//                    }
                }
                String orgName = ExcelTool.getCellValue(row.getCell(6));
                if(StringUtils.isEmpty(orgName)) {
                    errorMsg.add("???" + rowNum + "??????????????????????????????????????????");
                } else {
                    Integer orgId = null;
                    orgCommissionerService.orgsCheck(row, rowNum, orgId, errorMsg, 6);
                }
                String unionGroup = ExcelTool.getCellValue(row.getCell(7));
                if(StringUtils.isEmpty(unionGroup))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????????????????");
                String isDerafa = ExcelTool.getCellValue(row.getCell(5));
                if(StringUtils.isEmpty(isDerafa))
                    errorMsg.add("???" + rowNum + "??????????????????????????????????????????");
                String isTemp = ExcelTool.getCellValue(row.getCell(4));
                if(StringUtils.isEmpty(isTemp))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????????????????");

                String birthday = ExcelTool.getCellValue(row.getCell(10));
                if(!StringUtils.isEmpty(birthday) && !Str.dateCheck(birthday))
                    errorMsg.add("???" + rowNum + "??????????????????????????????????????????");
                String joinWorkDate = ExcelTool.getCellValue(row.getCell(22));
                if(!StringUtils.isEmpty(joinWorkDate) && !Str.dateCheck(joinWorkDate))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????????????????");
            }

            if(0 != errorMsg.size())
                return AjaxResult.error("????????????", errorMsg);

            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // ?????????
                Row row = sheet.getRow(rowNum);

                // ?????????????????????orgCode
                Integer orgId = null;
                orgCommissionerService.orgsCheck(row, rowNum, orgId, null, 6);
                UserProfile userProfile = new UserProfile();
                userProfile.setTruename(ExcelTool.getCellValue(row.getCell(0)));
                userProfile.setGender(ExcelTool.getCellValue(row.getCell(1)).equals("???") ? "male" : "female");
                userProfile.setMobile(ExcelTool.getCellValue(row.getCell(2)));
                userProfile.setOrgId(orgId);
                userProfile.setUnionGroup(ExcelTool.getCellValue(row.getCell(7)));
                userProfile.setIsDerafa(ExcelTool.getCellValue(row.getCell(5)).equals("???") ? 1 : 0);
                userProfile.setIsTemp(ExcelTool.getCellValue(row.getCell(4)).equals("???") ? 1 : 0);
                userProfile.setHardship(ExcelTool.getCellValue(row.getCell(9)));
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                userProfile.setBirthday(LocalDate.parse(ExcelTool.getCellValue(row.getCell(10)), fmt));
                userProfile.setNation(ExcelTool.getCellValue(row.getCell(11)));
                userProfile.setIdcard(ExcelTool.getCellValue(row.getCell(12)));
                userProfile.setMss(ExcelTool.getCellValue(row.getCell(13)));
                userProfile.setEmail(ExcelTool.getCellValue(row.getCell(14)));
                userProfile.setPoliticalAffiliation(ExcelTool.getCellValue(row.getCell(15)));
                userProfile.setHighestEducation(ExcelTool.getCellValue(row.getCell(16)));
                userProfile.setDegree(ExcelTool.getCellValue(row.getCell(17)));
                userProfile.setCompany(ExcelTool.getCellValue(row.getCell(18)));
                userProfile.setDepartment(ExcelTool.getCellValue(row.getCell(19)));
                userProfile.setPartPost(ExcelTool.getCellValue(row.getCell(20)));
                userProfile.setModelWorkers(ExcelTool.getCellValue(row.getCell(21)));
                userProfile.setJoinWorkDate(LocalDate.parse(ExcelTool.getCellValue(row.getCell(22)), fmt));
                userProfile.setPostName(ExcelTool.getCellValue(row.getCell(23)));
                userProfile.setPostLevel(ExcelTool.getCellValue(row.getCell(24)));
                userProfile.setProfessional(ExcelTool.getCellValue(row.getCell(25)));
                userProfile.setConcurMember(ExcelTool.getCellValue(row.getCell(26)));
                userProfile.setWorkerRepresentative(ExcelTool.getCellValue(row.getCell(27)));
                userProfile.setUnionJob(ExcelTool.getCellValue(row.getCell(28)));
                userProfile.setPartyGroup(ExcelTool.getCellValue(row.getCell(29)));
                userProfile.setAccountAddress(ExcelTool.getCellValue(row.getCell(30)));
                userProfile.setAddress(ExcelTool.getCellValue(row.getCell(31)));
                userProfile.setHobbies(ExcelTool.getCellValue(row.getCell(32)));
                userProfile.setHonoraryTitle(ExcelTool.getCellValue(row.getCell(33)));
                userProfile.setSympathyRecord(ExcelTool.getCellValue(row.getCell(34)));
//                userProfile.setAccountAddress(ExcelTool.getCellValue(row.getCell(35))); ???????????????
                userProfile.setProjectProgress(ExcelTool.getCellValue(row.getCell(36)));

                UserProfile upExist = userProfileDao.selectOne(new QueryWrapper<UserProfile>()
                        .eq(UserProfile.MOBILE, ExcelTool.getCellValue(row.getCell(2))));
                // ???????????????????????????????????????????????????
                if(null != upExist) {
                    userProfile.setId(upExist.getId());
                    updateUser(userProfile);
                } else {
                    createUser(userProfile);
                }
            }

            return AjaxResult.success("??????????????????" + dataNum + "????????????");
        } catch (Exception e) {
            log.error("Excel??????????????????????????????" + e);
            return AjaxResult.error("Excel??????????????????????????????" + e);
        }
    }

    @Override
    @Transactional
    public AjaxResult userImport(MultipartFile file) {
        Workbook wb = null;

        try {
            wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            int dataNum = rowEnd - rowStart;
//            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // ?????????
//                Row row = sheet.getRow(rowNum);
//
//                // ?????????????????????orgCode
//                Integer orgId = null;
//                orgCommissionerService.orgsCheck(row, rowNum, orgId, null, 6);
//                UserProfile userProfile = new UserProfile();
//                userProfile.setTruename(ExcelTool.getCellValue(row.getCell(0)));
//                userProfile.setGender(ExcelTool.getCellValue(row.getCell(1)).equals("???") ? "male" : "female");
//                userProfile.setMobile(ExcelTool.getCellValue(row.getCell(2)));
//                userProfile.setOrgId(orgId);
//                userProfile.setUnionGroup(ExcelTool.getCellValue(row.getCell(7)));
//                userProfile.setIsDerafa(ExcelTool.getCellValue(row.getCell(5)).equals("???") ? 1 : 0);
//                userProfile.setIsTemp(ExcelTool.getCellValue(row.getCell(4)).equals("???") ? 1 : 0);
//                userProfile.setHardship(ExcelTool.getCellValue(row.getCell(9)));
//                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                userProfile.setBirthday(LocalDate.parse(ExcelTool.getCellValue(row.getCell(10)), fmt));
//                userProfile.setNation(ExcelTool.getCellValue(row.getCell(11)));
//                userProfile.setIdcard(ExcelTool.getCellValue(row.getCell(12)));
//                userProfile.setMss(ExcelTool.getCellValue(row.getCell(13)));
//                userProfile.setEmail(ExcelTool.getCellValue(row.getCell(14)));
//                userProfile.setPoliticalAffiliation(ExcelTool.getCellValue(row.getCell(15)));
//                userProfile.setHighestEducation(ExcelTool.getCellValue(row.getCell(16)));
//                userProfile.setDegree(ExcelTool.getCellValue(row.getCell(17)));
//                userProfile.setCompany(ExcelTool.getCellValue(row.getCell(18)));
//                userProfile.setDepartment(ExcelTool.getCellValue(row.getCell(19)));
//                userProfile.setPartPost(ExcelTool.getCellValue(row.getCell(20)));
//                userProfile.setModelWorkers(ExcelTool.getCellValue(row.getCell(21)));
//                userProfile.setJoinWorkDate(LocalDate.parse(ExcelTool.getCellValue(row.getCell(22)), fmt));
//                userProfile.setPostName(ExcelTool.getCellValue(row.getCell(23)));
//                userProfile.setPostLevel(ExcelTool.getCellValue(row.getCell(24)));
//                userProfile.setProfessional(ExcelTool.getCellValue(row.getCell(25)));
//                userProfile.setConcurMember(ExcelTool.getCellValue(row.getCell(26)));
//                userProfile.setWorkerRepresentative(ExcelTool.getCellValue(row.getCell(27)));
//                userProfile.setUnionJob(ExcelTool.getCellValue(row.getCell(28)));
//                userProfile.setPartyGroup(ExcelTool.getCellValue(row.getCell(29)));
//                userProfile.setAccountAddress(ExcelTool.getCellValue(row.getCell(30)));
//                userProfile.setAddress(ExcelTool.getCellValue(row.getCell(31)));
//                userProfile.setHobbies(ExcelTool.getCellValue(row.getCell(32)));
//                userProfile.setHonoraryTitle(ExcelTool.getCellValue(row.getCell(33)));
//                userProfile.setSympathyRecord(ExcelTool.getCellValue(row.getCell(34)));
////                userProfile.setAccountAddress(ExcelTool.getCellValue(row.getCell(35))); ???????????????
//                userProfile.setProjectProgress(ExcelTool.getCellValue(row.getCell(36)));
//
//                createUser(userProfile);
//            }

            return AjaxResult.success("??????????????????" + dataNum + "????????????");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Excel??????????????????????????????" + e);
            return AjaxResult.error(e.toString());
        }
    }

    @Override
    @Transactional
    public AjaxResult disableUser(DisableUserVo disableUserVo, HttpServletRequest request) {
        // ????????????????????????
        if(!checkPassword(disableUserVo.getPublicKey(), disableUserVo.getPassword(), request))
            return AjaxResult.error("??????????????????????????????");

        // ???user????????????
        User user = userDao.selectById(disableUserVo.getDisableUserId());
        user.setLocked(1);
        if(null == disableUserVo.getLockDeadline()) {

        } else {
            Long lockDeadline = disableUserVo.getLockDeadline().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
            user.setLockDeadline(lockDeadline / 1000);
        }

        userDao.updateById(user);
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public void batchDisableUser(List<Integer> userIds) {
        userIds.forEach(item -> {
            User user = userDao.selectById(item);
            user.setLocked(1);
            userDao.updateById(user);
        });
    }

    @Override
    @Transactional
    public AjaxResult disableImportCheck(MultipartFile file) {
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
            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // ?????????
                Row row = sheet.getRow(rowNum);

                String account = ExcelTool.getCellValue(row.getCell(0));
                if(StringUtils.isEmpty(account))
                    errorMsg.add("???" + rowNum + "????????????????????????????????????");
            }

            if(0 != errorMsg.size())
                return AjaxResult.error("????????????", errorMsg);

            for(int rowNum = rowStart; rowNum < rowEnd; rowNum++) { // ?????????
                Row row = sheet.getRow(rowNum);

                String account = ExcelTool.getCellValue(row.getCell(0));
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String lockTime = ExcelTool.getCellValue(row.getCell(1));
                LocalDate locakDate = LocalDate.parse(lockTime, fmt);
                // ???user????????????
                List<User> userNickName = userDao.selectList(new QueryWrapper<User>().eq(User.NICKNAME, account));
                List<User> userMobile = userDao.selectUserByMobile(account);
                List<User> userEmail = userDao.selectList(new QueryWrapper<User>().eq(User.EMAIL, account));

                Set<User> userSet = new HashSet<>();
                userSet.addAll(userNickName);
                userSet.addAll(userMobile);
                userSet.addAll(userEmail);

                userSet.forEach(item -> {
                    item.setLocked(1);
                    Long lockDeadline = locakDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
                    item.setLockDeadline(lockDeadline / 1000);

                    userDao.updateById(item);
                });
            }

            return AjaxResult.success("??????????????????" + dataNum + "????????????");
        } catch (Exception e) {
            log.error("Excel??????????????????????????????" + e);
            errorMsg.add("Excel??????????????????????????????" + e);
            return AjaxResult.success(errorMsg);
        }
    }

    @Override
    @Transactional
    public AjaxResult disableImport(MultipartFile file) {
        return null;
    }

    @Override
    @Transactional
    public void updateUser(UserProfile userProfile) {
        User user = new User();
        user.setId(userProfile.getId());
        user.setUpdatedTime((int) (System.currentTimeMillis() / 1000));
        if(null != userProfile.getOrgId()) {
            user.setOrgId(userProfile.getOrgId());
            Org org = orgDao.selectById(userProfile.getOrgId());
            user.setOrgCode(org != null ? org.getOrgCode() : "");
        }
        if(null != userProfile.getNickname())
            user.setNickname(userProfile.getNickname());
        if(null != userProfile.getTitle())
            user.setTitle(userProfile.getTitle());

        userDao.updateById(user);
        userProfileDao.updateById(userProfile);
    }

    @Override
    public AjaxResult searchUserById(Integer userId) {
        UserProfile userProfile = userProfileDao.selectById(userId);
        User user = userDao.selectById(userId);
        userProfile.setOrganization(combinOrg(user.getOrgId()));
        userProfile.setOrgId(user.getOrgId());
        userProfile.setId(userId);
        return AjaxResult.success(userProfile);
    }

    @Override
    public AjaxResult searchOrgUser(OrgUserSearchPojo orgUserSearchPojo) {
        orgUserSearchPojo.setCurrent((orgUserSearchPojo.getCurrent() - 1 ) * orgUserSearchPojo.getSize());
        return AjaxResult.success(userDao.searchOrgUser(orgUserSearchPojo));
    }

    @Override
    public User searchUserByAccount(String account) {
        return userDao.selectOne(new QueryWrapper<User>().eq(User.NICKNAME, account));
    }

    @Override
    @Transactional
    public AjaxResult resetPassword(ResetPasswordVo resetPasswordVo, HttpServletRequest request) {
        // ????????????????????????
        if(!checkPassword(resetPasswordVo.getPublicKey(), resetPasswordVo.getPassword(), request))
            return AjaxResult.error("??????????????????????????????");
//        String manageRealPwd = sysLoginService.getRealPwd(resetPasswordVo.getPublicKey(), resetPasswordVo.getPassword());
//        User loginUser = loginTokenService.getLoginUser(request);
//        if(!manageRealPwd.equals(loginUser.getPassword())) {
//            return AjaxResult.error("??????????????????????????????");
//        }

        // ????????????
        if(null == resetPasswordVo.getResetPassword()) {
            resetPasswordVo.setResetPassword(MD5Utils.calc(Str.INITIAL_PASSWORD));
        } else {
            resetPasswordVo.setResetPassword(MD5Utils.calc(resetPasswordVo.getResetPassword()));
        }

        // ??????
        User user = new User();
        user.setId(resetPasswordVo.getResetUserId());
        user.setPassword(resetPasswordVo.getResetPassword());
        userDao.updateById(user);
        return AjaxResult.success();
    }

    /******************************************************************************************************************/
    public boolean checkPassword(String publicKey, String password, HttpServletRequest request) {
        String manageRealPwd = sysLoginService.getRealPwd(publicKey, password);
        User loginUser = loginTokenService.getLoginUser(request);
        return manageRealPwd.equals(loginUser.getPassword());
    }
}

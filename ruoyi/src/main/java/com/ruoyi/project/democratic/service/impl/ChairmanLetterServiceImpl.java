package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.entity.DO.FileUrlNameDO;
import com.ruoyi.project.democratic.entity.DO.ManageExportDO;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.entity.VO.ChairmanLetterBackVO;
import com.ruoyi.project.democratic.mapper.ChairmanLetterMapper;
import com.ruoyi.project.democratic.mapper.ManagerLetterMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.IChairmanLetterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.entity.OrgCommissioner;
import com.ruoyi.project.org.mapper.OrgCommissionerDao;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.User;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserDao;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ??????????????? ???????????????
 * </p>
 *
 * @author cxr
 * @since 2021-04-12
 */
@Service
public class ChairmanLetterServiceImpl extends ServiceImpl<ChairmanLetterMapper, ChairmanLetterBox> implements IChairmanLetterService {

    @Autowired
    private ChairmanLetterMapper chairmanLetterMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;
    @Autowired
    private ManagerLetterMapper managerLetterMapper;
    @Autowired
    private OrgCommissionerDao orgCommissionerDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UserDao userDao;

    @Resource
    private ToolUtils toolUtils;
    @Resource
    private FastdfsClientUtil fastdfsClientUtil;

    /**
     * ??????????????????id
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getLetterMan(Integer userId) {
        try {
            //???????????????id
            User user = userDao.selectOne(new QueryWrapper<User>().
                    eq(User.ID, userId));
            Map<String, List<Integer>> map = toolUtils.getChairmanId(user.getOrgId());
            List<Integer> provinceList = map.get("?????????");
            List<Integer> cityList = map.get("?????????");
            Integer province = provinceList.size()==0 ? null : provinceList.get(0);
            Integer city = cityList.size()==0 ? null : cityList.get(0);

            List<JSONObject> list = new ArrayList<>();
            if (province != null) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("position", "?????????");
                jsonObject1.put("positionId", province);
                list.add(jsonObject1);
            }
            if (city != null) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("position", "?????????");
                jsonObject2.put("positionId", city);
                list.add(jsonObject2);
            }

            return AjaxResult.success("????????????", list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param letterBox
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertChairmanLetter(ChairmanLetterBox letterBox) {
        try {
            letterBox.setCreateDate(new Date());
            save(letterBox);

            if (letterBox.getFileList() != null && letterBox.getFileList().size() > 0){
                List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, letterBox.getFileList()));
                for (UploadFiles file : filesList){
                    file.setTargetId(letterBox.getId());
                    file.setTargetType("chairman");
                    uploadFilesMapper.updateById(file);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????????????????
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopChairmanList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<ChairmanLetterBox> letterList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.FROMID, userId).
                    eq(ChairmanLetterBox.PARENTID, 0).
                    orderByDesc(ChairmanLetterBox.CREATEDATE));
            PageInfo pageInfo = new PageInfo<>(letterList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", pageInfo.getList());
            jsonObject.put("total", pageInfo.getTotal());

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ??????????????????
     * @param id
     * @return
     */
    @Override
    public AjaxResult getChairmanDetailById(Integer id) {
        try {
            //???????????????
            ChairmanLetterBox letter = chairmanLetterMapper.selectOne(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.ID, id));
            //??????????????????
            UserProfile user = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                    eq(UserProfile.ID, letter.getFromId()));
            letter.setFromName(user.getTruename());
            //???????????????
            List<ChairmanLetterBox> replyList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.PARENTID, id).
                    orderByDesc(ChairmanLetterBox.CREATEDATE));
            //?????????
            List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETTYPE, "chairman").
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", letter);
            jsonObject.put("require", replyList);
            jsonObject.put("file", filesList);

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ??????-???????????????
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    @Override
    public AjaxResult evaluate(String evaluate, String evaluateContent, Integer requireId) {
        try {
            ChairmanLetterBox letterBox = chairmanLetterMapper.selectOne(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.ID, requireId));
            letterBox.setEvaluate(evaluate);
            letterBox.setEvaluateContent(evaluateContent);
            updateById(letterBox);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ????????????????????????
     * @param content
     * @param year
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackLetterList(String content, Integer year, Integer userId, Integer pageNum, Integer pageSize) {
        try {
            content = Str.fuzzyQuery(content);
            List<String> provinceInt = getChairmanProvince(userId);
            List<String> cityInt = getChairmanCity(userId);
            Integer isAdmin = getIsAdmin(userId);

            List<String> intList = new ArrayList<>();
            intList.addAll(provinceInt);
            intList.addAll(cityInt);

            PageHelper.startPage(pageNum, pageSize);
            List<ChairmanLetterBackVO> backList = chairmanLetterMapper.selectLetterList(content, year, intList, isAdmin);
            for (ChairmanLetterBackVO letter : backList){
                //???????????????????????????
                String orgName = toolUtils.getOrgName(letter.getOrgId());
                letter.setOrgName(orgName);
                //????????????
                List<UploadFiles> files = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.TARGETTYPE, "chairman").
                        eq(UploadFiles.TARGETID, letter.getId()).
                        eq(UploadFiles.STATUS, "ok"));
                if (files != null && files.size() != 0) {
                    letter.setImgUrl(files.get(0).getUri());
                }
            }
            PageInfo pageInfo = new PageInfo<>(backList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param idList
     * @return
     */
    @Override
    public AjaxResult deleteLetter(List<Integer> idList) {
        try {
            List<ChairmanLetterBox> letterList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    in(ChairmanLetterBox.ID, idList).
                    or().
                    in(ChairmanLetterBox.PARENTID, idList));
            for (ChairmanLetterBox letter : letterList){
                letter.setIsShow(0);
            }

            boolean flag = updateBatchById(letterList);
            if (flag){
                return AjaxResult.success("????????????");
            }else {
                return AjaxResult.error("??????????????????????????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param replyLetter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult replyLetter(ReplyLetterDO replyLetter) {
        try {
            //?????????????????????
            List<ChairmanLetterBox> letterBoxList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    in(ChairmanLetterBox.ID, replyLetter.getIdList()));
            //??????????????????
            List<ChairmanLetterBox> replyList = new ArrayList<>();
            for (ChairmanLetterBox letter : letterBoxList){
                letter.setRealReply(replyLetter.getUserId());
                letter.setIsReply(1);

                ChairmanLetterBox letterBox = new ChairmanLetterBox();
                letterBox.setContent(replyLetter.getContent());
                letterBox.setFromId(letter.getToId());
                letterBox.setParentId(letter.getId());
                letterBox.setToId(letter.getFromId());
                letterBox.setCreateDate(new Date());
                letterBox.setRealReply(replyLetter.getUserId());
                letterBox.setReceiver(letter.getReceiver());
                replyList.add(letterBox);
            }
            saveBatch(replyList);
            updateBatchById(letterBoxList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ????????????
     * @param file
     * @param userId
     * @return
     */
    @Override
    public AjaxResult uploadFile(MultipartFile file, Integer userId) {
        try {
//            String url = fastdfsClientUtil.uploadFile(file);
            String url = null;

            //??????????????????????????????
            Integer size = (int) file.getSize();
            UploadFiles uploadFiles = new UploadFiles();
            uploadFiles.setFileSize(size);
            String name = file.getOriginalFilename();
            uploadFiles.setFilename(name);
            uploadFiles.setUri(url);
            uploadFiles.setExt(name.substring(name.indexOf(".") + 1));
            uploadFiles.setCreatedUserId(userId);
            uploadFiles.setUpdatedUserId(userId);
            long t = System.currentTimeMillis() / 1000;
            Integer time = Integer.valueOf(Long.toString(t));
            uploadFiles.setCreatedTime(time);
            uploadFiles.setUpdatedTime(time);
            uploadFilesMapper.insert(uploadFiles);

            return AjaxResult.success("????????????", uploadFiles);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteFile(Integer id) {
        try {
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));
            //?????????uri?????????????????????
//            boolean flag = fastdfsClientUtil.deleteFile(uploadFile.getUri());
//            if (! flag){
//                return AjaxResult.error("?????????????????????????????????");
//            }
            uploadFilesMapper.delete(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));

            return AjaxResult.success("????????????");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param id
     * @param response
     * @return
     */
    @Override
    public AjaxResult downloadFile(Integer id, HttpServletResponse response) {
        try {
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));
            //?????????uri???????????????
//            fastdfsClientUtil.downLoadFile(response, uploadFile.getUri(), uploadFile.getFilename());

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????
     * @param content
     * @param year
     * @param userId
     * @param response
     * @param request
     * @return
     */
    @Override
    public AjaxResult export(String content, Integer year, Integer userId, HttpServletResponse response, HttpServletRequest request) {
        try {
            content = Str.fuzzyQuery(content);
            List<String> provinceInt = getChairmanProvince(userId);
            List<String> cityInt = getChairmanCity(userId);
            Integer isAdmin = getIsAdmin(userId);
            List<String> intList = new ArrayList<>();
            intList.addAll(provinceInt);
            intList.addAll(cityInt);
            List<ManageExportDO> exportList = chairmanLetterMapper.getExportData(content, year, intList, isAdmin);

            //????????????
            int i = 1;
            for (ManageExportDO export : exportList){
                //????????????
                export.setId(i++);
                //?????????????????????
                String orgName = toolUtils.getOrgName(export.getOrgId());
                export.setOrgName(orgName);
                //????????????
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                export.setSendDate(sdf.format(export.getDate()));
                //????????????
                List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.TARGETID, export.getLetterId()).
                        eq(UploadFiles.TARGETTYPE, "chairman").
                        eq(UploadFiles.STATUS, "ok"));
                List<FileUrlNameDO> fileDOList = new ArrayList<>();
                for (UploadFiles file : filesList){
                    FileUrlNameDO fileDO = new FileUrlNameDO();
                    fileDO.setUrl(file.getUri());
                    fileDO.setName(file.getFilename());
                    fileDOList.add(fileDO);
                }
                export.setFiles(fileDOList);
                //????????????
                List<ChairmanLetterBox> replyList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                        eq(ChairmanLetterBox.PARENTID, export.getLetterId()).
                        eq(ChairmanLetterBox.ISSHOW, 1));
                String reply = "";
                Integer j = 1;
                for (ChairmanLetterBox r : replyList){
                    reply += j.toString() + "???" + r.getContent() + "\n";
                    j++;
                }
                if (j != 1){
                    reply = reply.substring(0, reply.length() - 1);
                }
                export.setReply(reply);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "????????????" + sdf.format(date) + ".xls";

            //??????
            Workbook wb = getWorkbook(exportList, request);
            ExcelTool.export(wb, name, response);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }


    //??????????????????
    private Workbook getWorkbook(List<ManageExportDO> exportList, HttpServletRequest request) throws Exception{
        //????????????
        Workbook wb = ExcelTool.read(request, "/static/excel/????????????????????????.xls");
        //??????sheet
        Sheet sheet = wb.getSheetAt(0);
        //????????????????????????style
        CellStyle style = ExcelTool.getStyle(wb);

        //???????????????excel
        int r = 1;
        Row row = null;
        for (ManageExportDO manage : exportList){
            String reply = "????????????";
            if (!"".equals(manage.getReply()) && manage.getReply() != null){
                reply = manage.getReply();
            }

            //????????????
            row = sheet.createRow(r++);
            //??????????????????
            ExcelTool.createCell(row, 0, style, manage.getId());
            ExcelTool.createCell(row, 1, style, manage.getFromName());
            ExcelTool.createCell(row, 2, style, manage.getOrgName());
            ExcelTool.createCell(row, 3, style, manage.getObject());
            ExcelTool.createCell(row, 4, style, manage.getSendDate());
            ExcelTool.createCell(row, 5, style, manage.getContent());
            ExcelTool.createCell(row, 7, style, manage.getToName());
            ExcelTool.createCell(row, 8, style, reply);
            //???????????????????????????
            if (manage.getFiles() == null || manage.getFiles().size() == 0){
                Cell cell = row.createCell(6);
                cell.setCellStyle(style);
                cell.setCellValue("????????????");
            }else {
                for (FileUrlNameDO file : manage.getFiles()) {
                    Cell cell = row.createCell(6);
                    cell.setCellStyle(style);
                    cell.setCellFormula("HYPERLINK(\"" + file.getUrl() +"\",\"" + file.getName() + "\")");

                    row = sheet.createRow(r++);
                }
                r--;
                //?????????????????????????????????????????????
                for (int j=0; j<=8; j++) {
                    //????????????????????????6???
                    if (j == 6){
                        continue;
                    }
                    //???????????????????????????
                    if (manage.getFiles().size() > 1) {
                        CellRangeAddress region = new CellRangeAddress(r - manage.getFiles().size(),
                                r - 1, j, j);
                        sheet.addMergedRegion(region);
                    }
                }
            }
        }

        return wb;
    }

    /**
     * ????????????????????????1????????????2????????????
     * @param userId
     * @return
     */
    private Integer getIsAdmin(Integer userId){
        Integer isAdmin = 2;

        //?????????
        SysRole sysRole = managerLetterMapper.getUserRole(userId);
        if (sysRole != null && "ROLE_SUPER_ADMIN".equals(sysRole.getRoleKey())){
            isAdmin = 1;
        }

        return isAdmin;
    }

    /**
     * ???????????????/????????????/???????????????????????? ??????????????????
     * @param userId
     * @return
     */
    private List<String> getChairmanProvince(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        List<String> provinceInt = new ArrayList<>();
        //????????????
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            if ("?????????".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("????????????".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("????????????????????????".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("??????".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }
            }else if ("?????????".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }
            }
        }

        //?????????+????????????+????????????????????????
        for (OrgCommissioner comm : province){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //??????????????????????????????????????????????????????????????????????????????????????????
            List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
            for (Integer id : orgTreeIds){
                org = orgDao.selectOne(new QueryWrapper<Org>().
                        eq(Org.ID, id));
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "???????????????????????????".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                    break;
                }
            }
        }

        return provinceInt;
    }

    /**
     * ???????????????/????????????/???????????????????????? ??????????????????
     * @param userId
     * @return
     */
    private List<String> getChairmanCity(Integer userId){
        List<OrgCommissioner> city = new ArrayList<>();
        List<String> cityInt = new ArrayList<>();

        //????????????
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            if ("?????????".equals(comm.getPosition())){
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("????????????".equals(comm.getPosition())){
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("????????????????????????".equals(comm.getPosition())){
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("??????".equals(comm.getPosition())){
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }
            }else if ("?????????".equals(comm.getPosition())){
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }
            }
        }

        //?????????+????????????+????????????????????????
        for (OrgCommissioner comm : city){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //??????????????????????????????????????????????????????????????????????????????????????????
            List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
            for (Integer id : orgTreeIds){
                org = orgDao.selectOne(new QueryWrapper<Org>().
                        eq(Org.ID, id));
                if ("???????????????".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                    break;
                }
            }
        }

        return cityInt;
    }
}

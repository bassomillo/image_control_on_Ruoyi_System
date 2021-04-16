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
import com.ruoyi.project.union.entity.UserProfile;
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
 * 主席信箱表 服务实现类
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

    @Resource
    private ToolUtils toolUtils;
    @Resource
    private FastdfsClientUtil fastdfsClientUtil;

    /**
     * 获取写信对象id
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getLetterMan(Integer userId) {
        try {
            Map<String, List<Integer>> map = toolUtils.getChairmanId(userId);
            List<Integer> provinceList = map.get("省主席");
            List<Integer> cityList = map.get("市主席");
            Integer province = provinceList.size()==0 ? null : provinceList.get(0);
            Integer city = cityList.size()==0 ? null : cityList.get(0);

            List<JSONObject> list = new ArrayList<>();
            if (province != null) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("position", "省主席");
                jsonObject1.put("positionId", province);
                list.add(jsonObject1);
            }
            if (city != null) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("position", "市主席");
                jsonObject2.put("positionId", city);
                list.add(jsonObject2);
            }

            return AjaxResult.success("获取成功", list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("获取失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 发送信件
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
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查询首页列表
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

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 查询记录详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getChairmanDetailById(Integer id) {
        try {
            //查记录详情
            ChairmanLetterBox letter = chairmanLetterMapper.selectOne(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.ID, id));
            //查发送人名字
            UserProfile user = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                    eq(UserProfile.ID, letter.getFromId()));
            letter.setFromName(user.getTruename());
            //查回复列表
            List<ChairmanLetterBox> replyList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    eq(ChairmanLetterBox.PARENTID, id).
                    orderByDesc(ChairmanLetterBox.CREATEDATE));
            //查附件
            List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETTYPE, "chairman").
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", letter);
            jsonObject.put("require", replyList);
            jsonObject.put("file", filesList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页-回复的评价
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
            return AjaxResult.error("评价失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("评价成功");
    }

    /**
     * 条件查询后台列表
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
            //生成所属组织架构名
            for (ChairmanLetterBackVO letter : backList){
                String orgName = toolUtils.getOrgName(letter.getOrgId());
                letter.setOrgName(orgName);
            }
            PageInfo pageInfo = new PageInfo<>(backList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 批量删除
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
                return AjaxResult.success("删除成功");
            }else {
                return AjaxResult.error("删除失败，删除不存在的数据或重复删除");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 批量回复
     * @param replyLetter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult replyLetter(ReplyLetterDO replyLetter) {
        try {
            //获取被回复信件
            List<ChairmanLetterBox> letterBoxList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                    in(ChairmanLetterBox.ID, replyLetter.getIdList()));
            //设置回复信息
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
            return AjaxResult.error("回复失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("回复成功");
    }

    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    @Override
    public AjaxResult uploadFile(MultipartFile file, Integer userId) {
        try {
//            String url = fastdfsClientUtil.uploadFile(file);
            String url = null;

            //保存文件信息至数据库
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

            return AjaxResult.success("上传成功", uploadFiles);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("上传失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteFile(Integer id) {
        try {
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));
            //暂时用uri，后续完善路径
//            boolean flag = fastdfsClientUtil.deleteFile(uploadFile.getUri());
//            if (! flag){
//                return AjaxResult.error("删除失败，请联系管理员");
//            }
            uploadFilesMapper.delete(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));

            return AjaxResult.success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param id
     * @param response
     * @return
     */
    @Override
    public AjaxResult downloadFile(Integer id, HttpServletResponse response) {
        try {
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, id));
            //暂时用uri，后续调整
//            fastdfsClientUtil.downLoadFile(response, uploadFile.getUri(), uploadFile.getFilename());

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("下载失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 批量导出
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

            //处理数据
            int i = 1;
            for (ManageExportDO export : exportList){
                //设置序号
                export.setId(i++);
                //设置组织机构名
                String orgName = toolUtils.getOrgName(export.getOrgId());
                export.setOrgName(orgName);
                //转换日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                export.setSendDate(sdf.format(export.getDate()));
                //查询附件
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
                //查询回复
                List<ChairmanLetterBox> replyList = chairmanLetterMapper.selectList(new QueryWrapper<ChairmanLetterBox>().
                        eq(ChairmanLetterBox.PARENTID, export.getLetterId()).
                        eq(ChairmanLetterBox.ISSHOW, 1));
                String reply = "";
                Integer j = 1;
                for (ChairmanLetterBox r : replyList){
                    reply += j.toString() + "、" + r.getContent() + "\n";
                    j++;
                }
                if (j != 1){
                    reply = reply.substring(0, reply.length() - 1);
                }
                export.setReply(reply);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "主席信箱" + sdf.format(date) + ".xlsx";

            //导出
            Workbook wb = getWorkbook(exportList, request);
            ExcelTool.export(wb, name, response);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
    }


    //导出表格处理
    private Workbook getWorkbook(List<ManageExportDO> exportList, HttpServletRequest request) throws Exception{
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/主席信箱导出模板.xls");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);

        //设置数据到excel
        int r = 1;
        Row row = null;
        for (ManageExportDO manage : exportList){
            String reply = "暂未回复";
            if (!"".equals(manage.getReply()) && manage.getReply() != null){
                reply = manage.getReply();
            }

            //创建一行
            row = sheet.createRow(r++);
            //组装一行数据
            ExcelTool.createCell(row, 0, style, manage.getId());
            ExcelTool.createCell(row, 1, style, manage.getFromName());
            ExcelTool.createCell(row, 2, style, manage.getOrgName());
            ExcelTool.createCell(row, 3, style, manage.getObject());
            ExcelTool.createCell(row, 4, style, manage.getSendDate());
            ExcelTool.createCell(row, 5, style, manage.getContent());
            ExcelTool.createCell(row, 7, style, manage.getToName());
            ExcelTool.createCell(row, 8, style, reply);
            //特殊处理附件超链接
            if (manage.getFiles() == null || manage.getFiles().size() == 0){
                Cell cell = row.createCell(6);
                cell.setCellStyle(style);
                cell.setCellValue("暂无附件");
            }else {
                for (FileUrlNameDO file : manage.getFiles()) {
                    Cell cell = row.createCell(6);
                    cell.setCellStyle(style);
                    cell.setCellFormula("HYPERLINK(\"" + file.getUrl() +"\",\"" + file.getName() + "\")");

                    row = sheet.createRow(r++);
                }
                r--;
                //开始行，结束行，开始列，结束列
                for (int j=0; j<=8; j++) {
                    //跳过附件所在的第6列
                    if (j == 6){
                        continue;
                    }
                    //只有一个附件不合并
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
     * 判断是否为超管，1为超管，2不为超管
     * @param userId
     * @return
     */
    private Integer getIsAdmin(Integer userId){
        Integer isAdmin = 2;

        //管理员
        SysRole sysRole = managerLetterMapper.getUserRole(userId);
        if (sysRole != null && "ROLE_SUPER_ADMIN".equals(sysRole.getRoleKey())){
            isAdmin = 1;
        }

        return isAdmin;
    }

    /**
     * 获取省主席/省副主席/省主席信箱管理人 对应部门节点
     * @param userId
     * @return
     */
    private List<String> getChairmanProvince(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        List<String> provinceInt = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            if ("省主席".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("省副主席".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("省主席信箱管理人".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }else {
                    province.add(comm);
                }
            }else if ("主席".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }
            }else if ("副主席".equals(comm.getPosition())){
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                }
            }
        }

        //省主席+省副主席+省主席信箱管理人
        for (OrgCommissioner comm : province){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在省级下被任命，则直接记录；否则层层寻找其省级节点
            List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
            for (Integer id : orgTreeIds){
                org = orgDao.selectOne(new QueryWrapper<Org>().
                        eq(Org.ID, id));
                if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                    provinceInt.add(org.getId().toString() + ".");
                    break;
                }
            }
        }

        return provinceInt;
    }

    /**
     * 获取市主席/市副主席/市主席信箱管理人 对应部门节点
     * @param userId
     * @return
     */
    private List<String> getChairmanCity(Integer userId){
        List<OrgCommissioner> city = new ArrayList<>();
        List<String> cityInt = new ArrayList<>();

        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            if ("市主席".equals(comm.getPosition())){
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("市副主席".equals(comm.getPosition())){
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("市主席信箱管理人".equals(comm.getPosition())){
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }else {
                    city.add(comm);
                }
            }else if ("主席".equals(comm.getPosition())){
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }
            }else if ("副主席".equals(comm.getPosition())){
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                }
            }
        }

        //市主席+市副主席+市主席信箱管理人
        for (OrgCommissioner comm : city){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在市级下被任命，则直接记录；否则层层寻找其市级节点
            List<Integer> orgTreeIds = toolUtils.getOrgTreeInt(org.getId());
            for (Integer id : orgTreeIds){
                org = orgDao.selectOne(new QueryWrapper<Org>().
                        eq(Org.ID, id));
                if ("市级分公司".equals(org.getOrgLevel())){
                    cityInt.add(org.getId().toString() + ".");
                    break;
                }
            }
        }

        return cityInt;
    }
}

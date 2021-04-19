package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.DO.FileUrlNameDO;
import com.ruoyi.project.democratic.entity.DO.ManageExportDO;
import com.ruoyi.project.democratic.entity.DO.ReplyLetterDO;
import com.ruoyi.project.democratic.entity.ManagerLetterBox;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.entity.VO.ManagerLetterBackVO;
import com.ruoyi.project.democratic.mapper.ManagerLetterMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.IManagerLetterService;
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
 * 总经理信箱 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-02
 */
@Service
public class ManagerLetterServiceImpl extends ServiceImpl<ManagerLetterMapper, ManagerLetterBox> implements IManagerLetterService {

    @Autowired
    private ManagerLetterMapper managerLetterMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;
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
     * 获取总经理信息
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getLetterMan(Integer userId) {
        try {
            //查询总经理id
            User user = userDao.selectOne(new QueryWrapper<User>().
                    eq(User.ID, userId));
            Map<String, List<Integer>> m1 = toolUtils.getManagerId(user.getOrgId());
            Integer province = m1.get("省总经理").size()==0 ? null : m1.get("省总经理").get(0);
            Integer city = m1.get("市总经理").size()==0 ? null : m1.get("市总经理").get(0);

            List<JSONObject> list = new ArrayList<>();
            if (province != null) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("position", "省总经理");
                jsonObject1.put("positionId", province);
                list.add(jsonObject1);
            }
            if (city != null) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("position", "市总经理");
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
     * 首页-发送信件
     * @param letterBox
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertManagerLetter(ManagerLetterBox letterBox) {
        try {
            letterBox.setCreateDate(new Date());
            save(letterBox);

            if (letterBox.getFileList() != null && letterBox.getFileList().size() > 0){
                List<UploadFiles> uploadFiles = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, letterBox.getFileList()));
                for (UploadFiles files : uploadFiles){
                    files.setTargetId(letterBox.getId());
                    files.setTargetType("manager");
                    uploadFilesMapper.updateById(files);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查询回复记录列表-首页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopManagerList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            //分页插件
            PageHelper.startPage(pageNum, pageSize);
            List<ManagerLetterBox> letterBoxList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.FROMID, userId).
                    eq(ManagerLetterBox.PARENTID, 0).
                    orderByDesc(ManagerLetterBox.CREATEDATE));
            PageInfo pageInfo = new PageInfo<>(letterBoxList, pageSize);

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
     * 根据id查详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getManagerDetailById(Integer id) {
        try {
            //查记录详情
            ManagerLetterBox letterBox = managerLetterMapper.selectOne(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.ID, id));
            //查发送人名字
            UserProfile user = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                    eq(UserProfile.ID, letterBox.getFromId()));
            letterBox.setFromName(user.getTruename());
            //查回复详情
            List<ManagerLetterBox> requireList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.PARENTID, id).
                    orderByAsc(ManagerLetterBox.CREATEDATE));
            //查附件
            List<UploadFiles> filesList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.TARGETTYPE, "manager").
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", letterBox);
            jsonObject.put("require", requireList);
            jsonObject.put("file", filesList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 评价回复
     * @param evaluate
     * @param evaluateContent
     * @param requireId
     * @return
     */
    @Override
    public AjaxResult evaluate(String evaluate, String evaluateContent, Integer requireId) {
        try {
            ManagerLetterBox letterBox = managerLetterMapper.selectOne(new QueryWrapper<ManagerLetterBox>().
                    eq(ManagerLetterBox.ID, requireId));
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
     * 条件查询
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
            List<String> provinceInt = getProvinceManager(userId);
            List<String> cityInt = getCityManager(userId);
            Integer isAdmin = getIsAdmin(userId);

            PageHelper.startPage(pageNum, pageSize);
            List<ManagerLetterBackVO> letterBoxList = managerLetterMapper.getLetterList(content, year, provinceInt, cityInt, isAdmin);
            //生成所属组织架构名
            for (ManagerLetterBackVO letter : letterBoxList){
                String orgName = toolUtils.getOrgName(letter.getOrgId());
                letter.setOrgName(orgName);
            }
            PageInfo pageInfo = new PageInfo<>(letterBoxList);


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
            List<ManagerLetterBox> letterBoxList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    in(ManagerLetterBox.ID, idList).
                    or().
                    in(ManagerLetterBox.PARENTID, idList));
            for (ManagerLetterBox letter : letterBoxList){
                letter.setIsShow(0);
            }

            boolean flag = updateBatchById(letterBoxList);
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
            //更新被回复的信件状态
            List<ManagerLetterBox> letterBoxList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                    in(ManagerLetterBox.ID, replyLetter.getIdList()));
            //设置批量回复的内容信息
            List<ManagerLetterBox> replyList = new ArrayList<>();

            for (ManagerLetterBox letter : letterBoxList){
                letter.setRealReply(replyLetter.getUserId());
                letter.setIsReply(1);

                ManagerLetterBox box = new ManagerLetterBox();
                box.setFromId(letter.getToId());
                box.setContent(replyLetter.getContent());
                box.setParentId(letter.getId());
                box.setToId(letter.getFromId());
                box.setCreateDate(new Date());
                box.setRealReply(replyLetter.getUserId());
                box.setObject(letter.getObject());
                replyList.add(box);
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
            //上传至服务器
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
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
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
            Integer isAdmin = getIsAdmin(userId);
            List<String> provinceInt = getProvinceManager(userId);
            List<String> cityInt = getCityManager(userId);
            List<ManageExportDO> exportList = managerLetterMapper.getExportData(content, year, provinceInt, cityInt, isAdmin);

            int i = 1;
            for (ManageExportDO export : exportList){
                //设置序号
                export.setId(i++);
                //转换日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                export.setSendDate(sdf.format(export.getDate()));
                //生成组织架构
                String orgName = toolUtils.getOrgName(export.getOrgId());
                export.setOrgName(orgName);
                //查询附件
                List<UploadFiles> uploadFiles = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.TARGETID, export.getLetterId()).
                        eq(UploadFiles.TARGETTYPE, "manager").
                        eq(UploadFiles.STATUS, "ok"));
                List<FileUrlNameDO> urlList = new ArrayList<>();
                for (UploadFiles upload : uploadFiles){
                    FileUrlNameDO fileDO = new FileUrlNameDO();
                    fileDO.setName(upload.getFilename());
                    fileDO.setUrl(upload.getUri());
                    urlList.add(fileDO);
                }
                export.setFiles(urlList);
                //查询回复
                List<ManagerLetterBox> letterList = managerLetterMapper.selectList(new QueryWrapper<ManagerLetterBox>().
                        eq(ManagerLetterBox.PARENTID, export.getLetterId()).
                        eq(ManagerLetterBox.ISSHOW, 1));
                String reply = "";
                Integer j = 1;
                for (ManagerLetterBox letter : letterList){
                    reply += j.toString() + "、" + letter.getContent() + "\n";
                    j++;
                }
                if (j != 1){
                    reply = reply.substring(0, reply.length() - 1);
                }
                export.setReply(reply);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "总经理信箱" + sdf.format(date) + ".xlsx";

            //导出
            Workbook wb = getWorkbook(exportList, request);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
        return null;
    }


    //导出表格处理
    private Workbook getWorkbook(List<ManageExportDO> exportList, HttpServletRequest request) throws Exception {
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/总经理信箱导出模板.xls");
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
     * 查询当前用户身份，1为超管，2不为超管
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
     * 获取省总经理对应部门节点
     * @param userId
     * @return
     */
    private List<String> getProvinceManager(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            if ("省总经理".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省总经理秘书".equals(comm.getPosition())){
                province.add(comm);
            }
        }

        List<String> provinceInt = new ArrayList<>();
        //省总经理+省总经理秘书
        for (OrgCommissioner comm : province){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在省级下被任命，则直接记录；否则层层寻找其省级节点
            if (org.getOrgLevel() == null || "".equals(org.getOrgLevel()) || "省级分公司或子公司".equals(org.getOrgLevel())){
                provinceInt.add(org.getId().toString() + ".");
            }else {
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
        }

        return provinceInt;
    }

    /**
     * 获取市总经理对应部门节点
     * @param userId
     * @return
     */
    private List<String> getCityManager(Integer userId){
        List<OrgCommissioner> province = new ArrayList<>();
        List<OrgCommissioner> city = new ArrayList<>();
        //判断身份
        List<OrgCommissioner> commList = orgCommissionerDao.selectList(new QueryWrapper<OrgCommissioner>().
                eq(OrgCommissioner.USERID, userId));
        for (OrgCommissioner comm : commList){
            if ("省总经理".equals(comm.getPosition())){
                province.add(comm);
            }else if ("省总经理秘书".equals(comm.getPosition())){
                province.add(comm);
            }else if ("市总经理".equals(comm.getPosition())){
                city.add(comm);
            }else if ("市总经理秘书".equals(comm.getPosition())){
                city.add(comm);
            }
        }

        List<String> cityInt = new ArrayList<>();
        //市总经理+市总经理秘书
        for (OrgCommissioner comm : city){
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, comm.getOrgId()));
            //如果本身是在市级下被任命，则直接记录；否则层层寻找其市级节点
            if ("市级分公司".equals(org.getOrgLevel())){
                cityInt.add(org.getId().toString() + ".");
            }else {
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
        }

        return cityInt;
    }
}

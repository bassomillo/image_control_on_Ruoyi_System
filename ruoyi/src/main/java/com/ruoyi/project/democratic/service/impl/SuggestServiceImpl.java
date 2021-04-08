package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.DO.FileUrlNameDO;
import com.ruoyi.project.democratic.entity.DO.SuggestExportDO;
import com.ruoyi.project.democratic.entity.SuggestBox;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.entity.VO.SuggestBackVO;
import com.ruoyi.project.democratic.mapper.SuggestMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.SuggestService;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */
@Service
public class SuggestServiceImpl extends ServiceImpl<SuggestMapper, SuggestBox> implements SuggestService {

    @Autowired
    private SuggestMapper suggestMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;

    @Resource
    private FastdfsClientUtil fastdfsClientUtil;
    @Resource
    private ToolUtils toolUtils;

    /**
     * 发送建言-首页
     * @param suggest
     * @param fileList
     * @return
     */
    @Override
    public AjaxResult insertSuggest(SuggestBox suggest, List<Integer> fileList) {
        try {
            suggest.setCreateDate(new Date());
            save(suggest);

            if (fileList != null && fileList.size() != 0){
                List<UploadFiles> files = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, fileList));
                for (UploadFiles f : files){
                    f.setTargetId(suggest.getId());
                    f.setTargetType("suggest");
                    uploadFilesMapper.updateById(f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查找回复记录列表-首页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopSuggestList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            //分页插件
            PageHelper.startPage(pageNum, pageSize);
            QueryWrapper<SuggestBox> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SuggestBox.CREATEUSERID, userId).
                    eq(SuggestBox.PARENTID, 0).
                    orderByDesc(SuggestBox.CREATEDATE);
            List<SuggestBox> suggestList = suggestMapper.selectList(queryWrapper);
            PageInfo pageInfo = new PageInfo<>(suggestList, pageSize);

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
     * 根据id查询建言详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getSuggestDetailById(Integer id) {
        try {
            QueryWrapper<SuggestBox> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SuggestBox.ID, id);
            //查询记录详情
            SuggestBox suggest = suggestMapper.selectOne(queryWrapper);
            //查询回复记录
            List<SuggestBox> requireList = suggestMapper.selectList(new QueryWrapper<SuggestBox>().
                    eq(SuggestBox.PARENTID, id).
                    orderByAsc(SuggestBox.CREATEDATE));
            //查询附件
            List<UploadFiles> fileList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.TARGETTYPE, "suggest").
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", suggest);
            jsonObject.put("require", requireList);
            jsonObject.put("file", fileList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 条件查询建言列表-后台
     * @param content
     * @param year
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackSuggestList(String content, Integer year, Integer pageNum, Integer pageSize) {
        try {
            //转义%
            content = Str.fuzzyQuery(content);

            PageHelper.startPage(pageNum, pageSize);
            List<SuggestBackVO> suggestList = suggestMapper.getBackSuggestList(content, year==null ? null : year.toString());
            for (SuggestBackVO suggest : suggestList){
                //生成组织架构
                String orgName = toolUtils.getOrgName(suggest.getOrgId());
                suggest.setOrgName(orgName);
            }
            PageInfo pageInfo = new PageInfo<>(suggestList, pageSize);

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
    public AjaxResult deleteSuggest(List<Integer> idList) {
        try {
            List<SuggestBox> suggestList = suggestMapper.selectList(new QueryWrapper<SuggestBox>().
                    in(SuggestBox.ID, idList).
                    or().
                    in(SuggestBox.PARENTID, idList));
            for (SuggestBox suggest : suggestList){
                suggest.setIsShow(0);
            }
            boolean flag = updateBatchById(suggestList);

            if (flag){
                return AjaxResult.success("删除成功");
            }else {
                return AjaxResult.error("删除失败，请联系管理员");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    @Override
    public AjaxResult replySuggest(String content, Integer suggestId, Integer userId) {
        try {
            //插入回复
            SuggestBox suggestBox = new SuggestBox();
            suggestBox.setCreateDate(new Date());
            suggestBox.setContent(content);
            suggestBox.setParentId(suggestId);
            suggestBox.setCreateUserId(userId);
            save(suggestBox);

            //设置记录回复字段状态
            SuggestBox suggestBox1 = suggestMapper.selectOne(new QueryWrapper<SuggestBox>().
                    eq(SuggestBox.ID, suggestId));
            suggestBox1.setReplied(1);
            updateById(suggestBox1);

            return AjaxResult.success("回复成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("回复失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 上传首页建言文件
     * @param file
     * @param userId
     * @return
     */
    @Override
    public AjaxResult uploadSuggestFile(MultipartFile file, Integer userId) {
        try {
            //上传文件至服务器
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
     * 删除上传的文件
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
     * @param response
     * @param request
     */
    @Override
    public AjaxResult export(String content, Integer year, HttpServletResponse response, HttpServletRequest request) {
        try {
            //转义
            content = Str.fuzzyQuery(content);
            List<SuggestExportDO> exportList = suggestMapper.getExportData(content, year);

            int i = 1;
            for (SuggestExportDO suggest : exportList){
                suggest.setId(i++);
                //转换日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                suggest.setSendDate(sdf.format(suggest.getDate()));
                //生成组织架构
                String orgName = toolUtils.getOrgName(suggest.getOrgId());
                suggest.setOrgName(orgName);
                //查询附件
                List<UploadFiles> uploadFiles = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.TARGETID, suggest.getSuggestId()).
                        eq(UploadFiles.TARGETTYPE, "suggest").
                        eq(UploadFiles.STATUS, "ok"));
                List<FileUrlNameDO> urlList = new ArrayList<>();
                for (UploadFiles upload : uploadFiles){
                    FileUrlNameDO fileDO = new FileUrlNameDO();
                    fileDO.setName(upload.getFilename());
                    fileDO.setUrl(upload.getUri());
                    urlList.add(fileDO);
                }
                suggest.setFiles(urlList);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "建言献策" + sdf.format(date) + ".xlsx";

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
    private Workbook getWorkbook(List<SuggestExportDO> exportList, HttpServletRequest request) throws Exception {

        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/建言献策导出模板.xls");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);

        //设置数据到excel
        int r = 2;
        Row row = null;
        for (SuggestExportDO suggest : exportList){
            //创建一行
            row = sheet.createRow(r++);
            //组装一行数据
            ExcelTool.createCell(row, 0, style, suggest.getId());
            ExcelTool.createCell(row, 1, style, suggest.getUserName());
            ExcelTool.createCell(row, 2, style, suggest.getOrgName());
            ExcelTool.createCell(row, 3, style, suggest.getSendDate());
            ExcelTool.createCell(row, 4, style, suggest.getType());
            ExcelTool.createCell(row, 5, style, suggest.getContent());
            //特殊处理附件超链接
            if (suggest.getFiles() == null || suggest.getFiles().size() == 0){
                Cell cell = row.createCell(6);
                cell.setCellStyle(style);
                cell.setCellValue("暂无附件");
            }else {
                for (FileUrlNameDO file : suggest.getFiles()) {
                    Cell cell = row.createCell(6);
                    cell.setCellStyle(style);
                    cell.setCellFormula("HYPERLINK(\"" + file.getUrl() +"\",\"" + file.getName() + "\")");

                    row = sheet.createRow(r++);
                }
                r--;
                //开始行，结束行，开始列，结束列
                for (int j=0; j<=5; j++) {
                    //只有一个附件不合并
                    if (suggest.getFiles().size() > 1) {
                        CellRangeAddress region = new CellRangeAddress(r - suggest.getFiles().size(),
                                r - 1, j, j);
                        sheet.addMergedRegion(region);
                    }
                }
            }
        }

        return wb;
    }
}

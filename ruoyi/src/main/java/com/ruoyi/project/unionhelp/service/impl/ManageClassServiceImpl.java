package com.ruoyi.project.unionhelp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.ChairmanLetterBox;
import com.ruoyi.project.democratic.entity.DO.FileUrlNameDO;
import com.ruoyi.project.democratic.entity.DO.ManageExportDO;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.unionhelp.entity.DO.ManageClassExportDO;
import com.ruoyi.project.unionhelp.entity.ManageClass;
import com.ruoyi.project.unionhelp.entity.VO.ManageClassVO;
import com.ruoyi.project.unionhelp.entity.VO.ManageClassVO1;
import com.ruoyi.project.unionhelp.mapper.ManageClassMapper;
import com.ruoyi.project.unionhelp.service.IManageClassService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 托管班 服务实现类
 * </p>
 *
 * @author zm
 * @since 2021-04-25
 */
@Service
public class ManageClassServiceImpl extends ServiceImpl<ManageClassMapper, ManageClass> implements IManageClassService {
    @Autowired
    ManageClassMapper manageClassMapper;

    @Autowired
    UploadFilesMapper uploadFilesMapper;

    @Resource
    FastdfsClientUtil fastdfsClientUtil;

    /**
     * 创建托管班
     * @param manageClass
     * @return
     */
    @Override
    public AjaxResult InsertManageClass(ManageClass manageClass) {
        try {
            manageClassMapper.InsertManageClass(manageClass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    /**
     * 上传文件
     * @param userId
     * @param file
     * @param targetId
     * @return
     */
    @Override
    public AjaxResult UploadFile(Integer userId, MultipartFile file, Integer targetId){
        try {
//            String url = fastdfsClientUtil.uploadFile(file);
            String url = null;

            //保存文件信息至数据库
            Integer size = (int) file.getSize();
            UploadFiles uploadFiles = new UploadFiles();
            uploadFiles.setTargetId(targetId);
            uploadFiles.setTargetType("manage_class");
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
    public AjaxResult DeleteFile(Integer id) {
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
     * 条件查询
     * @param company
     * @param trueName
     * @param mobile
     * @return
     */
    @Override
    public AjaxResult SelectManageClasses(String company, String trueName, String mobile, Integer pagesize, Integer page) {
        List<ManageClassVO> manageClassVOList = manageClassMapper.SelectManageClasses(company, trueName, mobile, pagesize, (page-1)*pagesize);
        Integer manageClassNum = manageClassMapper.GetManageClassNum(company, trueName, mobile);
        Map<String, Object> map = new HashMap<>();
        map.put("list", manageClassVOList);
        map.put("count", manageClassNum);
        return AjaxResult.success("操作成功", map);
    }


    /**
     * 根据id得到托管班详细信息
     * @param id
     * @return
     */
    @Override
    public AjaxResult SearchManageClassById(Integer id) {
        try {
            ManageClassVO1 manageClassVO1 = manageClassMapper.SearchManageClassById(id);
            return AjaxResult.success("操作成功", manageClassVO1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 展示所有托管班信息
     * @param pagesize
     * @param page
     * @return
     */
    @Override
    public AjaxResult GetManageClasses(Integer pagesize, Integer page) {
        try {
            List<ManageClassVO> manageClassVOList = manageClassMapper.GetManageClasses(pagesize, (page-1)*pagesize);
            Integer manageClassNum = manageClassMapper.GetManageClassNum(null, null,null);
            Map<String, Object> map = new HashMap<>();
            map.put("list", manageClassVOList);
            map.put("count", manageClassNum);
            return AjaxResult.success("操作成功", map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 编辑托管班
     * @param manageClass
     * @return
     */
    @Override
    public AjaxResult UpdateManageClass(ManageClass manageClass) {
        try {
            manageClassMapper.UpdateManageClass(manageClass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    /**
     * 批量导出
     */
    @Override
    public AjaxResult export(HttpServletResponse response, HttpServletRequest request) {
        try {

            List<ManageClassExportDO> classExportDOList = manageClassMapper.GetExportData();

            //处理数据
            int i = 1;
            for (ManageClassExportDO export : classExportDOList){

                //转换日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Integer timestamp = export.getStartTime();
                long time =timestamp.longValue();
                String date = sdf.format(new Date(time * 1000L));//转成string类型
                //Date startDate = sdf.parse(date);//转成date类型
                export.setOpenTime(date);

                Integer timestamp1 = export.getStartTime();
                long time1 =timestamp1.longValue();
                String date1 = sdf.format(new Date(time1 * 1000L));//转成string类型
                //Date endDate = sdf.parse(date1);//转成date类型
                export.setCloseTime(date1);

            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "托管班" + sdf.format(date) + ".xls";

            //导出
            Workbook wb = getWorkbook(classExportDOList, request);
            ExcelTool.export(wb, name, response);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
    }


    //导出表格处理
    private Workbook getWorkbook(List<ManageClassExportDO> exportList, HttpServletRequest request) throws Exception{
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/托管班导出模板.xls");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);

        //设置数据到excel
        int r = 2;
        Row row = null;
        for (ManageClassExportDO manage : exportList){

            //创建一行
            row = sheet.createRow(r++);
            //组装一行数据
            ExcelTool.createCell(row, 0, style, manage.getCompany());
            ExcelTool.createCell(row, 1, style, manage.getTrueName());
            ExcelTool.createCell(row, 2, style, manage.getMobile());
            ExcelTool.createCell(row, 3, style, manage.getOpenTime());
            ExcelTool.createCell(row, 4, style, manage.getCloseTime());
            ExcelTool.createCell(row, 5, style, manage.getClassNum());
            ExcelTool.createCell(row, 6, style, manage.getWillNum());
            ExcelTool.createCell(row, 7, style, manage.getMoney());
            ExcelTool.createCell(row, 8, style, manage.getJoinNum());
            ExcelTool.createCell(row, 9, style, manage.getHoldForm());
            ExcelTool.createCell(row, 10, style, manage.getTeacherSource());
            ExcelTool.createCell(row, 11, style, manage.getSite());
            ExcelTool.createCell(row, 12, style, manage.getMoneySource());
            ExcelTool.createCell(row, 13, style, manage.getOutNewNum());
            ExcelTool.createCell(row, 14, style, manage.getOtherNewNum());
            ExcelTool.createCell(row, 15, style, manage.getInNewNum());
            ExcelTool.createCell(row, 16, style, manage.getContent());
            ExcelTool.createCell(row, 17, style, manage.getRemark());

        }

        return wb;
    }

}

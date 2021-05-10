package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.DO.FileUrlNameDO;
import com.ruoyi.project.democratic.entity.DO.ManageExportDO;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.mapper.StaffServiceSympathyMapper;
import com.ruoyi.project.monitor.service.StaffServiceSympathyService;
import com.ruoyi.project.tool.ExcelTool;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StaffServiceSympathyServiceImpl implements StaffServiceSympathyService {
    @Autowired
    private StaffServiceSympathyMapper staffServiceSympathyMapper;
    /*
     * String to List
     */
    @Override
    public List<String> StringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }
    /*
     * List to String
     */
    @Override
    public  String ListToString(@NonNull CharSequence delimiter, @NonNull Iterable tokens) {
        final Iterator<?> it = tokens.iterator();
        if (!it.hasNext()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    @Override
    public AjaxResult StaffServiceSympathySetting(StaffServiceSympathyRequire staffServiceSympathyRequire) {
        try {
            Date time = new Date();
            StaffServiceSympathy staffServiceSympathy = new StaffServiceSympathy();
            staffServiceSympathy.setUpdatedTime(time);
            staffServiceSympathy.setSympathyCost(staffServiceSympathyRequire.getSympathyCost());
            staffServiceSympathy.setSympathyTime(staffServiceSympathyRequire.getSympathyTime());
            staffServiceSympathy.setSympathyNumber(staffServiceSympathyRequire.getSympathyNumber());
            staffServiceSympathy.setCreatedUserId(staffServiceSympathyRequire.getCreatedUserId());
            staffServiceSympathy.setExpenseOrgId(staffServiceSympathyRequire.getExpenseOrgId());
            staffServiceSympathy.setOrgId(staffServiceSympathyRequire.getOrgId());
            staffServiceSympathy.setRemark(staffServiceSympathyRequire.getRemark());
            staffServiceSympathy.setType(staffServiceSympathyRequire.getType());
            String sympathyType = ListToString(",", staffServiceSympathyRequire.getSympathyTypeList());
            staffServiceSympathy.setSympathyType(sympathyType);
            String coverType = ListToString(",", staffServiceSympathyRequire.getCoverTypeList());
            staffServiceSympathy.setCoverType(coverType);
            String fundsSources = ListToString(",", staffServiceSympathyRequire.getFundsSourcesList());
            staffServiceSympathy.setFundsSources(fundsSources);
            staffServiceSympathyMapper.StaffServiceSympathySetting(staffServiceSympathy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathyUpdate(StaffServiceSympathyRequire staffServiceSympathyRequire) {
        try {
            Date time = new Date();
            StaffServiceSympathy staffServiceSympathy = new StaffServiceSympathy();
            staffServiceSympathy.setUpdatedTime(time);
            staffServiceSympathy.setSympathyCost(staffServiceSympathyRequire.getSympathyCost());
            staffServiceSympathy.setSympathyTime(staffServiceSympathyRequire.getSympathyTime());
            staffServiceSympathy.setSympathyNumber(staffServiceSympathyRequire.getSympathyNumber());
            staffServiceSympathy.setCreatedUserId(staffServiceSympathyRequire.getCreatedUserId());
            staffServiceSympathy.setExpenseOrgId(staffServiceSympathyRequire.getExpenseOrgId());
            staffServiceSympathy.setOrgId(staffServiceSympathyRequire.getOrgId());
            staffServiceSympathy.setRemark(staffServiceSympathyRequire.getRemark());
            staffServiceSympathy.setType(staffServiceSympathyRequire.getType());
            staffServiceSympathy.setId(staffServiceSympathyRequire.getId());
            String sympathyType = ListToString(",", staffServiceSympathyRequire.getSympathyTypeList());
            staffServiceSympathy.setSympathyType(sympathyType);
            String coverType = ListToString(",", staffServiceSympathyRequire.getCoverTypeList());
            staffServiceSympathy.setCoverType(coverType);
            String fundsSources = ListToString(",", staffServiceSympathyRequire.getFundsSourcesList());
            staffServiceSympathy.setFundsSources(fundsSources);
            staffServiceSympathyMapper.StaffServiceSympathyUpdate(staffServiceSympathy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathySearch(StaffServiceSympathySearch staffServiceSympathySearch) {

        Integer submitStatus = staffServiceSympathySearch.getSubmitStatus();
        int createdUserOrg = staffServiceSympathySearch.getCreatedUserOrg();
        int expenseOrgId = staffServiceSympathySearch.getExpenseOrgId();
        int orgId = staffServiceSympathySearch.getOrgId();
        Integer status = staffServiceSympathySearch.getStatus();
        String type = staffServiceSympathySearch.getType();
        List<Integer> sons = FindSon(staffServiceSympathySearch.getCreatedUserOrg());
        Integer pageSize = staffServiceSympathySearch.getPageSize();
        Integer index = staffServiceSympathySearch.getIndex();

        List<StaffServiceSympathy> staffServiceSympathyList = staffServiceSympathyMapper.StaffServiceSympathySearch(expenseOrgId, orgId, createdUserOrg, submitStatus, status, type, sons,pageSize,(index-1)*pageSize);
        List<StaffServiceSympathyReview> staffServiceSympathyReviewList = new ArrayList<>();
        for(StaffServiceSympathy staffServiceSympathy: staffServiceSympathyList){
            StaffServiceSympathyReview staffServiceSympathyReview = new StaffServiceSympathyReview();
            staffServiceSympathyReview.setId(staffServiceSympathy.getId());
            staffServiceSympathyReview.setSympathyCost(staffServiceSympathy.getSympathyCost());
            staffServiceSympathyReview.setSympathyTime(staffServiceSympathy.getSympathyTime());
            staffServiceSympathyReview.setSympathyNumber(staffServiceSympathy.getSympathyNumber());
            staffServiceSympathyReview.setUpdatedTime(staffServiceSympathy.getUpdatedTime());
            staffServiceSympathyReview.setCreatedUserId(staffServiceSympathy.getCreatedUserId());
            staffServiceSympathyReview.setType(staffServiceSympathy.getType());
            staffServiceSympathyReview.setSubmitTime(staffServiceSympathy.getSubmitTime());
            staffServiceSympathyReview.setSubmitStatus(staffServiceSympathy.getSubmitStatus());
            staffServiceSympathyReview.setRemark(staffServiceSympathy.getRemark());
            List<String> sympathyType = StringToList(staffServiceSympathy.getSympathyType());
            List<String> fundsSources = StringToList(staffServiceSympathy.getFundsSources());
            List<String> coverType = StringToList(staffServiceSympathy.getCoverType());
            staffServiceSympathyReview.setSympathyTypeList(sympathyType);
            staffServiceSympathyReview.setFundsSourcesList(fundsSources);
            staffServiceSympathyReview.setCoverTypeList(coverType);
            String expenseOrgName = staffServiceSympathyMapper.StaffServiceSympathyGetOrg(staffServiceSympathy.getExpenseOrgId());
            String orgName = staffServiceSympathyMapper.StaffServiceSympathyGetOrg(staffServiceSympathy.getOrgId());
            staffServiceSympathyReview.setOrgName(orgName);
            staffServiceSympathyReview.setExpenseOrgName(expenseOrgName);
            String createName = staffServiceSympathyMapper.StaffServiceSympathyFindCreaterName(staffServiceSympathy.getCreatedUserId());
            staffServiceSympathyReview.setCreateName(createName);
            staffServiceSympathyReview.setExpenseOrgId(staffServiceSympathy.getExpenseOrgId());
            staffServiceSympathyReview.setCoverTypeList1(staffServiceSympathy.getCoverType());
            staffServiceSympathyReview.setFundsSourcesList1(staffServiceSympathy.getFundsSources());
            staffServiceSympathyReview.setSympathyTypeList1(staffServiceSympathy.getSympathyType());
            SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
            if(staffServiceSympathy.getSubmitTime() != null) {
                staffServiceSympathyReview.setSubmitTime1(sformat.format(staffServiceSympathy.getSubmitTime()));
            }
            if(staffServiceSympathy.getSubmitTime() == null) {
                staffServiceSympathyReview.setSubmitTime1(null);
            }
            if(staffServiceSympathy.getSympathyTime() != null) {
                staffServiceSympathyReview.setSympathyTime1(sformat.format(staffServiceSympathy.getSympathyTime()));
            }
            if(staffServiceSympathy.getSympathyTime() == null) {
                staffServiceSympathyReview.setSympathyTime1(null);
            }
            staffServiceSympathyReviewList.add(staffServiceSympathyReview);
        }
        int count = staffServiceSympathyMapper.StaffServiceSympathyCount(expenseOrgId, orgId, createdUserOrg, submitStatus, status, type, sons);
        Map<String, Object> map = new HashMap<>();
        map.put("list", staffServiceSympathyReviewList);
        map.put("count", count);

        return AjaxResult.success("提交成功", map);
    }

    @Override
    public AjaxResult export(StaffServiceSympathySearch1 staffServiceSympathySearch1, HttpServletResponse response, HttpServletRequest request){
        try {
            Integer submitStatus = staffServiceSympathySearch1.getSubmitStatus();
            int createdUserOrg = staffServiceSympathySearch1.getCreatedUserOrg();
            int expenseOrgId = staffServiceSympathySearch1.getExpenseOrgId();
            int orgId = staffServiceSympathySearch1.getOrgId();
            Integer status = staffServiceSympathySearch1.getStatus();
            String type = staffServiceSympathySearch1.getType();
            List<Integer> sons = FindSon(staffServiceSympathySearch1.getCreatedUserOrg());

            List<StaffServiceSympathy> staffServiceSympathyList = staffServiceSympathyMapper.StaffServiceSympathySearch1(expenseOrgId, orgId, createdUserOrg, submitStatus, status, type, sons);
            List<StaffServiceSympathyReview> staffServiceSympathyReviewList = new ArrayList<>();
            for (StaffServiceSympathy staffServiceSympathy : staffServiceSympathyList) {
                StaffServiceSympathyReview staffServiceSympathyReview = new StaffServiceSympathyReview();
                staffServiceSympathyReview.setId(staffServiceSympathy.getId());
                staffServiceSympathyReview.setSympathyCost(staffServiceSympathy.getSympathyCost());
                staffServiceSympathyReview.setSympathyTime(staffServiceSympathy.getSympathyTime());
                staffServiceSympathyReview.setSympathyNumber(staffServiceSympathy.getSympathyNumber());
                staffServiceSympathyReview.setUpdatedTime(staffServiceSympathy.getUpdatedTime());
                staffServiceSympathyReview.setCreatedUserId(staffServiceSympathy.getCreatedUserId());
                staffServiceSympathyReview.setType(staffServiceSympathy.getType());
                staffServiceSympathyReview.setSubmitTime(staffServiceSympathy.getSubmitTime());
                staffServiceSympathyReview.setSubmitStatus(staffServiceSympathy.getSubmitStatus());
                staffServiceSympathyReview.setRemark(staffServiceSympathy.getRemark());
                List<String> sympathyType = StringToList(staffServiceSympathy.getSympathyType());
                List<String> fundsSources = StringToList(staffServiceSympathy.getFundsSources());
                List<String> coverType = StringToList(staffServiceSympathy.getCoverType());
                staffServiceSympathyReview.setSympathyTypeList(sympathyType);
                staffServiceSympathyReview.setFundsSourcesList(fundsSources);
                staffServiceSympathyReview.setCoverTypeList(coverType);
                String expenseOrgName = staffServiceSympathyMapper.StaffServiceSympathyGetOrg(staffServiceSympathy.getExpenseOrgId());
                String orgName = staffServiceSympathyMapper.StaffServiceSympathyGetOrg(staffServiceSympathy.getOrgId());
                staffServiceSympathyReview.setOrgName(orgName);
                staffServiceSympathyReview.setExpenseOrgName(expenseOrgName);
                String createName = staffServiceSympathyMapper.StaffServiceSympathyFindCreaterName(staffServiceSympathy.getCreatedUserId());
                staffServiceSympathyReview.setCreateName(createName);
                staffServiceSympathyReview.setCoverTypeList1(staffServiceSympathy.getCoverType());
                staffServiceSympathyReview.setFundsSourcesList1(staffServiceSympathy.getFundsSources());
                staffServiceSympathyReview.setSympathyTypeList1(staffServiceSympathy.getSympathyType());
                SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
                if(staffServiceSympathy.getSubmitTime() != null) {
                    staffServiceSympathyReview.setSubmitTime1(sformat.format(staffServiceSympathy.getSubmitTime()));
                }
                if(staffServiceSympathy.getSubmitTime() == null) {
                    staffServiceSympathyReview.setSubmitTime1(null);
                }
                if(staffServiceSympathy.getSympathyTime() != null) {
                    staffServiceSympathyReview.setSympathyTime1(sformat.format(staffServiceSympathy.getSympathyTime()));
                }
                if(staffServiceSympathy.getSympathyTime() == null) {
                    staffServiceSympathyReview.setSympathyTime1(null);
                }
                staffServiceSympathyReviewList.add(staffServiceSympathyReview);
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "慰问记录" + sdf.format(date) + ".xlsx";
            //导出
            Workbook wb = getWorkbook(staffServiceSympathyReviewList, request);
            ExcelTool.export(wb, name, response);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }

    }

    //导出表格处理
    private Workbook getWorkbook(List<StaffServiceSympathyReview> exportList, HttpServletRequest request) throws Exception {
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/慰问记录导出模板.xlsx");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);

        //设置数据到excel
        int r = 1;
        Row row = null;
        for (StaffServiceSympathyReview manage : exportList) {
//            String reply = "暂未回复";
//            if (!"".equals(manage.getReply()) && manage.getReply() != null) {
//                reply = manage.getReply();
//            }

            //创建一行
            row = sheet.createRow(r++);
            //组装一行数据
            ExcelTool.createCell(row, 0, style, manage.getExpenseOrgName());
            ExcelTool.createCell(row, 1, style, manage.getSympathyTypeList1());
            ExcelTool.createCell(row, 2, style, manage.getSympathyCost());
            ExcelTool.createCell(row, 3, style, manage.getFundsSourcesList1());
            ExcelTool.createCell(row, 4, style, manage.getSympathyNumber());
            ExcelTool.createCell(row, 5, style, manage.getCoverTypeList1());
            ExcelTool.createCell(row, 6, style, manage.getSympathyTime1());
            ExcelTool.createCell(row, 7, style, manage.getOrgName());
            ExcelTool.createCell(row, 8, style, manage.getSubmitTime1());
            ExcelTool.createCell(row, 9, style, manage.getCreateName());
            ExcelTool.createCell(row, 10, style, manage.getRemark());

        }
        return wb;
    }

    @Override
    public List<Integer> FindSon(int id){
        List<Integer> Sons = new ArrayList<>();
        List<StaffServiceSympathyFindSon> staffServiceSympathyFindSonList = staffServiceSympathyMapper.StaffServiceSympathyFindSon();
        for(StaffServiceSympathyFindSon staffServiceSympathyFindSon : staffServiceSympathyFindSonList){
            String orgCode = staffServiceSympathyFindSon.getOrgCode();
            int orgID = staffServiceSympathyFindSon.getId();
//            orgCode = orgCode.substring(0, orgCode.length()-1);
//            String orgCodeList[] = orgCode.split(".");
            String idString = String.valueOf(id);
            if(orgCode.contains(idString)){
                Sons.add(orgID);
            }
        }
        return Sons;
    }

    @Override
    public AjaxResult StaffServiceSympathyDelete(List<Integer> listId) {
        try {
            for (int id : listId) {
                staffServiceSympathyMapper.StaffServiceSympathyDelete(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult StaffServiceSympathySubmit(int id) {
        try {
            Date time = new Date();
            staffServiceSympathyMapper.StaffServiceSympathySubmit(id, time, time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("提交成功");
    }
}

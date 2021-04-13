package com.ruoyi.project.monitor.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.*;
import com.ruoyi.project.monitor.mapper.StaffServiceSympathyMapper;
import com.ruoyi.project.monitor.service.StaffServiceSympathyService;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Integer> sons = FindSon(staffServiceSympathySearch.getCreatedUserOrg());

        List<StaffServiceSympathy> staffServiceSympathyList = staffServiceSympathyMapper.StaffServiceSympathySearch(expenseOrgId, orgId, createdUserOrg, submitStatus, status, sons);
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

            staffServiceSympathyReviewList.add(staffServiceSympathyReview);
        }
        int count = staffServiceSympathyMapper.StaffServiceSympathyCount(expenseOrgId, orgId, createdUserOrg, submitStatus, status, sons);
        Map<String, Object> map = new HashMap<>();
        map.put("list", staffServiceSympathyReviewList);
        map.put("count", count);

        return AjaxResult.success("提交成功", map);
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

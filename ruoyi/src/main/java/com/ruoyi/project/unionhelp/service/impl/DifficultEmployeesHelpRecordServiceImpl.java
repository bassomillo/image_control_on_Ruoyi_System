package com.ruoyi.project.unionhelp.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.monitor.domain.StaffServiceSympathy;
import com.ruoyi.project.monitor.domain.StaffServiceSympathyReview;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecord;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordGet;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearch;
import com.ruoyi.project.unionhelp.entity.DifficultEmployeesHelpRecordSearchRequire;
import com.ruoyi.project.unionhelp.mapper.DifficultEmployeesHelpRecordDao;
import com.ruoyi.project.unionhelp.mapper.UserProfileDao1;
import com.ruoyi.project.unionhelp.service.IDifficultEmployeesHelpRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 困难员工帮扶记录表 服务实现类
 * </p>
 *
 * @author crl
 * @since 2021-04-14
 */
@Service
public class DifficultEmployeesHelpRecordServiceImpl extends ServiceImpl<DifficultEmployeesHelpRecordDao, DifficultEmployeesHelpRecord> implements IDifficultEmployeesHelpRecordService {
    @Autowired
    private DifficultEmployeesHelpRecordDao difficultEmployeesHelpRecordDao;
    @Override
    public AjaxResult searchHelper(DifficultEmployeesHelpRecordSearchRequire difficultEmployeesHelpRecordSearchRequire){

        Integer pageSize = difficultEmployeesHelpRecordSearchRequire.getPageSize();
        Integer index = difficultEmployeesHelpRecordSearchRequire.getIndex();
        String searchUser = difficultEmployeesHelpRecordSearchRequire.getSearchUser();
        String searchHelper = difficultEmployeesHelpRecordSearchRequire.getSearchHelper();
        if(searchUser != null) {
            String searchUser1 = searchUser.replace("%", "/%");
        }
        if(searchHelper != null) {
            String searchHelper1 = searchHelper.replace("%", "/%");
        }
        List<DifficultEmployeesHelpRecordGet> difficultEmployeesHelpRecordGetList = difficultEmployeesHelpRecordDao.searchHelper(pageSize,(index-1)*pageSize,searchUser,searchHelper);
        List<DifficultEmployeesHelpRecordSearch> difficultEmployeesHelpRecordSearchList = new ArrayList<>();
        for(DifficultEmployeesHelpRecordGet difficultEmployeesHelpRecordGet: difficultEmployeesHelpRecordGetList){
            DifficultEmployeesHelpRecordSearch difficultEmployeesHelpRecordSearch = new DifficultEmployeesHelpRecordSearch();
            difficultEmployeesHelpRecordSearch.setDifficultEmployeesHelpRecordGet(difficultEmployeesHelpRecordGet);
            String time1 = String.valueOf(difficultEmployeesHelpRecordGet.getHelpTime());
            String time = time1.concat("000");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String helpTime = sdf.format(new Date(Long.parseLong(time)));
            difficultEmployeesHelpRecordSearch.setHelpTimeStringFormate(helpTime);
            difficultEmployeesHelpRecordSearchList.add(difficultEmployeesHelpRecordSearch);
        }
        Integer count = difficultEmployeesHelpRecordSearchList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("list", difficultEmployeesHelpRecordSearchList);
        map.put("count", count);
        return AjaxResult.success("提交成功", map);
    }
}

package com.ruoyi.project.unionhelp.service.impl;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.UserProfile1;
import com.ruoyi.project.unionhelp.mapper.UserProfileDao1;
import com.ruoyi.project.unionhelp.service.IUserProfileService1;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.unionhelp.vo.SearchUser;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crl
 * @since 2021-04-21
 */
@Service
public class UserProfileServiceImpl1 extends ServiceImpl<UserProfileDao1, UserProfile1> implements IUserProfileService1 {
    @Autowired
    private UserProfileDao1 userProfileDao1;
    @Override
    public List<Userinfo> search(SearchUser searchUser){
        Integer pageSize = searchUser.getPageSize();
        Integer index = searchUser.getIndex();
        String searchContent = searchUser.getSearchContent();
        List<Userinfo> userinfoList = new ArrayList<>();
        if(searchContent != null) {
            String searchContent1 = searchContent.replace("%", "/%");
            userinfoList = userProfileDao1.searchUser(pageSize, (index-1)*pageSize, searchContent1);
        }
        else{
            String searchContent1 = null;
            userinfoList = userProfileDao1.searchUser(pageSize, (index-1)*pageSize, searchContent1);
        }

        return userinfoList;

    }


}

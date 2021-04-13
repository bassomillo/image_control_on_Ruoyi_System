package com.ruoyi.project.union.mapper;

import com.ruoyi.project.org.pojo.OrgUserSearchPojo;
import com.ruoyi.project.org.pojo.UserShowPojo;
import com.ruoyi.project.union.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.union.pojo.UserSearchPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    List<Integer> searchUser(UserSearchPojo userSearchPojo);

    List<UserShowPojo> searchOrgUser(OrgUserSearchPojo orgUserSearchPojo);
}

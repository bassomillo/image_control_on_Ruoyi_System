package com.ruoyi.project.union.mapper;

import com.ruoyi.project.org.entity.pojo.OrgUserSearchPojo;
import com.ruoyi.project.org.entity.pojo.UserShowPojo;
import com.ruoyi.project.union.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import com.ruoyi.project.union.entity.vo.UserVo;
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

    List<UserVo> searchAccount(AccountSearchVo accountSearchVo);
}

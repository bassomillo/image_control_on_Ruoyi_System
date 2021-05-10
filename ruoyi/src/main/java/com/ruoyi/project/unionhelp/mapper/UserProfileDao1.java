package com.ruoyi.project.unionhelp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.unionhelp.entity.UserProfile1;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crl
 * @since 2021-04-21
 */
public interface UserProfileDao1 extends BaseMapper<UserProfile1> {
//    @Select("select mss,truename,mobile,orgId,user.email,smallAvatar from user_profile,user where user.id = user_profile.id and concat(mss,truename,mobile,orgId,user.email,smallAvatar) like concat('%',${searchContent},'%') limit ${pageSize} OFFSET ${index}")
    List<Userinfo> searchUser(@Param("pageSize") Integer pageSize, @Param("index") Integer index, @Param("searchContent") String searchContent);

}

package com.ruoyi.project.unionhelp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.unionhelp.entity.UserProfile1;
import com.ruoyi.project.unionhelp.vo.Userinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crl
 * @since 2021-04-21
 */
public interface IUserProfileService1 extends IService<UserProfile1> {
    List<Userinfo> findAllPage(@Param("CurrentSize") int pageSize, @Param("CurrremtPage") int index, @Param("searchContent") String searchContent);

}

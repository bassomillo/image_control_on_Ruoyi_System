package com.ruoyi.project.union.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.org.entity.pojo.OrgUserSearchPojo;
import com.ruoyi.project.union.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.entity.vo.ResetPasswordVo;
import com.ruoyi.project.union.entity.vo.AccountSearchVo;
import com.ruoyi.project.union.entity.vo.DisableUserVo;
import com.ruoyi.project.union.entity.vo.UserRoleVo;
import com.ruoyi.project.union.entity.vo.UserSearchPojo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IUserService extends IService<User> {

    AjaxResult searchUser(UserSearchPojo userSearchPojo);

    Workbook writeExcel(HttpServletRequest req, String path, UserSearchPojo userSearchPojo);

    AjaxResult searchAccount(AccountSearchVo accountSearchVo);

    void updateUserRole(UserRoleVo userRoleVo);

    boolean isExistMobile(String mobile);

    boolean isExistEmail(String email);

    boolean isExistNickname(String nickname);

    void createUser(UserProfile userProfile);

    AjaxResult userImportCheck(MultipartFile file);

    AjaxResult userImport(MultipartFile file);

    AjaxResult disableUser(DisableUserVo disableUserVo, HttpServletRequest request);

    void batchDisableUser(List<Integer> userIds);

    AjaxResult disableImportCheck(MultipartFile file);

    AjaxResult disableImport(MultipartFile file);

    void updateUser(UserProfile userProfile);

    AjaxResult searchUserById(Integer userId);

    AjaxResult searchOrgUser(OrgUserSearchPojo orgUserSearchPojo);

    User searchUserByAccount(String account);

    AjaxResult resetPassword(ResetPasswordVo resetPasswordVo, HttpServletRequest request);
}

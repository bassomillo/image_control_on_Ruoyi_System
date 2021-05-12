package com.ruoyi.project.unionhelp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.unionhelp.entity.ManageClass;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 托管班 服务类
 * </p>
 *
 * @author zm
 * @since 2021-04-25
 */
public interface IManageClassService extends IService<ManageClass> {
    AjaxResult InsertManageClass(ManageClass manageClass);

    AjaxResult SearchManageClassById(Integer id);

    AjaxResult GetManageClasses(Integer pagesize, Integer page);

    AjaxResult UpdateManageClass(ManageClass manageClass);

    AjaxResult UploadFile(Integer userId, MultipartFile multipartFile, Integer targetId);

    AjaxResult DeleteFile(Integer targetId);

    AjaxResult downloadFile(Integer id, HttpServletResponse response);

    AjaxResult SelectManageClasses(String company, String trueName, String mobile, Integer pagesize, Integer page);

    AjaxResult export(HttpServletResponse response, HttpServletRequest request);

}

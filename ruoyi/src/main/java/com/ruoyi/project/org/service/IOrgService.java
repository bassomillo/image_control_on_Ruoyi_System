package com.ruoyi.project.org.service;

import com.ruoyi.project.org.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
public interface IOrgService extends IService<Org> {

    List<Org> searchOrgTree();
}

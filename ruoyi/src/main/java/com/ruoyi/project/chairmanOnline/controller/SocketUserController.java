package com.ruoyi.project.chairmanOnline.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.VO.GeneralManagerVO;
import com.ruoyi.project.chairmanOnline.service.SocketUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * (User)表控制层
 *
 * @author weide
 * @since 2021-04-29 11:22:39
 */
@RestController
@RequestMapping("socketuser")
@Api(tags = "web端-心理咨询-hwd")
public class SocketUserController {
    /**
     * 服务对象
     */
    @Resource
    private SocketUserService userService;


    /**
     * 心理咨询师列表
     *
     * @param
     * @return 单条数据
     */
    @ApiOperation("心理咨询师列表")
    @PostMapping("selectPsychologicalCounselors")
    public AjaxResult selectPsychologicalCounselors() {
        return AjaxResult.success(this.userService.selectPsychologicalCounselors());
    }


    /**
     * 省总经理和市总经理
     *
     * @param
     * @return 单条数据
     */
    @ApiOperation("用户的省总经理和市总经理")
    @PostMapping("selectGeneralManager")
    public AjaxResult selectGeneralManager(int userId) {
        GeneralManagerVO generalManagerVO = new GeneralManagerVO();
        GeneralManagerVO generalManagerVO1 = new GeneralManagerVO();
        generalManagerVO.setId(1);
        generalManagerVO.setName("省总经理");
        generalManagerVO1.setId(2);
        generalManagerVO1.setName("市总经理");
        ArrayList<GeneralManagerVO> generalManagerVOS = new ArrayList<>(2);
        generalManagerVOS.add(generalManagerVO);
        generalManagerVOS.add(generalManagerVO1);
        return AjaxResult.success(generalManagerVOS);
    }

}

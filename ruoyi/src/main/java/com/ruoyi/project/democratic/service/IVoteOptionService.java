package com.ruoyi.project.democratic.service;

import com.ruoyi.project.democratic.entity.VoteOption;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.democratic.entity.VoteQuestion;

import java.util.List;

/**
 * <p>
 * 投票/问卷题目选项表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
public interface IVoteOptionService extends IService<VoteOption> {

    /**
     * 批量新增选项
     * @param questionList
     * @return
     */
    boolean insertOptions(List<VoteQuestion> questionList);

}

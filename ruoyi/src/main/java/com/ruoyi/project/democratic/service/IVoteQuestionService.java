package com.ruoyi.project.democratic.service;

import com.ruoyi.project.democratic.entity.VoteQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 投票/问卷题目表 服务类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
public interface IVoteQuestionService extends IService<VoteQuestion> {

    /**
     * 批量新增题目
     * @param questionList
     * @return
     */
    boolean insertQuestionList(List<VoteQuestion> questionList);

}

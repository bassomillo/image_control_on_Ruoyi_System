package com.ruoyi.project.democratic.service.impl;

import com.ruoyi.project.democratic.entity.VoteQuestion;
import com.ruoyi.project.democratic.mapper.VoteQuestionMapper;
import com.ruoyi.project.democratic.service.IVoteQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投票题目表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Service
public class VoteQuestionServiceImpl extends ServiceImpl<VoteQuestionMapper, VoteQuestion> implements IVoteQuestionService {

    /**
     * 批量新增题目
     * @param questionList
     * @return
     */
    @Override
    public boolean insertQuestionList(List<VoteQuestion> questionList) {
        if (questionList == null || questionList.size() == 0){
            return true;
        }
        return saveBatch(questionList);
    }
}

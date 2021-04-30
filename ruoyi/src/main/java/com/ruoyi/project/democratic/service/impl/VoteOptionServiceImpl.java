package com.ruoyi.project.democratic.service.impl;

import com.ruoyi.project.democratic.entity.VoteOption;
import com.ruoyi.project.democratic.entity.VoteQuestion;
import com.ruoyi.project.democratic.mapper.VoteOptionMapper;
import com.ruoyi.project.democratic.service.IVoteOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投票/问卷题目选项表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Service
public class VoteOptionServiceImpl extends ServiceImpl<VoteOptionMapper, VoteOption> implements IVoteOptionService {

    /**
     * 批量新增选项
     * @param questionList
     * @return
     */
    @Override
    public boolean insertOptions(List<VoteQuestion> questionList) {
        try {
            for (VoteQuestion question : questionList){
                List<VoteOption> optionList = question.getOptionList();
                for (VoteOption option : optionList){
                    option.setVoteQuestionId(question.getId());
                }
                saveBatch(optionList);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

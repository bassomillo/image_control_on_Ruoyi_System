package com.ruoyi.project.democratic.entity.DO;

import com.ruoyi.project.democratic.entity.VoteQuestion;
import lombok.Data;

import java.util.List;

@Data
public class VoteAnalyseExportDO {

    //标题
    private String title;

    //答题人数
    private Integer num;

    private List<VoteQuestion> questionList;
}

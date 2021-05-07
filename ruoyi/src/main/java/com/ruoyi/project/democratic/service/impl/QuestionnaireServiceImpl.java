package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.*;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.mapper.VoteMapper;
import com.ruoyi.project.democratic.mapper.VoteOptionMapper;
import com.ruoyi.project.democratic.mapper.VoteQuestionMapper;
import com.ruoyi.project.democratic.service.IQuestionnaireService;
import com.ruoyi.project.democratic.service.IVoteOptionService;
import com.ruoyi.project.democratic.service.IVoteQuestionService;
import com.ruoyi.project.democratic.service.IVoteService;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 问卷表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-28
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<VoteMapper, Vote> implements IQuestionnaireService {

    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private VoteQuestionMapper voteQuestionMapper;
    @Autowired
    private VoteOptionMapper voteOptionMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;

    @Autowired
    private IVoteQuestionService voteQuestionService;
    @Autowired
    private IVoteOptionService voteOptionService;

    /**
     * 后台新增问卷
     * @param question
     * @return
     */
    @Override
    public AjaxResult insertQuestionnaire(Vote question) {
        try {
            question.setCreateDate(new Date());
            question.setUpdateDate(new Date());
            question.setType("questionnaire");

            save(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("创建失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("创建成功");
    }

    /**
     * 后台条件查询问卷列表
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackQuestionList(String title, Integer pageNum, Integer pageSize) {
        try {
            title = Str.fuzzyQuery(title);

            PageHelper.startPage(pageNum, pageSize);
            List<Vote> questionList = voteMapper.getQuestionList(title);
            PageInfo pageInfo = new PageInfo<>(questionList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", pageInfo.getTotal());
            jsonObject.put("list", pageInfo.getList());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台置顶/取消
     * @param questionnaireId
     * @param sticky
     * @return
     */
    @Override
    public AjaxResult setTop(Integer questionnaireId, Integer sticky) {
        try {
            Vote question = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            question.setSticky(sticky);
            question.setUpdateDate(new Date());

            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("设置失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("设置成功");
    }

    /**
     * 后台发布/取消
     * @param questionnaireId
     * @param userId
     * @param isPublish
     * @return
     */
    @Override
    public AjaxResult publishOrUn(Integer questionnaireId, Integer userId, boolean isPublish) {
        try {
            Vote question = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            question.setUpdateDate(new Date());
            question.setUpdateUserId(userId);
            if (isPublish){
                question.setStatus("published");
            }else {
                question.setStatus("unpublished");
            }

            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("成功");
    }

    /**
     * 后台回收
     * @param questionnaireId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult over(Integer questionnaireId, Integer userId) {
        try {
            Vote question = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            if (!"published".equals(question.getStatus())){
                return AjaxResult.error("该问卷尚未发布，不能回收");
            }
            question.setUpdateDate(new Date());
            question.setUpdateUserId(userId);
            question.setStatus("over");
            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("回收失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("回收成功");
    }

    /**
     * 后台-删除
     * @param questionnaireId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult deleteBack(Integer questionnaireId, Integer userId) {
        try {
            Vote question = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            question.setUpdateDate(new Date());
            question.setUpdateUserId(userId);
            question.setIsShow(0);
            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 后台重命名
     * @param questionnaireId
     * @param userId
     * @param title
     * @return
     */
    @Override
    public AjaxResult rename(Integer questionnaireId, Integer userId, String title) {
        try {
            Vote question = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            question.setUpdateDate(new Date());
            question.setUpdateUserId(userId);
            question.setTitle(title);
            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("重命名失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("重命名成功");
    }

    /**
     * 后台查详情
     * @param questionnaireId
     * @return
     */
    @Override
    public AjaxResult getBackDetail(Integer questionnaireId) {
        try {
            //查问卷基本信息
            Vote q = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            //查问卷题目
            List<VoteQuestion> questionList = voteQuestionMapper.selectList(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId));
            //查问卷选项
            for (VoteQuestion question : questionList){
                List<VoteOption> optionList = voteOptionMapper.selectList(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questionnaire", q);
            jsonObject.put("question", questionList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台修改基础信息
     * @param questionBase
     * @return
     */
    @Override
    public AjaxResult updateBaseData(Vote questionBase) {
        try {
            Vote q = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionBase.getId()));
            q.setStartTime(questionBase.getStartTime());
            q.setEndTime(questionBase.getEndTime());
            q.setMaxSubmit(questionBase.getMaxSubmit());
            q.setUpdateUserId(questionBase.getUpdateUserId());
            q.setUpdateDate(new Date());

            updateById(q);

            return AjaxResult.success("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("更新失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台编辑标题封面
     * @param questionCover
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateCover(Vote questionCover) {
        try {
            //设置基础信息
            Vote q = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionCover.getId()));
            q.setTitle(questionCover.getTitle());
            q.setIsSigned(questionCover.getIsSigned());
            q.setUpdateDate(new Date());
            q.setUpdateUserId(questionCover.getUpdateUserId());
            q.setCoverUrl(questionCover.getCoverUrl());
            q.setCoverUrlId(questionCover.getCoverUrlId());

            //富文本转换
            String text = null;
            if (questionCover.getDescription() != null && !"".equals(questionCover.getDescription())) {
                text = HtmlUtils.htmlEscape(questionCover.getDescription());
            }
            q.setDescription(text);

            //处理封面文件
            if (questionCover.getCoverUrlId() != null){
                UploadFiles files = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.ID, questionCover.getCoverUrlId()));
                files.setTargetId(q.getId());
                files.setTargetType("questionnaire_cover");
                uploadFilesMapper.updateById(files);
            }

            updateById(q);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("编辑失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("编辑成功");
    }

    /**
     * 后台添加题目
     * @param question
     * @return
     */
    @Override
    @Transactional
    public AjaxResult insertQuestion(VoteQuestion question) {
        try {
            //保存题目
            voteQuestionMapper.insert(question);
            //保存选项
            for (VoteOption option : question.getOptionList()) {
                option.setVoteQuestionId(question.getId());
                voteOptionMapper.insert(option);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("新增失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("新增成功");
    }

    /**
     * 后台编辑题目
     * @param question
     * @return
     */
    @Override
    @Transactional
    public AjaxResult updateQuestion(VoteQuestion question) {
        try {
            voteQuestionMapper.updateById(question);
            for (VoteOption option : question.getOptionList()){
                if (option.getId() != null) {
                    voteOptionMapper.updateById(option);
                }else {
                    voteOptionMapper.insert(option);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("编辑失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("编辑成功");
    }

    /**
     * 后台删除题目/选项
     * @param questionId
     * @param optionId
     * @return
     */
    @Override
    @Transactional
    public AjaxResult deleteQuestionOrOption(Integer questionId, Integer optionId) {
        try {
            //删选项
            if (optionId != null){
                voteOptionMapper.deleteById(optionId);
            }
            //删题目
            if (questionId != null){
                voteQuestionMapper.deleteById(questionId);
                voteOptionMapper.delete(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, questionId));
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 后台校验
     * @param file
     * @param questionnaireId
     * @return
     */
    @Override
    public AjaxResult checkImportQuestion(MultipartFile file, Integer questionnaireId) {
        try {
            List<String> msg = new ArrayList<>();

            Workbook wb = ExcelTool.getWb(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 2;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            ExcelTool.excelCheck(sheet, msg, rowStart, rowEnd);
            if (msg.size() != 0){
                return AjaxResult.error("第一行没有数据或没有填写数据");
            }

            List<String> errorMsgList = new ArrayList<>();
            List<VoteQuestion> questionList = new ArrayList<>();
            for (int i = rowStart; i < rowEnd; i++){
                Row row = sheet.getRow(i);

                //必填项校验
                String question = ExcelTool.getCellValue(row.getCell(0));
                String type = ExcelTool.getCellValue(row.getCell(1));
                String must = ExcelTool.getCellValue(row.getCell(2));
                String option = ExcelTool.getCellValue(row.getCell(3));
                String max = ExcelTool.getCellValue(row.getCell(4));
                if (StringUtils.isBlank(question) && StringUtils.isBlank(type) && StringUtils.isBlank(must) &&
                StringUtils.isBlank(option) && StringUtils.isBlank(max)){
                    continue;
                }
                /*********校验格式*********/
                String errMsg = "";
                boolean isFirst = true; // 判断是否是此行数据的第一个错误

                //题目不能为空
                if (StringUtils.isBlank(question)){
                    errMsg += "第" + (i+1) + "行信息有误，题目不能为空";
                    isFirst = false;
                }

                //题型不能为空，名字不能错
                if (StringUtils.isBlank(type)){
                    if (isFirst){
                        errMsg += "第" + (i+1) + "行信息有误，题型不能为空，题型只能为单选，多选或文本";
                        isFirst = false;
                    }else {
                        errMsg += "、题型不能为空，题型只能为单选，多选或文本";
                    }
                }else {
                    if (!"单选".equals(type) && !"多选".equals(type) && !"文本".equals(type)){
                        if (isFirst){
                            errMsg += "第" + (i+1) + "行信息有误，题型名字有误，题型只能为单选，多选或文本";
                            isFirst = false;
                        }else {
                            errMsg += "、题型名字有误，题型只能为单选，多选或文本";
                        }
                    }
                }

                //是否必答不能为空，名字不能错
                if (StringUtils.isBlank(must)){
                    if (isFirst){
                        errMsg += "第" + (i+1) + "行信息有误，是否必答不能为空";
                        isFirst = false;
                    }else {
                        errMsg += "、是否必答不能为空";
                    }
                }else {
                    if (!"是".equals(must) && !"否".equals(must)){
                        if (isFirst){
                            errMsg += "第" + (i+1) + "行信息有误，是否必答只能为是或否";
                            isFirst = false;
                        }else {
                            errMsg += "、是否必答只能为是或否";
                        }
                    }
                }

                //选项在单选多选时必填
                if (StringUtils.isBlank(option)){
                    if (type != null && !"".equals(type) && !"文本".equals(type)){
                        if (isFirst){
                            errMsg += "第" + (i+1) + "行信息有误，选项不能为空";
                            isFirst = false;
                        }else {
                            errMsg += "、选项不能为空";
                        }
                    }
                }else {
                    //选项应大于等于2
                    if ("单选".equals(type) || "多选".equals(type)){
                        String[] opt = option.split("\n");
                        if (opt.length < 2){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，选项个数应大于等于2";
                                isFirst = false;
                            }else {
                                errMsg += "、选项个数应大于等于2";
                            }
                        }
                    }
                }

                //最多选择个数不能超过选项数,应为正整数
                if (StringUtils.isNotBlank(max)){
                    if ("多选".equals(type)){
                        if (max.contains(".") || max.contains("-")){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，最多选择个数应为正整数";
                                isFirst = false;
                            }else {
                                errMsg += "、最多选择个数应为正整数";
                            }
                        }else {
                            String[] opt = option.split("\n");
                            Integer maxSum = Integer.valueOf(max);
                            if (maxSum > opt.length){
                                if (isFirst){
                                    errMsg += "第" + (i+1) + "行信息有误，最多选择个数应小于等于选项数";
                                    isFirst = false;
                                }else {
                                    errMsg += "、最多选择个数应小于等于选项数";
                                }
                            }
                        }
                    }
                }

                //有错误信息就不执行下一步
                if (!"".equals(errMsg)){
                    errorMsgList.add(errMsg);
                    continue;
                }

                /*********填写数据*********/
                VoteQuestion voteQuestion = new VoteQuestion();
                List<VoteOption> optionList = new ArrayList<>();
                VoteOption voteOption;

                voteQuestion.setContent(question);
                voteQuestion.setVoteId(questionnaireId);
                if ("单选".equals(type)) voteQuestion.setType("single");
                if ("多选".equals(type)) voteQuestion.setType("multiple");
                if ("是".equals(must)) voteQuestion.setMust(1);
                if ("否".equals(must)) voteQuestion.setMust(0);

                if ("文本".equals(type)){
                    voteQuestion.setType("completion");
                    voteOption = new VoteOption();
                    optionList.add(voteOption);
                }else {
                    //单选、多选
                    //选项
                    String[] opts = option.split("\n");
                    for (String opt : opts){
                        voteOption = new VoteOption();
                        voteOption.setContent(opt);
                        optionList.add(voteOption);
                    }
                    //最多选择数
                    Integer maxInt = StringUtils.isBlank(max) ? 1 : Integer.parseInt(max);
                    voteQuestion.setMaxChecked("单选".equals(type) ? 1 : maxInt);
                }

                voteQuestion.setOptionList(optionList);
                questionList.add(voteQuestion);
            }

            if (errorMsgList.size() > 0){
                return AjaxResult.error("校验失败", errorMsgList);
            }else {
                return AjaxResult.success("校验成功，一共包含" + questionList.size() + "条数据", questionList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("校验失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台批量导题
     * @param questionList
     * @return
     */
    @Override
    @Transactional
    public AjaxResult importQuestion(List<VoteQuestion> questionList) {
        try {
            boolean question = voteQuestionService.insertQuestionList(questionList);
            boolean option = voteOptionService.insertOptions(questionList);
            if (!question || !option){
                return AjaxResult.error("导入失败，请联系管理员");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导入失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("导入成功");
    }
}

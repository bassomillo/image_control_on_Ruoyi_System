package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.*;
import com.ruoyi.project.democratic.entity.DO.ExamPaperExportDO;
import com.ruoyi.project.democratic.entity.DO.QuestionAndPaperDO;
import com.ruoyi.project.democratic.entity.DO.VoteAnalyseExportDO;
import com.ruoyi.project.democratic.entity.VO.ExamPublishVO;
import com.ruoyi.project.democratic.entity.VO.MemberInfoVO;
import com.ruoyi.project.democratic.mapper.*;
import com.ruoyi.project.democratic.service.IQuestionnaireService;
import com.ruoyi.project.democratic.service.IVoteOptionService;
import com.ruoyi.project.democratic.service.IVoteQuestionService;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import com.ruoyi.project.union.entity.UserProfile;
import com.ruoyi.project.union.mapper.UserProfileDao;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private OrgDao orgDao;
    @Autowired
    private VotePaperMapper votePaperMapper;
    @Autowired
    private VoteMemberMapper voteMemberMapper;
    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private IVoteQuestionService voteQuestionService;
    @Autowired
    private IVoteOptionService voteOptionService;

    @Resource
    private ToolUtils toolUtils;

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

    /**
     * 删除文件/图片
     * @param fileId
     * @param type
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteFile(Integer fileId, String type, Integer id) {
        try {
            //删文件
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, fileId));
            //暂时用uri，后续完善路径
//            boolean flag = fastdfsClientUtil.deleteFile(uploadFile.getUri());
//            if (! flag){
//                return AjaxResult.error("删除失败，请联系管理员");
//            }
            uploadFilesMapper.delete(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, fileId));

            //改字段
            if ("qu".equals(type)){
                Vote vote = voteMapper.selectOne(new QueryWrapper<Vote>().
                        eq(Vote.ID, id));
                vote.setCoverUrlId(null);
                vote.setCoverUrl(null);
                updateById(vote);
            }else if ("quQuestion".equals(type)){
                VoteQuestion voteQuestion = voteQuestionMapper.selectOne(new QueryWrapper<VoteQuestion>().
                        eq(VoteQuestion.ID, id));
                voteQuestion.setImgId(null);
                voteQuestion.setImgUrl(null);
                voteQuestionMapper.updateById(voteQuestion);
            }else if ("quOption".equals(type)){
                VoteOption voteOption = voteOptionMapper.selectOne(new QueryWrapper<VoteOption>().
                        eq(VoteOption.ID, id));
                voteOption.setImgId(null);
                voteOption.setImgUrl(null);
                voteOptionMapper.updateById(voteOption);
            }

            return AjaxResult.success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台发布情况
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public AjaxResult getPublishList(String startTime, String endTime) {
        try {
            //查出时间段内的问卷，默认查全部
            List<Vote> voteList = voteMapper.getPublishList(startTime, endTime);

            Map<Integer, Integer> map = new HashMap<>();
            for (Vote vote : voteList){
                List<Integer> orgIds = toolUtils.getOrgTreeInt(Integer.parseInt(vote.getNewStatus()));
                if (orgIds.size() != 0) {
                    Org org = orgDao.selectOne(new QueryWrapper<Org>().
                            eq(Org.ORGLEVEL, "市级分公司").
                            in(Org.ID, orgIds));
                    Integer num = map.get(org.getId());
                    map.put(org.getId(), num == null ? 1 : num + 1);
                }
            }

            //查询所有地市
            List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().
                    eq(Org.ORGLEVEL, "市级分公司"));
            List<ExamPublishVO> publishList = new ArrayList<>();
            for (Org org : orgList){
                ExamPublishVO publish = new ExamPublishVO();
                publish.setOrgId(org.getId());
                publish.setOrgName(org.getName());
                publish.setNum(map.get(org.getId()) == null ? 0 : map.get(org.getId()));

                publishList.add(publish);
            }

            return AjaxResult.success("获取成功", publishList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("获取失败，请联系管理员");
        }
    }

    /**
     * 后台导出发布情况
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportPublishList(String startTime, String endTime, HttpServletResponse response) {
        try {
            AjaxResult result = getPublishList(startTime, endTime);
            List<ExamPublishVO> publishList = (List<ExamPublishVO>) result.get("data");

            //导出
            String name = "发布情况数据" + ".xls";
            Workbook wb = getWorkbook(publishList, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
        return null;
    }

    /**
     * 后台统计结果
     * @param questionnaireId
     * @return
     */
    @Override
    public AjaxResult analyseQuestionnaire(Integer questionnaireId) {
        try {
            Vote questionnaire = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            List<VoteQuestion> questionList = voteQuestionMapper.selectList(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId));

            //计算题目出现次数<题目id，数量>
            Map<Integer, Integer> questionMap = new HashMap<>();
            //计算选项出现次数<选项id，数量>
            Map<String, Integer> countMap = new HashMap<>();
            //保存填空题内容<题目id，内容列表>
            Map<Integer, List<String>> completionMap = new HashMap<>();

            List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId));
            for (VotePaper paper : paperList){
                int questionNum = questionMap.get(paper.getVoteQuestionId())==null ? 0 : questionMap.get(paper.getVoteQuestionId());
                questionMap.put(paper.getVoteQuestionId(), questionNum + 1);

                if ("completion".equals(paper.getType())){
                    //填空
                    List<String> contentList = completionMap.get(paper.getVoteQuestionId())==null ? new ArrayList<>() : completionMap.get(paper.getVoteQuestionId());
                    contentList.add(paper.getSubmitContent());
                    if (paper.getSubmitContent() != null && !"".equals(paper.getSubmitContent())){
                        completionMap.put(paper.getVoteQuestionId(), contentList);
                    }
                }else if ("multiple".equals(paper.getType())){
                    //多选
                    String[] ids = paper.getSubmitContent().split(" ");
                    for (String id : ids){
                        int num = countMap.get(id)==null ? 0 : countMap.get(id);
                        countMap.put(id, num + 1);
                    }
                }else {
                    //单选
                    int num = countMap.get(paper.getSubmitContent())==null ? 0 : countMap.get(paper.getSubmitContent());
                    countMap.put(paper.getSubmitContent(), num + 1);
                }
            }

            for (VoteQuestion question : questionList){
                //查找此题目数量
                question.setCount(questionMap.get(question.getId())==null ? 0 : questionMap.get(question.getId()));
                DecimalFormat df = new DecimalFormat("#.0000");

                //查找选项
                List<VoteOption> optionList = voteOptionMapper.selectList(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, question.getId()));
                if ("completion".equals(question.getType())){
                    List<String> contentList = completionMap.get(question.getId());
                    contentList = contentList == null ? new ArrayList<>() : contentList;
                    optionList.get(0).setContentList(contentList);
                }else {
                    for (VoteOption option : optionList) {
                        int num = countMap.get(option.getId().toString())==null ? 0 : countMap.get(option.getId().toString());
                        option.setCount(num);
                        //计算百分比
                        double scale = Double.parseDouble(df.format((double) num / (double)question.getCount()));
                        option.setScale(scale);
                    }
                }
                question.setOptionList(optionList);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questionnaire", questionnaire);
            jsonObject.put("question", questionList);

            return AjaxResult.success("统计成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("统计失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台导出答题数据
     * @param questionnaireId
     * @param userId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportPaperData(Integer questionnaireId, Integer userId, HttpServletResponse response) {
        try {
            //获取数据
            ExamPaperExportDO exportDO =voteMapper.getUserExportData(userId);

            //设置用户基础信息
            exportDO.setSex("male".equals(exportDO.getGender()) ? "男" : "女");
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, exportDO.getOrgId()));
            exportDO.setOrgName(org.getName());
            String orgNameDetail = toolUtils.getOrgName(exportDO.getOrgId());
            exportDO.setOrgNameDetail(orgNameDetail);

            //设置用户问卷信息
            VotePaper quPaper = votePaperMapper.selectOne(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId).
                    eq(VotePaper.USERID, userId).
                    orderByDesc(VotePaper.SUBMITFLAG).
                    last("limit 1"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(quPaper.getSubmitDate());
            exportDO.setTime(date);
            exportDO.setImg(quPaper.getSignatureImg());

            List<VotePaper> quPaperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId).
                    eq(VotePaper.USERID, userId));
            List<QuestionAndPaperDO> paperDOList = new ArrayList<>();
            for (VotePaper paper : quPaperList){
                //查询答题信息
                QuestionAndPaperDO qap = new QuestionAndPaperDO();
                VoteQuestion question = voteQuestionMapper.selectOne(new QueryWrapper<VoteQuestion>().
                        eq(VoteQuestion.ID, paper.getVoteQuestionId()));
                qap.setQuestion(question.getContent());
                if ("completion".equals(paper.getType())){
                    //填空
                    qap.setContent(paper.getSubmitContent());
                }else if ("multiple".equals(paper.getType())){
                    //多选
                    String[] ids = paper.getSubmitContent().split(" ");
                    String content = "";
                    for (String id : ids){
                        VoteOption option = voteOptionMapper.selectOne(new QueryWrapper<VoteOption>().
                                eq(VoteOption.ID, id));
                        content += option.getContent() + " ";
                    }
                    qap.setContent(content);
                }else {
                    //单选
                    VoteOption option = voteOptionMapper.selectOne(new QueryWrapper<VoteOption>().
                            eq(VoteOption.ID, paper.getSubmitContent()));
                    qap.setContent(option==null ? "" : option.getContent());
                }
                paperDOList.add(qap);
            }
            exportDO.setPaperList(paperDOList);

            //导出
            String name = "答题详细数据" + ".xls";
            Workbook wb = getWorkbook(exportDO, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
        return null;
    }

    /**
     * 后台导出统计结果
     * @param questionnaireId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportAnalyse(Integer questionnaireId, HttpServletResponse response) {
        try {
            //数据转换
            AjaxResult result = analyseQuestionnaire(questionnaireId);
            JSONObject jsonObject = (JSONObject) result.get("data");
            Vote qu = jsonObject.getObject("questionnaire", Vote.class);
            JSONArray array = jsonObject.getJSONArray("question");
            String str = array.toJSONString();
            List<VoteQuestion> questionList = JSONArray.parseArray(str, VoteQuestion.class);

            //处理数据
            VoteAnalyseExportDO analyse = new VoteAnalyseExportDO();
            //一共有几人答题
            VoteQuestion must = voteQuestionMapper.selectOne(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId).
                    eq(VoteQuestion.MUST, 1).
                    last("limit 1"));
            List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId).
                    eq(VotePaper.VOTEQUESTIONID, must.getId()));
            analyse.setNum(paperList.size());
            analyse.setTitle(qu.getTitle());
            analyse.setQuestionList(questionList);

            //导出
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = analyse.getTitle() + sdf.format(date) + ".xls";
            Workbook wb = getWorkbook(analyse, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
        return null;
    }

    /**
     * 后台人员情况（未投票）
     * @param questionnaireId
     * @param orgId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getMember(Integer questionnaireId, Integer orgId, Integer pageNum, Integer pageSize) {
        try {
            long total = 0;
            List<MemberInfoVO> members = new ArrayList<>();
            //找到组织机构的下级
            List<Integer> orgList = toolUtils.getOrgDownInt(orgId);

            if (pageNum != null) {
                PageHelper.startPage(pageNum, pageSize);
                List<MemberInfoVO> memberList = voteMapper.getMemberList(questionnaireId, orgList);
                PageInfo pageInfo = new PageInfo<>(memberList);
                members = pageInfo.getList();
                total = pageInfo.getTotal();
            }else {
                members = voteMapper.getMemberList(questionnaireId, orgList);
                total = members.size();
            }

            for (MemberInfoVO member : members){
                member.setSex("male".equals(member.getSex()) ? "男" : "女");
                String orgName = toolUtils.getOrgName(member.getOrgId());
                member.setOrgName(orgName);
                List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, questionnaireId).
                        eq(VotePaper.USERID, member.getId()));
                if (paperList != null && paperList.size() != 0) {
                    member.setStatus(1);
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", members);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台导出人员情况
     * @param questionnaireId
     * @param orgId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportMember(Integer questionnaireId, Integer orgId, HttpServletResponse response) {
        try {
            //数据处理
            AjaxResult result = getMember(questionnaireId, orgId, null, null);
            JSONObject jsonObject = (JSONObject) result.get("data");
            JSONArray array = jsonObject.getJSONArray("list");
            String str = array.toJSONString();
            List<MemberInfoVO> memberList = JSONArray.parseArray(str, MemberInfoVO.class);

            //导出
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "问卷调查未投票人员" + sdf.format(date) + ".xls";
            Workbook wb = getWorkbookMember(memberList, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("导出失败，请联系管理员", e.getMessage());
        }
        return null;
    }

    /**
     * 首页条件查询列表
     * @param userId
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopQuList(Integer userId, String title, Integer pageNum, Integer pageSize) {
        try {
            title = Str.fuzzyQuery(title);
            List<VoteMember> memberList = voteMemberMapper.selectList(new QueryWrapper<VoteMember>().
                    eq(VoteMember.USERID, userId).
                    eq(VoteMember.TYPE, "questionnaire"));

            List<Vote> quList = new ArrayList<>();
            long total = 0;
            if (memberList != null && memberList.size() > 0) {
                List<Integer> voteIdList = memberList.stream().map(VoteMember::getVoteId).collect(Collectors.toList());

                PageHelper.startPage(pageNum, pageSize);
                quList = voteMapper.getTopQuList(title, voteIdList);
                PageInfo pageInfo = new PageInfo<>(quList);
                total = pageInfo.getTotal();
                quList = pageInfo.getList();
            }

            for (Vote qu : quList){
                //获取作答记录
                List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, qu.getId()).
                        eq(VotePaper.USERID, userId).
                        eq(VotePaper.SUBMITFLAG, 1));
                //是否已作答
                boolean isExam = false;
                if (paperList != null && paperList.size() > 0){
                    isExam = true;
                }
                //获取最晚作答时间
                Date endTime = qu.getEndTime();
                //是否已超时
                boolean isLate = endTime.before(new Date());

                if (isExam){
                    qu.setNewStatus("已提交");
                }else if (isLate){
                    qu.setNewStatus("已结束");
                }else {
                    qu.setNewStatus("未作答");
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", quList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页查询问卷详情
     * @param questionnaireId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getTopDetail(Integer questionnaireId, Integer userId) {
        try {
            //查问卷信息
            Vote qu = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            //查找题目
            List<VoteQuestion> questionList = voteQuestionMapper.selectList(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId));
            for (VoteQuestion question : questionList){
                //查找选项
                List<VoteOption> optionList = voteOptionMapper.selectList(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }
            //查询问卷答题信息
            VotePaper paper = votePaperMapper.selectOne(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId).
                    eq(VotePaper.USERID, userId).
                    orderByDesc(VotePaper.SUBMITFLAG).
                    last("limit 1"));
            List<VotePaper> paperList = new ArrayList<>();
            if (paper != null) {
                paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, questionnaireId).
                        eq(VotePaper.USERID, userId).
                        eq(VotePaper.SUBMITFLAG, paper.getSubmitFlag()));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questionnaire", qu);
            jsonObject.put("question", questionList);
            jsonObject.put("paper", paperList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页提交问卷
     * @param paperList
     * @return
     */
    @Override
    public AjaxResult submitQu(List<VotePaper> paperList) {
        try {
            UserProfile profile = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                    eq(UserProfile.ID, paperList.get(0).getUserId()));
            //查找用户是第几次作答
            VotePaper paper0 = votePaperMapper.selectOne(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, paperList.get(0).getVoteId()).
                    eq(VotePaper.USERID, paperList.get(0).getUserId()).
                    orderByDesc(VotePaper.SUBMITFLAG).
                    last("limit 1"));
            Integer count = paper0 == null ? 0 : paper0.getSubmitFlag();

            Vote qu = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, paperList.get(0).getVoteId()));
            if (count >= qu.getMaxSubmit()){
                return AjaxResult.error("已没有提交次数，请勿重复提交");
            }else {
                count ++;
            }

            for (VotePaper paper : paperList){
                paper.setSubmitDate(new Date());
                paper.setTel(profile.getMobile());
                paper.setSubmitFlag(count);
                votePaperMapper.insert(paper);
            }

            return AjaxResult.success("提交成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("提交失败，请联系管理员", e.getMessage());
        }
    }


    /**
     * 处理导出发布情况表格
     * @param publishList
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(List<ExamPublishVO> publishList, HttpServletRequest request) throws Exception{
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/发布情况导出模板.xls");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);

        //设置数据到excel
        int r = 1;
        Row row = null;
        for (ExamPublishVO publish : publishList){
            //创建一行
            row = sheet.createRow(r++);
            //组装一行数据
            ExcelTool.createCell(row, 0, style, publish.getOrgName());
            ExcelTool.createCell(row, 1, style, publish.getNum());
        }

        return wb;
    }

    /**
     * 导出答题数据处理
     * @param export
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(ExamPaperExportDO export, HttpServletRequest request) throws Exception{
        //读取模板
        Workbook wb = ExcelTool.read(request, "/static/excel/答题详细数据导出模板.xls");
        //获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);
        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);

        //设置数据到excel
        int r = 1;
        Row row0 = sheet.createRow(0);
        ExcelTool.createCell(row0, 0, style, "姓名");
        ExcelTool.createCell(row0, 1, style, "性别");
        ExcelTool.createCell(row0, 2, style, "联系方式");
        ExcelTool.createCell(row0, 3, style, "组织机构");
        ExcelTool.createCell(row0, 4, style, "答题时间");
        ExcelTool.createCell(row0, 5, style, "签名");
        ExcelTool.createCell(row0, 6, style, "用工形式");
        ExcelTool.createCell(row0, 7, style, "归属组织");

        Row row = sheet.createRow(r++);
        //组装一行数据
        ExcelTool.createCell(row, 0, style, export.getName());
        ExcelTool.createCell(row, 1, style, export.getSex());
        ExcelTool.createCell(row, 2, style, export.getMobile());
        ExcelTool.createCell(row, 3, style, export.getOrgName());
        ExcelTool.createCell(row, 4, style, export.getTime());
        ExcelTool.createCell(row, 5, style, export.getImg());
        ExcelTool.createCell(row, 6, style, export.getEmploymentForm());
        ExcelTool.createCell(row, 7, style, export.getOrgNameDetail());

        int i = 8;
        for (QuestionAndPaperDO paperDO : export.getPaperList()){
            ExcelTool.createCell(row0, i, style, paperDO.getQuestion());
            ExcelTool.createCell(row, i, style, paperDO.getContent());
            i++;
        }

        return wb;
    }

    /**
     * 导出统计结果
     * @param export
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(VoteAnalyseExportDO export, HttpServletRequest request) throws Exception{
        Workbook wb = new HSSFWorkbook();
        //获取sheet
        Sheet sheet = wb.createSheet();
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);
        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);
        //下边框
		style.setBorderBottom(BorderStyle.THIN);
		//左边框
		style.setBorderLeft(BorderStyle.THIN);
		//上边框
		style.setBorderTop(BorderStyle.THIN);
		//右边框
		style.setBorderRight(BorderStyle.THIN);
		//设置宽度
        sheet.setColumnWidth(0, 252*30+323);
        sheet.setColumnWidth(1, 252*18+323);
        sheet.setColumnWidth(2, 252*18+323);

        //设置数据到excel
        int r = 0;
        Row row0 = sheet.createRow(r++);
        ExcelTool.createCell(row0, 0, style, export.getTitle());
        ExcelTool.createCell(row0, 1, style, export.getTitle());
        ExcelTool.createCell(row0, 2, style, export.getTitle());
        //合并单元格
        CellRangeAddress region0 = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(region0);

        Row row1 = sheet.createRow(r++);
        ExcelTool.createCell(row1, 2, style, "答题人数：" + export.getNum() + "人");
        int i = 1;
        for (VoteQuestion question : export.getQuestionList()){
            //设置题目背景色
            CellStyle style1 = wb.createCellStyle();
            style1.cloneStyleFrom(style);
            style1.setFillForegroundColor(IndexedColors.GOLD.getIndex());
            style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style1.setAlignment(HorizontalAlignment.LEFT);
            Row rowQ = sheet.createRow(r++);
            ExcelTool.createCell(rowQ, 0, style1, i + "." + question.getContent());
            ExcelTool.createCell(rowQ, 1, style1, i + "." + question.getContent());
            ExcelTool.createCell(rowQ, 2, style1, i + "." + question.getContent());
            //合并
            CellRangeAddress region = new CellRangeAddress(r-1, r-1, 0, 2);
            sheet.addMergedRegion(region);

            //填空和选择的选项需要的格式
            CellStyle style2 = wb.createCellStyle();
            style2.cloneStyleFrom(style);
            style2.setAlignment(HorizontalAlignment.LEFT);
            //选项为填空不需要有接下来的描述行
            if ("completion".equals(question.getType())){
                for (String content : question.getOptionList().get(0).getContentList()){
                    Row row = sheet.createRow(r++);
                    ExcelTool.createCell(row, 0, style2, content);
                    ExcelTool.createCell(row, 1, style2, content);
                    ExcelTool.createCell(row, 2, style2, content);
                    //合并
                    CellRangeAddress region1 = new CellRangeAddress(r-1, r-1, 0, 2);
                    sheet.addMergedRegion(region1);
                }
            }else {
                Row rowO = sheet.createRow(r++);
                ExcelTool.createCell(rowO, 0, style, "选项");
                ExcelTool.createCell(rowO, 1, style, "小计");
                ExcelTool.createCell(rowO, 2, style, "比例");
                char a = 'A';
                for (VoteOption option : question.getOptionList()){
                    a = (char) (a + 1);
                    Row row = sheet.createRow(r++);
                    ExcelTool.createCell(row, 0, style2, a + "." + option.getContent());
                    ExcelTool.createCell(row, 1, style, option.getCount());
                    ExcelTool.createCell(row, 2, style, (option.getScale()*100) + "%");
                }
            }

            i++;
        }

        return wb;
    }

    /**
     * 导出人员情况
     * @param memberList
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbookMember(List<MemberInfoVO> memberList, HttpServletRequest request) throws Exception{
        Workbook wb = new HSSFWorkbook();
        //获取sheet
        Sheet sheet = wb.createSheet();
        //定义单元格的样式style
        CellStyle style = ExcelTool.getStyle(wb);
        //设置字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);
        //设置宽度
        sheet.setColumnWidth(0, 252*25+323);
        sheet.setColumnWidth(1, 252*25+323);
        sheet.setColumnWidth(2, 252*25+323);
        sheet.setColumnWidth(3, 252*25+323);
        sheet.setColumnWidth(4, 252*25+323);

        //设置数据到excel
        int r = 0;
        Row row0 = sheet.createRow(r++);
        ExcelTool.createCell(row0, 0, style, "姓名");
        ExcelTool.createCell(row0, 1, style, "性别");
        ExcelTool.createCell(row0, 2, style, "联系方式");
        ExcelTool.createCell(row0, 3, style, "用工形式");
        ExcelTool.createCell(row0, 4, style, "归属组织");
        for (MemberInfoVO member : memberList){
            Row row = sheet.createRow(r++);

            ExcelTool.createCell(row, 0, style, member.getTruename());
            ExcelTool.createCell(row, 1, style, member.getSex());
            ExcelTool.createCell(row, 2, style, member.getMobile());
            ExcelTool.createCell(row, 3, style, member.getEmploymentForm());
            ExcelTool.createCell(row, 4, style, member.getOrgName());
        }

        return wb;
    }
}

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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * ????????? ???????????????
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
     * ??????????????????
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
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????????????????????????????
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

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????/??????
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
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ????????????/??????
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
            return AjaxResult.error("???????????????????????????", e.getMessage());
        }
        return AjaxResult.success("??????");
    }

    /**
     * ????????????
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
                return AjaxResult.error("????????????????????????????????????");
            }
            question.setUpdateDate(new Date());
            question.setUpdateUserId(userId);
            question.setStatus("over");
            updateById(question);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????-??????
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
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ???????????????
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
            return AjaxResult.error("????????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("???????????????");
    }

    /**
     * ???????????????
     * @param questionnaireId
     * @return
     */
    @Override
    public AjaxResult getBackDetail(Integer questionnaireId) {
        try {
            //?????????????????????
            Vote q = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            //???????????????
            List<VoteQuestion> questionList = voteQuestionMapper.selectList(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId));
            //???????????????
            for (VoteQuestion question : questionList){
                List<VoteOption> optionList = voteOptionMapper.selectList(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questionnaire", q);
            jsonObject.put("question", questionList);

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????????????????
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

            return AjaxResult.success("????????????");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????????????????
     * @param questionCover
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateCover(Vote questionCover) {
        try {
            //??????????????????
            Vote q = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionCover.getId()));
            q.setTitle(questionCover.getTitle());
            q.setIsSigned(questionCover.getIsSigned());
            q.setUpdateDate(new Date());
            q.setUpdateUserId(questionCover.getUpdateUserId());
            q.setCoverUrl(questionCover.getCoverUrl());
            q.setCoverUrlId(questionCover.getCoverUrlId());

            //???????????????
            String text = null;
            if (questionCover.getDescription() != null && !"".equals(questionCover.getDescription())) {
                text = HtmlUtils.htmlEscape(questionCover.getDescription());
            }
            q.setDescription(text);

            //??????????????????
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
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????????????????
     * @param question
     * @return
     */
    @Override
    @Transactional
    public AjaxResult insertQuestion(VoteQuestion question) {
        try {
            //????????????
            voteQuestionMapper.insert(question);
            //????????????
            for (VoteOption option : question.getOptionList()) {
                option.setVoteQuestionId(question.getId());
                voteOptionMapper.insert(option);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????????????????
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
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ??????????????????/??????
     * @param questionId
     * @param optionId
     * @return
     */
    @Override
    @Transactional
    public AjaxResult deleteQuestionOrOption(Integer questionId, Integer optionId) {
        try {
            //?????????
            if (optionId != null){
                voteOptionMapper.deleteById(optionId);
            }
            //?????????
            if (questionId != null){
                voteQuestionMapper.deleteById(questionId);
                voteOptionMapper.delete(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, questionId));
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ????????????
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
                return AjaxResult.error("??????????????????????????????????????????");
            }

            List<String> errorMsgList = new ArrayList<>();
            List<VoteQuestion> questionList = new ArrayList<>();
            for (int i = rowStart; i < rowEnd; i++){
                Row row = sheet.getRow(i);

                //???????????????
                String question = ExcelTool.getCellValue(row.getCell(0));
                String type = ExcelTool.getCellValue(row.getCell(1));
                String must = ExcelTool.getCellValue(row.getCell(2));
                String option = ExcelTool.getCellValue(row.getCell(3));
                String max = ExcelTool.getCellValue(row.getCell(4));
                if (StringUtils.isBlank(question) && StringUtils.isBlank(type) && StringUtils.isBlank(must) &&
                StringUtils.isBlank(option) && StringUtils.isBlank(max)){
                    continue;
                }
                /*********????????????*********/
                String errMsg = "";
                boolean isFirst = true; // ?????????????????????????????????????????????

                //??????????????????
                if (StringUtils.isBlank(question)){
                    errMsg += "???" + (i+1) + "????????????????????????????????????";
                    isFirst = false;
                }

                //????????????????????????????????????
                if (StringUtils.isBlank(type)){
                    if (isFirst){
                        errMsg += "???" + (i+1) + "??????????????????????????????????????????????????????????????????????????????";
                        isFirst = false;
                    }else {
                        errMsg += "???????????????????????????????????????????????????????????????";
                    }
                }else {
                    if (!"??????".equals(type) && !"??????".equals(type) && !"??????".equals(type)){
                        if (isFirst){
                            errMsg += "???" + (i+1) + "??????????????????????????????????????????????????????????????????????????????";
                            isFirst = false;
                        }else {
                            errMsg += "???????????????????????????????????????????????????????????????";
                        }
                    }
                }

                //??????????????????????????????????????????
                if (StringUtils.isBlank(must)){
                    if (isFirst){
                        errMsg += "???" + (i+1) + "??????????????????????????????????????????";
                        isFirst = false;
                    }else {
                        errMsg += "???????????????????????????";
                    }
                }else {
                    if (!"???".equals(must) && !"???".equals(must)){
                        if (isFirst){
                            errMsg += "???" + (i+1) + "????????????????????????????????????????????????";
                            isFirst = false;
                        }else {
                            errMsg += "?????????????????????????????????";
                        }
                    }
                }

                //??????????????????????????????
                if (StringUtils.isBlank(option)){
                    if (type != null && !"".equals(type) && !"??????".equals(type)){
                        if (isFirst){
                            errMsg += "???" + (i+1) + "????????????????????????????????????";
                            isFirst = false;
                        }else {
                            errMsg += "?????????????????????";
                        }
                    }
                }else {
                    //?????????????????????2
                    if ("??????".equals(type) || "??????".equals(type)){
                        String[] opt = option.split("\n");
                        if (opt.length < 2){
                            if (isFirst){
                                errMsg += "???" + (i+1) + "?????????????????????????????????????????????2";
                                isFirst = false;
                            }else {
                                errMsg += "??????????????????????????????2";
                            }
                        }
                    }
                }

                //???????????????????????????????????????,???????????????
                if (StringUtils.isNotBlank(max)){
                    if ("??????".equals(type)){
                        if (max.contains(".") || max.contains("-")){
                            if (isFirst){
                                errMsg += "???" + (i+1) + "???????????????????????????????????????????????????";
                                isFirst = false;
                            }else {
                                errMsg += "????????????????????????????????????";
                            }
                        }else {
                            String[] opt = option.split("\n");
                            Integer maxSum = Integer.valueOf(max);
                            if (maxSum > opt.length){
                                if (isFirst){
                                    errMsg += "???" + (i+1) + "????????????????????????????????????????????????????????????";
                                    isFirst = false;
                                }else {
                                    errMsg += "?????????????????????????????????????????????";
                                }
                            }
                        }
                    }
                }

                //????????????????????????????????????
                if (!"".equals(errMsg)){
                    errorMsgList.add(errMsg);
                    continue;
                }

                /*********????????????*********/
                VoteQuestion voteQuestion = new VoteQuestion();
                List<VoteOption> optionList = new ArrayList<>();
                VoteOption voteOption;

                voteQuestion.setContent(question);
                voteQuestion.setVoteId(questionnaireId);
                if ("??????".equals(type)) voteQuestion.setType("single");
                if ("??????".equals(type)) voteQuestion.setType("multiple");
                if ("???".equals(must)) voteQuestion.setMust(1);
                if ("???".equals(must)) voteQuestion.setMust(0);

                if ("??????".equals(type)){
                    voteQuestion.setType("completion");
                    voteOption = new VoteOption();
                    optionList.add(voteOption);
                }else {
                    //???????????????
                    //??????
                    String[] opts = option.split("\n");
                    for (String opt : opts){
                        voteOption = new VoteOption();
                        voteOption.setContent(opt);
                        optionList.add(voteOption);
                    }
                    //???????????????
                    Integer maxInt = StringUtils.isBlank(max) ? 1 : Integer.parseInt(max);
                    voteQuestion.setMaxChecked("??????".equals(type) ? 1 : maxInt);
                }

                voteQuestion.setOptionList(optionList);
                questionList.add(voteQuestion);
            }

            if (errorMsgList.size() > 0){
                return AjaxResult.error("????????????", errorMsgList);
            }else {
                return AjaxResult.success("???????????????????????????" + questionList.size() + "?????????", questionList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ??????????????????
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
                return AjaxResult.error("?????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return AjaxResult.success("????????????");
    }

    /**
     * ????????????/??????
     * @param fileId
     * @param type
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteFile(Integer fileId, String type, Integer id) {
        try {
            //?????????
            UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, fileId));
            //?????????uri?????????????????????
//            boolean flag = fastdfsClientUtil.deleteFile(uploadFile.getUri());
//            if (! flag){
//                return AjaxResult.error("?????????????????????????????????");
//            }
            uploadFilesMapper.delete(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.ID, fileId));

            //?????????
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

            return AjaxResult.success("????????????");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ??????????????????
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public AjaxResult getPublishList(String startTime, String endTime) {
        try {
            //?????????????????????????????????????????????
            List<Vote> voteList = voteMapper.getPublishList(startTime, endTime);

            Map<Integer, Integer> map = new HashMap<>();
            for (Vote vote : voteList){
                List<Integer> orgIds = toolUtils.getOrgTreeInt(Integer.parseInt(vote.getNewStatus()));
                if (orgIds.size() != 0) {
                    Org org = orgDao.selectOne(new QueryWrapper<Org>().
                            eq(Org.ORGLEVEL, "???????????????").
                            in(Org.ID, orgIds));
                    Integer num = map.get(org.getId());
                    map.put(org.getId(), num == null ? 1 : num + 1);
                }
            }

            //??????????????????
            List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().
                    eq(Org.ORGLEVEL, "???????????????"));
            List<ExamPublishVO> publishList = new ArrayList<>();
            for (Org org : orgList){
                ExamPublishVO publish = new ExamPublishVO();
                publish.setOrgId(org.getId());
                publish.setOrgName(org.getName());
                publish.setNum(map.get(org.getId()) == null ? 0 : map.get(org.getId()));

                publishList.add(publish);
            }

            return AjaxResult.success("????????????", publishList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????");
        }
    }

    /**
     * ????????????????????????
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

            //??????
            String name = "??????????????????" + ".xls";
            Workbook wb = getWorkbook(publishList, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return null;
    }

    /**
     * ??????????????????
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

            //????????????????????????<??????id?????????>
            Map<Integer, Integer> questionMap = new HashMap<>();
            //????????????????????????<??????id?????????>
            Map<String, Integer> countMap = new HashMap<>();
            //?????????????????????<??????id???????????????>
            Map<Integer, List<String>> completionMap = new HashMap<>();

            List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId));
            for (VotePaper paper : paperList){
                int questionNum = questionMap.get(paper.getVoteQuestionId())==null ? 0 : questionMap.get(paper.getVoteQuestionId());
                questionMap.put(paper.getVoteQuestionId(), questionNum + 1);

                if ("completion".equals(paper.getType())){
                    //??????
                    List<String> contentList = completionMap.get(paper.getVoteQuestionId())==null ? new ArrayList<>() : completionMap.get(paper.getVoteQuestionId());
                    contentList.add(paper.getSubmitContent());
                    if (paper.getSubmitContent() != null && !"".equals(paper.getSubmitContent())){
                        completionMap.put(paper.getVoteQuestionId(), contentList);
                    }
                }else if ("multiple".equals(paper.getType())){
                    //??????
                    String[] ids = paper.getSubmitContent().split(" ");
                    for (String id : ids){
                        int num = countMap.get(id)==null ? 0 : countMap.get(id);
                        countMap.put(id, num + 1);
                    }
                }else {
                    //??????
                    int num = countMap.get(paper.getSubmitContent())==null ? 0 : countMap.get(paper.getSubmitContent());
                    countMap.put(paper.getSubmitContent(), num + 1);
                }
            }

            for (VoteQuestion question : questionList){
                //?????????????????????
                question.setCount(questionMap.get(question.getId())==null ? 0 : questionMap.get(question.getId()));
                DecimalFormat df = new DecimalFormat("#.0000");

                //????????????
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
                        //???????????????
                        double scale = Double.parseDouble(df.format((double) num / (double)question.getCount()));
                        option.setScale(scale);
                    }
                }
                question.setOptionList(optionList);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questionnaire", questionnaire);
            jsonObject.put("question", questionList);

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????????????????
     * @param questionnaireId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportPaperData(Integer questionnaireId, HttpServletResponse response) {
        try {
            //????????????
            List<VotePaper> paperUsers = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, questionnaireId).
                    select("distinct voteId, userId"));
            List<ExamPaperExportDO> exportDOs =voteMapper.getUserExportData(paperUsers);

            for (ExamPaperExportDO exportDO : exportDOs) {
                //????????????????????????
                exportDO.setSex("male".equals(exportDO.getGender()) ? "???" : "???");
                Org org = orgDao.selectOne(new QueryWrapper<Org>().
                        eq(Org.ID, exportDO.getOrgId()));
                exportDO.setOrgName(org.getName());
                String orgNameDetail = toolUtils.getOrgName(exportDO.getOrgId());
                exportDO.setOrgNameDetail(orgNameDetail);

                //????????????????????????
                VotePaper quPaper = votePaperMapper.selectOne(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, questionnaireId).
                        eq(VotePaper.USERID, exportDO.getUserId()).
                        orderByAsc(VotePaper.SUBMITFLAG).
                        last("limit 1"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(quPaper.getSubmitDate());
                exportDO.setTime(date);
                exportDO.setImg(quPaper.getSignatureImg());

                List<VotePaper> quPaperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, questionnaireId).
                        eq(VotePaper.USERID, exportDO.getUserId()));
                List<QuestionAndPaperDO> paperDOList = new ArrayList<>();
                for (VotePaper paper : quPaperList) {
                    //??????????????????
                    QuestionAndPaperDO qap = new QuestionAndPaperDO();
                    VoteQuestion question = voteQuestionMapper.selectOne(new QueryWrapper<VoteQuestion>().
                            eq(VoteQuestion.ID, paper.getVoteQuestionId()));
                    qap.setQuestion(question.getContent());
                    if ("completion".equals(paper.getType())) {
                        //??????
                        qap.setContent(paper.getSubmitContent());
                    } else if ("multiple".equals(paper.getType())) {
                        //??????
                        String[] ids = paper.getSubmitContent().split(" ");
                        String content = "";
                        for (String id : ids) {
                            VoteOption option = voteOptionMapper.selectOne(new QueryWrapper<VoteOption>().
                                    eq(VoteOption.ID, id));
                            content += option.getContent() + " ";
                        }
                        qap.setContent(content);
                    } else {
                        //??????
                        VoteOption option = voteOptionMapper.selectOne(new QueryWrapper<VoteOption>().
                                eq(VoteOption.ID, paper.getSubmitContent()));
                        qap.setContent(option == null ? "" : option.getContent());
                    }
                    paperDOList.add(qap);
                }
                exportDO.setPaperList(paperDOList);
            }

            //??????
            String name = "??????????????????" + ".xls";
            Workbook wb = getWorkbookPaper(exportDOs, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return null;
    }

    /**
     * ????????????????????????
     * @param questionnaireId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportAnalyse(Integer questionnaireId, HttpServletResponse response) {
        try {
            //????????????
            AjaxResult result = analyseQuestionnaire(questionnaireId);
            JSONObject jsonObject = (JSONObject) result.get("data");
            Vote qu = jsonObject.getObject("questionnaire", Vote.class);
            JSONArray array = jsonObject.getJSONArray("question");
            String str = array.toJSONString();
            List<VoteQuestion> questionList = JSONArray.parseArray(str, VoteQuestion.class);

            //????????????
            VoteAnalyseExportDO analyse = new VoteAnalyseExportDO();
            //?????????????????????
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

            //??????
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = analyse.getTitle() + sdf.format(date) + ".xls";
            Workbook wb = getWorkbook(analyse, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return null;
    }

    /**
     * ?????????????????????????????????
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
            //???????????????????????????
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
                member.setSex("male".equals(member.getSex()) ? "???" : "???");
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

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????????????????
     * @param questionnaireId
     * @param orgId
     * @param response
     * @return
     */
    @Override
    public AjaxResult exportMember(Integer questionnaireId, Integer orgId, HttpServletResponse response) {
        try {
            //????????????
            AjaxResult result = getMember(questionnaireId, orgId, null, null);
            JSONObject jsonObject = (JSONObject) result.get("data");
            JSONArray array = jsonObject.getJSONArray("list");
            String str = array.toJSONString();
            List<MemberInfoVO> memberList = JSONArray.parseArray(str, MemberInfoVO.class);

            //??????
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = "???????????????????????????" + sdf.format(date) + ".xls";
            Workbook wb = getWorkbookMember(memberList, null);
            ExcelTool.export(wb, name, response);

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
        return null;
    }

    /**
     * ????????????????????????
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
                //??????????????????
                List<VotePaper> paperList = votePaperMapper.selectList(new QueryWrapper<VotePaper>().
                        eq(VotePaper.VOTEID, qu.getId()).
                        eq(VotePaper.USERID, userId).
                        eq(VotePaper.SUBMITFLAG, 1));
                //???????????????
                boolean isExam = false;
                if (paperList != null && paperList.size() > 0){
                    isExam = true;
                }
                //????????????????????????
                Date endTime = qu.getEndTime();
                //???????????????
                boolean isLate = endTime.before(new Date());

                if (isExam){
                    qu.setNewStatus("?????????");
                }else if (isLate){
                    qu.setNewStatus("?????????");
                }else {
                    qu.setNewStatus("?????????");
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", quList);

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ????????????????????????
     * @param questionnaireId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getTopDetail(Integer questionnaireId, Integer userId) {
        try {
            //???????????????
            Vote qu = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, questionnaireId));
            //????????????
            List<VoteQuestion> questionList = voteQuestionMapper.selectList(new QueryWrapper<VoteQuestion>().
                    eq(VoteQuestion.VOTEID, questionnaireId));
            for (VoteQuestion question : questionList){
                //????????????
                List<VoteOption> optionList = voteOptionMapper.selectList(new QueryWrapper<VoteOption>().
                        eq(VoteOption.VOTEQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }
            //????????????????????????
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

            return AjaxResult.success("????????????", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }

    /**
     * ??????????????????
     * @param paperList
     * @return
     */
    @Override
    public AjaxResult submitQu(List<VotePaper> paperList) {
        try {
            UserProfile profile = userProfileDao.selectOne(new QueryWrapper<UserProfile>().
                    eq(UserProfile.ID, paperList.get(0).getUserId()));
            //??????????????????????????????
            VotePaper paper0 = votePaperMapper.selectOne(new QueryWrapper<VotePaper>().
                    eq(VotePaper.VOTEID, paperList.get(0).getVoteId()).
                    eq(VotePaper.USERID, paperList.get(0).getUserId()).
                    orderByDesc(VotePaper.SUBMITFLAG).
                    last("limit 1"));
            Integer count = paper0 == null ? 0 : paper0.getSubmitFlag();

            Vote qu = voteMapper.selectOne(new QueryWrapper<Vote>().
                    eq(Vote.ID, paperList.get(0).getVoteId()));
            if (count >= qu.getMaxSubmit()){
                return AjaxResult.error("??????????????????????????????????????????");
            }else {
                count ++;
            }

            Date date = new Date();
            for (VotePaper paper : paperList){
                paper.setSubmitDate(date);
                paper.setTel(profile.getMobile());
                paper.setSubmitFlag(count);
                votePaperMapper.insert(paper);
            }

            return AjaxResult.success("????????????");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("?????????????????????????????????", e.getMessage());
        }
    }


    /**
     * ??????????????????????????????
     * @param publishList
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(List<ExamPublishVO> publishList, HttpServletRequest request) throws Exception{
        //????????????
        Workbook wb = ExcelTool.read(request, "/static/excel/????????????????????????.xls");
        //??????sheet
        Sheet sheet = wb.getSheetAt(0);
        //????????????????????????style
        CellStyle style = ExcelTool.getStyle(wb);

        //???????????????excel
        int r = 1;
        Row row = null;
        for (ExamPublishVO publish : publishList){
            //????????????
            row = sheet.createRow(r++);
            //??????????????????
            ExcelTool.createCell(row, 0, style, publish.getOrgName());
            ExcelTool.createCell(row, 1, style, publish.getNum());
        }

        return wb;
    }

    /**
     * ????????????????????????
     * @param exportList
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbookPaper(List<ExamPaperExportDO> exportList, HttpServletRequest request) throws Exception{
        //????????????
        Workbook wb = ExcelTool.read(request, "/static/excel/??????????????????????????????.xls");
        //??????sheet
        Sheet sheet = wb.getSheetAt(0);
        //????????????????????????style
        CellStyle style = ExcelTool.getStyle(wb);
        //????????????
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);

        //???????????????excel
        int r = 1;
        Row row0 = sheet.createRow(0);
        ExcelTool.createCell(row0, 0, style, "??????");
        ExcelTool.createCell(row0, 1, style, "??????");
        ExcelTool.createCell(row0, 2, style, "????????????");
        ExcelTool.createCell(row0, 3, style, "????????????");
        ExcelTool.createCell(row0, 4, style, "????????????");
        ExcelTool.createCell(row0, 5, style, "??????");
        ExcelTool.createCell(row0, 6, style, "????????????");
        ExcelTool.createCell(row0, 7, style, "????????????");

        for (ExamPaperExportDO export : exportList) {
            Row row = sheet.createRow(r++);
            //??????????????????
            ExcelTool.createCell(row, 0, style, export.getName());
            ExcelTool.createCell(row, 1, style, export.getSex());
            ExcelTool.createCell(row, 2, style, export.getMobile());
            ExcelTool.createCell(row, 3, style, export.getOrgName());
            ExcelTool.createCell(row, 4, style, export.getTime());
            ExcelTool.createCell(row, 5, style, export.getImg());
            ExcelTool.createCell(row, 6, style, export.getEmploymentForm());
            ExcelTool.createCell(row, 7, style, export.getOrgNameDetail());

            int i = 8;
            for (QuestionAndPaperDO paperDO : export.getPaperList()) {
                ExcelTool.createCell(row0, i, style, paperDO.getQuestion());
                ExcelTool.createCell(row, i, style, paperDO.getContent());
                i++;
            }
        }

        return wb;
    }

    /**
     * ??????????????????
     * @param export
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(VoteAnalyseExportDO export, HttpServletRequest request) throws Exception{
        Workbook wb = new HSSFWorkbook();
        //??????sheet
        Sheet sheet = wb.createSheet();
        //????????????????????????style
        CellStyle style = ExcelTool.getStyle(wb);
        //????????????
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);
        //?????????
		style.setBorderBottom(BorderStyle.THIN);
		//?????????
		style.setBorderLeft(BorderStyle.THIN);
		//?????????
		style.setBorderTop(BorderStyle.THIN);
		//?????????
		style.setBorderRight(BorderStyle.THIN);
		//????????????
        sheet.setColumnWidth(0, 252*30+323);
        sheet.setColumnWidth(1, 252*18+323);
        sheet.setColumnWidth(2, 252*18+323);

        //???????????????excel
        int r = 0;
        Row row0 = sheet.createRow(r++);
        ExcelTool.createCell(row0, 0, style, export.getTitle());
        ExcelTool.createCell(row0, 1, style, export.getTitle());
        ExcelTool.createCell(row0, 2, style, export.getTitle());
        //???????????????
        CellRangeAddress region0 = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(region0);

        Row row1 = sheet.createRow(r++);
        ExcelTool.createCell(row1, 2, style, "???????????????" + export.getNum() + "???");
        int i = 1;
        for (VoteQuestion question : export.getQuestionList()){
            //?????????????????????
            CellStyle style1 = wb.createCellStyle();
            style1.cloneStyleFrom(style);
            style1.setFillForegroundColor(IndexedColors.GOLD.getIndex());
            style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style1.setAlignment(HorizontalAlignment.LEFT);
            Row rowQ = sheet.createRow(r++);
            ExcelTool.createCell(rowQ, 0, style1, i + "." + question.getContent());
            ExcelTool.createCell(rowQ, 1, style1, i + "." + question.getContent());
            ExcelTool.createCell(rowQ, 2, style1, i + "." + question.getContent());
            //??????
            CellRangeAddress region = new CellRangeAddress(r-1, r-1, 0, 2);
            sheet.addMergedRegion(region);

            //???????????????????????????????????????
            CellStyle style2 = wb.createCellStyle();
            style2.cloneStyleFrom(style);
            style2.setAlignment(HorizontalAlignment.LEFT);
            //????????????????????????????????????????????????
            if ("completion".equals(question.getType())){
                for (String content : question.getOptionList().get(0).getContentList()){
                    Row row = sheet.createRow(r++);
                    ExcelTool.createCell(row, 0, style2, content);
                    ExcelTool.createCell(row, 1, style2, content);
                    ExcelTool.createCell(row, 2, style2, content);
                    //??????
                    CellRangeAddress region1 = new CellRangeAddress(r-1, r-1, 0, 2);
                    sheet.addMergedRegion(region1);
                }
            }else {
                Row rowO = sheet.createRow(r++);
                ExcelTool.createCell(rowO, 0, style, "??????");
                ExcelTool.createCell(rowO, 1, style, "??????");
                ExcelTool.createCell(rowO, 2, style, "??????");
                char a = 'A';
                for (VoteOption option : question.getOptionList()){
                    a = (char) (a + 1);
                    BigDecimal scale = new BigDecimal(option.getScale());
                    scale = scale.multiply(new BigDecimal(100));
                    String str = scale.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                    Row row = sheet.createRow(r++);
                    ExcelTool.createCell(row, 0, style2, a + "." + option.getContent());
                    ExcelTool.createCell(row, 1, style, option.getCount());
                    ExcelTool.createCell(row, 2, style, str + "%");
                }
            }

            i++;
        }

        return wb;
    }

    /**
     * ??????????????????
     * @param memberList
     * @param request
     * @return
     * @throws Exception
     */
    private Workbook getWorkbookMember(List<MemberInfoVO> memberList, HttpServletRequest request) throws Exception{
        Workbook wb = new HSSFWorkbook();
        //??????sheet
        Sheet sheet = wb.createSheet();
        //????????????????????????style
        CellStyle style = ExcelTool.getStyle(wb);
        //????????????
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        style.setFont(font);
        //????????????
        sheet.setColumnWidth(0, 252*25+323);
        sheet.setColumnWidth(1, 252*25+323);
        sheet.setColumnWidth(2, 252*25+323);
        sheet.setColumnWidth(3, 252*25+323);
        sheet.setColumnWidth(4, 252*25+323);

        //???????????????excel
        int r = 0;
        Row row0 = sheet.createRow(r++);
        ExcelTool.createCell(row0, 0, style, "??????");
        ExcelTool.createCell(row0, 1, style, "??????");
        ExcelTool.createCell(row0, 2, style, "????????????");
        ExcelTool.createCell(row0, 3, style, "????????????");
        ExcelTool.createCell(row0, 4, style, "????????????");
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

package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.*;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import com.ruoyi.project.democratic.mapper.*;
import com.ruoyi.project.democratic.service.IExamOptionService;
import com.ruoyi.project.democratic.service.IExamQuestionService;
import com.ruoyi.project.democratic.service.IExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-04-15
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamQuestionMapper examQuestionMapper;
    @Autowired
    private ExamOptionMapper examOptionMapper;
    @Autowired
    private ExamQuestionTypeMapper examQuestionTypeMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;

    @Autowired
    private IExamQuestionService examQuestionService;
    @Autowired
    private IExamOptionService examOptionService;

    /**
     * 新增考试/创建考试
     * @param exam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertExam(Exam exam) {
        try {
            //新增考试
            exam.setCreateDate(new Date());
            exam.setUpdateDate(new Date());
            save(exam);

            //新增考试分值
            String[] typeName = {"single", "multiple", "judge", "completion"};
            for (String type : typeName){
                ExamQuestionType questionType = new ExamQuestionType();
                questionType.setExamId(exam.getId());
                questionType.setScore(0);
                questionType.setType(type);
                examQuestionTypeMapper.insert(questionType);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("创建失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("创建成功");
    }

    /**
     * 条件查询后台列表
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackExamList(String title, Integer pageNum, Integer pageSize) {
        try {
            title = Str.fuzzyQuery(title);
            PageHelper.startPage(pageNum, pageSize);
            List<Exam> examList = examMapper.getBackExamList(title);
            PageInfo pageInfo = new PageInfo<>(examList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", pageInfo.getList());
            jsonObject.put("total", pageInfo.getTotal());

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台发布考试
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult publish(Integer examId, Integer userId) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(userId);
            exam.setStatus("published");
            updateById(exam);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发布失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发布成功");
    }

    /**
     * 后台取消发布
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult unpublish(Integer examId, Integer userId) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            if (!"published".equals(exam.getStatus())){
                return AjaxResult.error("该考试尚未发布，不能取消");
            }
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(userId);
            exam.setStatus("unpublished");
            updateById(exam);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("取消失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("取消成功");
    }

    /**
     * 后台回收考试
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult over(Integer examId, Integer userId) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            if (!"published".equals(exam.getStatus())){
                return AjaxResult.error("该考试尚未发布，不能回收");
            }
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(userId);
            exam.setStatus("over");
            updateById(exam);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("回收失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("回收成功");
    }

    /**
     * 后台删除考试
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult deleteBack(Integer examId, Integer userId) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(userId);
            exam.setIsShow(0);
            updateById(exam);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 后台重命名
     * @param examId
     * @param userId
     * @param title
     * @return
     */
    @Override
    public AjaxResult rename(Integer examId, Integer userId, String title) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(userId);
            exam.setTitle(title);
            updateById(exam);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("重命名失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("重命名成功");
    }

    /**
     * 根据id查询详情
     * @param examId
     * @return
     */
    @Override
    public AjaxResult getDetailById(Integer examId) {
        try {
            //查考试信息
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            //转义富文本
            String text = null;
            if (exam.getDescription() != null && !"".equals(exam.getDescription())){
                text = HtmlUtils.htmlUnescape(exam.getDescription());
            }
            exam.setDescription(text);
            //查题目信息
            List<ExamQuestion> questionList = examQuestionMapper.selectList(new QueryWrapper<ExamQuestion>().
                    eq(ExamQuestion.EXAMID, examId));
            for (ExamQuestion question : questionList) {
                //查选项信息
                List<ExamOption> optionList = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                        eq(ExamOption.EXAMQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }
            //查分值信息
            List<ExamQuestionType> typeList = examQuestionTypeMapper.selectList(new QueryWrapper<ExamQuestionType>().
                    eq(ExamQuestionType.EXAMID, examId));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("exam", exam);
            jsonObject.put("examQuestion", questionList);
            jsonObject.put("questionType", typeList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 更新基础信息
     * @param examBase
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateBaseData(ExamBaseVO examBase) {
        try {
            if (examBase.getTypeList() != null) {
                for (ExamQuestionType type : examBase.getTypeList()) {
                    type.setExamId(examBase.getExam().getId());
                    examQuestionTypeMapper.updateById(type);
                }
            }
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examBase.getExam().getId()));
            exam.setUpdateDate(new Date());
            exam.setUpdateUserId(examBase.getExam().getUpdateUserId());
            exam.setEntranceStartDate(examBase.getExam().getEntranceStartDate());
            exam.setEntranceEndDate(examBase.getExam().getEntranceEndDate());
            exam.setDuration(examBase.getExam().getDuration());
            exam.setMaxSubmit(examBase.getExam().getMaxSubmit());
            updateById(exam);

            return AjaxResult.success("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("更新失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 编辑标题、封面
     * @param exam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateExam(Exam exam) {
        try {
            Exam exam1 = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, exam.getId()));
            exam1.setTitle(exam.getTitle());
            exam1.setIsSigned(exam.getIsSigned());
            exam1.setUpdateUserId(exam.getUpdateUserId());
            exam1.setUpdateDate(new Date());
            //富文本数据存储转换
            String text = null;
            if (exam.getDescription() != null && !"".equals(exam.getDescription())) {
                text = HtmlUtils.htmlEscape(exam.getDescription());
            }
            exam1.setDescription(text);

            //处理封面文件
            if (exam.getCoverId() != null){
                UploadFiles uploadFile = uploadFilesMapper.selectOne(new QueryWrapper<UploadFiles>().
                        eq(UploadFiles.ID, exam.getCoverId()));
                uploadFile.setTargetId(exam.getId());
                uploadFile.setTargetType("exam_cover");
                uploadFilesMapper.updateById(uploadFile);
                exam1.setCoverUrl(uploadFile.getUri());
                exam1.setCoverId(exam.getCoverId());
            }

            updateById(exam1);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("编辑失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("编辑成功");
    }

    /**
     * 后台新增题目
     * @param question
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertQuestion(ExamQuestion question) {
        try {
            //保存题目
            examQuestionMapper.insert(question);
            //保存选项
            for (ExamOption option : question.getOptionList()) {
                option.setExamQuestionId(question.getId());
                examOptionMapper.insert(option);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateQuestion(ExamQuestion question) {
        try {
            examQuestionMapper.updateById(question);
            for (ExamOption option : question.getOptionList()){
                if (option.getId() != null) {
                    examOptionMapper.updateById(option);
                }else {
                    examOptionMapper.insert(option);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult deleteQuestionOrOption(Integer questionId, Integer optionId) {
        try {
            //删选项
            if (optionId != null){
                examOptionMapper.deleteById(optionId);
            }
            //删题目
            if (questionId != null){
                examQuestionMapper.deleteById(questionId);
                examOptionMapper.delete(new QueryWrapper<ExamOption>().
                        eq(ExamOption.EXAMQUESTIONID, questionId));
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 后台校验导入
     * @param file
     * @param examId
     * @param request
     * @return
     */
    @Override
    public AjaxResult checkImportQuestion(MultipartFile file, Integer examId, HttpServletRequest request) {
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
            List<ExamQuestion> questionList = new ArrayList<>();
            for (int i = rowStart; i < rowEnd; i++){
                Row row = sheet.getRow(i);

                //必填项校验
                String question = ExcelTool.getCellValue(row.getCell(0));
                String type = ExcelTool.getCellValue(row.getCell(1));
                String option = ExcelTool.getCellValue(row.getCell(2));
                String answer = ExcelTool.getCellValue(row.getCell(3));
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
                        errMsg += "第" + (i+1) + "行信息有误，题型不能为空，题型只能为单选，多选，判断或文本";
                        isFirst = false;
                    }else {
                        errMsg += "、题型不能为空，题型只能为单选，多选，判断或文本";
                    }
                }else {
                    if (!"单选".equals(type) && !"多选".equals(type) && !"文本".equals(type) && !"判断".equals(type)){
                        if (isFirst){
                            errMsg += "第" + (i+1) + "行信息有误，题型名字有误，题型只能为单选，多选，判断或文本";
                            isFirst = false;
                        }else {
                            errMsg += "、题型名字有误，题型只能为单选，多选，判断或文本";
                        }
                    }
                }

                //单选、多选选项不能为空
                if (StringUtils.isBlank(option)){
                    if (type != null && !"".equals(type) && !"文本".equals(type) && !"判断".equals(type)){
                        if (isFirst){
                            errMsg += "第" + (i+1) + "行信息有误，选项不能为空";
                            isFirst = false;
                        }else {
                            errMsg += "、选项不能为空";
                        }
                    }
                }else {
                    String[] opt = option.split("\n");
                    if ("单选".equals(type) || "多选".equals(type)){
                        //单选、多选选项必须大于等于2
                        if (opt.length < 2){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，选项个数应大于等于2";
                                isFirst = false;
                            }else {
                                errMsg += "、选项个数应大于等于2";
                            }
                        }
                    }else if ("判断".equals(type)) {
                        //判断题只能有2个选项
                        if (opt.length != 2){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，判断题选项应为2个";
                                isFirst = false;
                            }else {
                                errMsg += "、判断题选项应为2个";
                            }
                        }
                    }
                }

                //填空（文本）答案不能为空
                if (StringUtils.isBlank(answer)){
                    if ("文本".equals(type)) {
                        if (isFirst) {
                            errMsg += "第" + (i+1) + "行信息有误，填空题答案必填";
                            isFirst = false;
                        } else {
                            errMsg += "、填空题答案必填";
                        }
                    }
                }else {
                    String[] ans = answer.split(",");
                    String[] opt = option==null ? new String[0] : option.split("\n");
                    //单选
                    if ("单选".equals(type)){
                        //单选答案个数只能为1
                        if (ans.length != 1){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，单选题答案个数应为1";
                                isFirst = false;
                            }else {
                                errMsg += "、单选题答案个数应为1";
                            }
                        }
                        //单选答案不能超出选项个数范围
                        for (String a : ans){
                            if (Integer.parseInt(a) > opt.length){
                                if (isFirst){
                                    errMsg += "第" + (i+1) + "行信息有误，答案不在选项中";
                                    isFirst = false;
                                }else {
                                    errMsg += "、答案不在选项中";
                                }
                                break;
                            }
                        }
                    }
                    //多选
                    if ("多选".equals(type)){
                        if (ans.length < 2){
                            //多选答案应大于等于2
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，多选题答案数应大于等于2";
                                isFirst = false;
                            }else {
                                errMsg += "、多选题答案数应大于等于2";
                            }
                        }else {
                            if (ans.length > opt.length){
                                //多选答案个数不能大于选项个数
                                if (isFirst){
                                    errMsg += "第" + (i+1) + "行信息有误，多选题答案个数大于选项个数";
                                    isFirst = false;
                                }else {
                                    errMsg += "、多选题答案个数大于选项个数";
                                }
                            }
                        }
                        //答案不应超出选项范围
                        for (String a : ans){
                            if (Integer.parseInt(a) > opt.length){
                                if (isFirst){
                                    errMsg += "第" + (i+1) + "行信息有误，答案不在选项中";
                                    isFirst = false;
                                }else {
                                    errMsg += "、答案不在选项中";
                                }
                                break;
                            }
                        }
                    }
                    //判断
                    if ("判断".equals(type)){
                        //判断题答案只能为1
                        if (ans.length != 1){
                            if (isFirst){
                                errMsg += "第" + (i+1) + "行信息有误，判断题答案数应为1";
                                isFirst = false;
                            }else {
                                errMsg += "、判断题答案数应为1";
                            }
                        }
                        //判断题答案不能超过选项范围
                        for (String a : ans){
                            if (Integer.parseInt(a) > opt.length){
                                if (isFirst){
                                    errMsg += "第" + (i+1) + "行信息有误，答案不在选项中";
                                    isFirst = false;
                                }else {
                                    errMsg += "、答案不在选项中";
                                }
                                break;
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
                ExamQuestion examQuestion = new ExamQuestion();
                List<ExamOption> optionList = new ArrayList<>();
                ExamOption examOption;
                examQuestion.setExamId(examId);
                examQuestion.setContent(question);
                if ("单选".equals(type)) examQuestion.setType("single");
                if ("多选".equals(type)) examQuestion.setType("multiple");
                if ("判断".equals(type)) examQuestion.setType("judge");

                if ("文本".equals(type)){
                    //填空题
                    examQuestion.setType("completion");
                    String[] answers = answer.split(",");
                    for (String ans : answers){
                        examOption = new ExamOption();
                        examOption.setContent(ans);
                        examOption.setIsAnswer(1);
                        optionList.add(examOption);
                    }
                }else {
                    //单选、多选、判断题
                    //选项
                    if (StringUtils.isBlank(option)) {
                        if ("判断".equals(type)) {
                            examOption = new ExamOption();
                            examOption.setContent("对");
                            optionList.add(examOption);
                            examOption = new ExamOption();
                            examOption.setContent("错");
                            optionList.add(examOption);
                        }
                    }else {
                        String[] options = option.split("\n");
                        for (String opt : options){
                            examOption = new ExamOption();
                            examOption.setContent(opt);
                            optionList.add(examOption);
                        }
                    }
                    //答案
                    if (StringUtils.isBlank(answer)){
                        //不填答案默认第一个
                        optionList.get(0).setIsAnswer(1);
                    }else {
                        String[] answers = answer.split(",");
                        for (String ans : answers){
                            int j = Integer.parseInt(ans);
                            optionList.get(j - 1).setIsAnswer(1);
                        }
                    }
                }

                examQuestion.setOptionList(optionList);
                questionList.add(examQuestion);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult importQuestion(List<ExamQuestion> questionList) {
        try {
            boolean question = examQuestionService.insertQuestionList(questionList);
            boolean option = examOptionService.insertOptions(questionList);
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

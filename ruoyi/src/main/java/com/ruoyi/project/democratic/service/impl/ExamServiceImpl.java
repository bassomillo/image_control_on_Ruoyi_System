package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.*;
import com.ruoyi.project.democratic.entity.DO.ExamPaperExportDO;
import com.ruoyi.project.democratic.entity.DO.QuestionAndPaperDO;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import com.ruoyi.project.democratic.entity.VO.ExamPublishVO;
import com.ruoyi.project.democratic.entity.VO.ExamSaveVO;
import com.ruoyi.project.democratic.mapper.*;
import com.ruoyi.project.democratic.service.IExamOptionService;
import com.ruoyi.project.democratic.service.IExamQuestionService;
import com.ruoyi.project.democratic.service.IExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.org.entity.Org;
import com.ruoyi.project.org.mapper.OrgDao;
import com.ruoyi.project.tool.ExcelTool;
import com.ruoyi.project.tool.Str;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
    private ExamMemberMapper examMemberMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;
    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Autowired
    private ExamSaveMapper examSaveMapper;
    @Autowired
    private OrgDao orgDao;

    @Autowired
    private IExamQuestionService examQuestionService;
    @Autowired
    private IExamOptionService examOptionService;

    @Resource
    private FastdfsClientUtil fastdfsClientUtil;
    @Resource
    private ToolUtils toolUtils;

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

    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    @Override
    public AjaxResult upload(MultipartFile file, Integer userId) {
        try {
            //上传至服务器
//            String url = fastdfsClientUtil.uploadFile(file);
            String url = null;

            //保存文件信息至数据库
            Integer size = (int) file.getSize();
            UploadFiles uploadFiles = new UploadFiles();
            uploadFiles.setFileSize(size);
            String name = file.getOriginalFilename();
            uploadFiles.setFilename(name);
            uploadFiles.setUri(url);
            uploadFiles.setExt(name.substring(name.indexOf(".") + 1));
            uploadFiles.setCreatedUserId(userId);
            uploadFiles.setUpdatedUserId(userId);
            long t = System.currentTimeMillis() / 1000;
            Integer time = Integer.valueOf(Long.toString(t));
            uploadFiles.setCreatedTime(time);
            uploadFiles.setUpdatedTime(time);
            uploadFilesMapper.insert(uploadFiles);

            return AjaxResult.success("上传成功", uploadFiles);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("上传失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param fileId
     * @param type
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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
            if ("exam".equals(type)){
                Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                        eq(Exam.ID, id));
                exam.setCoverId(null);
                exam.setCoverUrl(null);
                updateById(exam);
            }else if ("examQuestion".equals(type)){
                ExamQuestion examQuestion = examQuestionMapper.selectOne(new QueryWrapper<ExamQuestion>().
                        eq(ExamQuestion.ID, id));
                examQuestion.setImgId(null);
                examQuestion.setImgUrl(null);
                examQuestionMapper.updateById(examQuestion);
            }else if ("examOption".equals(type)){
                ExamOption examOption = examOptionMapper.selectOne(new QueryWrapper<ExamOption>().
                        eq(ExamOption.ID, id));
                examOption.setImgId(null);
                examOption.setImgUrl(null);
                examOptionMapper.updateById(examOption);
            }

            return AjaxResult.success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台统计结果
     * @param examId
     * @return
     */
    @Override
    public AjaxResult analyseExam(Integer examId) {
        try {
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            List<ExamQuestion> questionList = examQuestionMapper.selectList(new QueryWrapper<ExamQuestion>().
                    eq(ExamQuestion.EXAMID, examId));

            //计算题目出现次数<题目id，数量>
            Map<Integer, Integer> questionMap = new HashMap<>();
            //计算选项出现次数<选项id，数量>
            Map<String, Integer> countMap = new HashMap<>();
            //计算填空题出现次数<题目id+内容+第i个空，数量>
            Map<String, Integer> completionMap = new HashMap<>();

            List<ExamPaper> paperList = examPaperMapper.selectList(new QueryWrapper<ExamPaper>().
                    eq(ExamPaper.EXAMID, examId));
            for (ExamPaper paper : paperList){
                int questionNum = questionMap.get(paper.getExamQuestionId())==null ? 0 : questionMap.get(paper.getExamQuestionId());
                questionMap.put(paper.getExamQuestionId(), questionNum + 1);

                if ("completion".equals(paper.getType())){
                    //填空
                    String[] contents = paper.getSubmitContent().split(" ", -1);
                    for (int i=0; i<contents.length; i++){
                        String str = paper.getExamQuestionId() + "+" + contents[i] + "+" + (i+1);
                        int num = completionMap.get(str)==null ? 0 : completionMap.get(str);
                        completionMap.put(str, num + 1);
                    }
                }else if ("multiple".equals(paper.getType())){
                    //多选
                    String[] ids = paper.getSubmitContent().split(" ");
                    for (String id : ids){
                        int num = countMap.get(id)==null ? 0 : countMap.get(id);
                        countMap.put(id, num + 1);
                    }
                }else {
                    //单选/判断
                    int num = countMap.get(paper.getSubmitContent())==null ? 0 : countMap.get(paper.getSubmitContent());
                    countMap.put(paper.getSubmitContent(), num + 1);
                }
            }

            for (ExamQuestion question : questionList){
                //查找此题目数量
                question.setCount(questionMap.get(question.getId())==null ? 0 : questionMap.get(question.getId()));
                DecimalFormat df = new DecimalFormat("#.0000");

                //查找选项
                List<ExamOption> optionList = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                        eq(ExamOption.EXAMQUESTIONID, question.getId()));
                if ("completion".equals(question.getType())){
                    for (int i=0; i<optionList.size(); i++){
                        String str = question.getId() + "+" + optionList.get(i).getContent() + "+" + (i+1);
                        int num = completionMap.get(str)==null ? 0 : completionMap.get(str);
                        optionList.get(i).setCount(num);
                        //计算百分比
                        double scale = Double.parseDouble(df.format((double)num / (double)question.getCount()));
                        optionList.get(i).setScale(scale);
                    }
                }else {
                    for (ExamOption option : optionList) {
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
            jsonObject.put("exam", exam);
            jsonObject.put("question", questionList);

            return AjaxResult.success("统计成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("统计失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 后台导出答题数据
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult exportPaperData(Integer examId, Integer userId, HttpServletResponse response) {
        try {
            //获取数据
            ExamPaperExportDO exportDO =examMapper.getUserExportData(userId);

            //设置用户基础信息
            exportDO.setSex("male".equals(exportDO.getGender()) ? "男" : "女");
            Org org = orgDao.selectOne(new QueryWrapper<Org>().
                    eq(Org.ID, exportDO.getOrgId()));
            exportDO.setOrgName(org.getName());
            String orgNameDetail = toolUtils.getOrgName(exportDO.getOrgId());
            exportDO.setOrgNameDetail(orgNameDetail);

            //设置用户考试信息
            ExamPaper examPaper = examPaperMapper.selectOne(new QueryWrapper<ExamPaper>().
                    eq(ExamPaper.EXAMID, examId).
                    eq(ExamPaper.USERID, userId).
                    orderByDesc(ExamPaper.SUBMITFLAG).
                    last("limit 1"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(examPaper.getSubmitDate());
            exportDO.setTime(date);
            exportDO.setImg(examPaper.getSignatureImg());
            int flag = examPaper.getSubmitFlag();
            //计算得分
            List<ExamPaper> examPaperList = examPaperMapper.selectList(new QueryWrapper<ExamPaper>().
                    eq(ExamPaper.EXAMID, examId).
                    eq(ExamPaper.USERID, userId));
            double score = 0;
            List<QuestionAndPaperDO> paperDOList = new ArrayList<>();
            for (ExamPaper paper : examPaperList){
                //计算得分
                if (paper.getSubmitFlag() == flag){
                    score += paper.getScore().doubleValue();
                }
                //查询答题信息
                QuestionAndPaperDO qap = new QuestionAndPaperDO();
                ExamQuestion question = examQuestionMapper.selectOne(new QueryWrapper<ExamQuestion>().
                        eq(ExamQuestion.ID, paper.getExamQuestionId()));
                qap.setQuestion(question.getContent());
                if ("completion".equals(paper.getType())){
                    //填空
                    qap.setContent(paper.getSubmitContent());
                }else if ("multiple".equals(paper.getType())){
                    //多选
                    String[] ids = paper.getSubmitContent().split(" ");
                    String content = "";
                    for (String id : ids){
                        ExamOption option = examOptionMapper.selectOne(new QueryWrapper<ExamOption>().
                                eq(ExamOption.ID, id));
                        content += option.getContent() + " ";
                    }
                    qap.setContent(content);
                }else {
                    //单选/判断
                    ExamOption option = examOptionMapper.selectOne(new QueryWrapper<ExamOption>().
                            eq(ExamOption.ID, paper.getSubmitContent()));
                    qap.setContent(option==null ? "" : option.getContent());
                }
                paperDOList.add(qap);
            }
            exportDO.setScore(score);
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
     * 后台发布情况
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public AjaxResult getPublishList(String startTime, String endTime) {
        try {
            //查出时间段内的考试，默认查全部
            List<Exam> examList = examMapper.getPublishList(startTime, endTime);

            Map<Integer, Integer> map = new HashMap<>();
            for (Exam exam : examList){
                List<Integer> orgIds = toolUtils.getOrgTreeInt(Integer.parseInt(exam.getNewStatus()));
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
     * 首页查询列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopExamList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            List<ExamMember> memberList = examMemberMapper.selectList(new QueryWrapper<ExamMember>().
                    eq(ExamMember.USERID, userId));

            List<Exam> examList = new ArrayList<>();
            long total = 0;
            if (memberList != null && memberList.size() > 0) {
                List<Integer> examIdList = memberList.stream().map(ExamMember::getExamId).collect(Collectors.toList());

                PageHelper.startPage(pageNum, pageSize);
                examList = examMapper.selectList(new QueryWrapper<Exam>().
                        eq(Exam.ISSHOW, 1).
                        in(Exam.ID, examIdList).
                        ne(Exam.STATUS, "unpublished").
                        orderByAsc(Exam.STATUS).
                        orderByDesc(Exam.CREATEDATE));
                PageInfo pageInfo = new PageInfo<>(examList);
                total = pageInfo.getTotal();
                examList = pageInfo.getList();
            }

            for (Exam exam : examList){
                //获取作答记录
                List<ExamPaper> paperList = examPaperMapper.selectList(new QueryWrapper<ExamPaper>().
                        eq(ExamPaper.EXAMID, exam.getId()).
                        eq(ExamPaper.USERID, userId).
                        eq(ExamPaper.SUBMITFLAG, 1));
                //是否已作答
                boolean isExam = false;
                if (paperList != null && paperList.size() > 0){
                    isExam = true;
                }
                //获取最晚作答时间
                Date endTime = exam.getEntranceEndDate();
                //是否已超时
                boolean isLate = endTime.before(new Date());

                if (isExam){
                    exam.setNewStatus("已提交");
                }else if (isLate){
                    exam.setNewStatus("已结束");
                }else {
                    exam.setNewStatus("未作答");
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.put("list", examList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 首页保存答题记录
     * @param examSave
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult saveExamPaper(ExamSave examSave) {
        try {
            String content = JSONArray.toJSONString(examSave.getSaveList());

            //是否已经有记录保存
            ExamSave examSave0 = examSaveMapper.selectOne(new QueryWrapper<ExamSave>().
                    eq(ExamSave.EXAMID, examSave.getExamId()).
                    eq(ExamSave.USERID, examSave.getUserId()));
            if (examSave0 == null){
                examSave.setSubmitContent(content);
                examSaveMapper.insert(examSave);
            }else {
                examSave0.setSubmitContent(content);
                examSave0.setRemainTime(examSave.getRemainTime());
                examSaveMapper.updateById(examSave0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("保存失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("保存成功");
    }

    /**
     * 首页根据id查详情
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public AjaxResult getTopDetail(Integer examId, Integer userId) {
        try {
            //查试卷信息
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, examId));
            //查找题目
            List<ExamQuestion> questionList = examQuestionMapper.selectList(new QueryWrapper<ExamQuestion>().
                    eq(ExamQuestion.EXAMID, examId));
            for (ExamQuestion question : questionList){
                //查找选项
                List<ExamOption> optionList = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                        eq(ExamOption.EXAMQUESTIONID, question.getId()));
                question.setOptionList(optionList);
            }
            //查询保存答题
            ExamSave examSave = examSaveMapper.selectOne(new QueryWrapper<ExamSave>().
                    eq(ExamSave.EXAMID, examId).
                    eq(ExamSave.USERID, userId));
            if (examSave != null) {
                List<ExamSaveVO> saveList = JSONObject.parseArray(examSave.getSubmitContent(), ExamSaveVO.class);
                examSave.setSaveList(saveList);
            }
            //查询考试答题信息
            List<ExamPaper> paperList = examPaperMapper.selectList(new QueryWrapper<ExamPaper>().
                    eq(ExamPaper.EXAMID, examId).
                    eq(ExamPaper.USERID, userId));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("exam", exam);
            jsonObject.put("question", questionList);
            jsonObject.put("save", examSave);
            jsonObject.put("paper", paperList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 交卷
     * @param paperList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult submitExam(List<ExamPaper> paperList) {
        try {
            //查题型对应的分数
            List<ExamQuestionType> typeList = examQuestionTypeMapper.selectList(new QueryWrapper<ExamQuestionType>().
                    eq(ExamQuestionType.EXAMID, paperList.get(0).getExamId()));
            int single = 0;
            int judge = 0;
            int multiple = 0;
            int completion = 0;
            for (ExamQuestionType type : typeList){
                if ("single".equals(type.getType())){
                    single = type.getScore();
                }
                if ("judge".equals(type.getType())){
                    judge = type.getScore();
                }
                if ("multiple".equals(type.getType())){
                    multiple = type.getScore();
                }
                if ("completion".equals(type.getType())){
                    completion = type.getScore();
                }
            }

            //判断这是第几次交卷
            List<ExamPaper> examPaper = examPaperMapper.selectList(new QueryWrapper<ExamPaper>().
                    eq(ExamPaper.EXAMID, paperList.get(0).getExamId()).
                    eq(ExamPaper.USERID, paperList.get(0).getUserId()).
                    eq(ExamPaper.EXAMQUESTIONID, paperList.get(0).getExamQuestionId()));
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().
                    eq(Exam.ID, paperList.get(0).getExamId()));
            int submitNum = examPaper == null ? 0 : examPaper.size();
            if (submitNum >= exam.getMaxSubmit()){
                return AjaxResult.error("已没有提交次数，请勿重复提交");
            }else {
                submitNum ++;
            }
            //处理试卷答题，计算得分
            double score = 0;
            for (ExamPaper paper : paperList){
                paper.setSubmitDate(new Date());
                paper.setSubmitFlag(submitNum);

                if ("single".equals(paper.getType()) || "judge".equals(paper.getType())){
                    //单选/判断
                    if (paper.getSubmitContent() == null || "".equals(paper.getSubmitContent())){
                        paper.setScore(new BigDecimal(0));
                    }else {
                        Integer content = Integer.parseInt(paper.getSubmitContent());
                        ExamOption option = examOptionMapper.selectOne(new QueryWrapper<ExamOption>().
                                eq(ExamOption.ID, content));
                        if (option.getIsAnswer() == 1){
                            //答对了
                            if ("single".equals(paper.getType())) {
                                paper.setScore(new BigDecimal(single));
                                score += single;
                            }else {
                                paper.setScore(new BigDecimal(judge));
                                score += judge;
                            }
                        }else {
                            //答错了
                            paper.setScore(new BigDecimal(0));
                        }
                    }
                }else if ("multiple".equals(paper.getType())){
                    //多选
                    if (paper.getSubmitContent() == null || "".equals(paper.getSubmitContent())){
                        paper.setScore(new BigDecimal(0));
                    }else {
                        //有填答案
                        String[] ids = paper.getSubmitContent().split(" ");
                        List<ExamOption> optionList = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                                eq(ExamOption.EXAMQUESTIONID, paper.getExamQuestionId()).
                                eq(ExamOption.ISANSWER, 1));
                        if (ids.length != optionList.size()){
                            paper.setScore(new BigDecimal(0));
                        }else {
                            List<ExamOption> optionList1 = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                                    in(ExamOption.ID, ids));
                            boolean existError = false;
                            for (ExamOption option : optionList1) {
                                if (option.getIsAnswer() == 0) {
                                    existError = true;
                                    break;
                                }
                            }
                            if (existError) {
                                paper.setScore(new BigDecimal(0));
                            } else {
                                paper.setScore(new BigDecimal(multiple));
                                score += multiple;
                            }
                        }
                    }
                }else if ("completion".equals(paper.getType())){
                    //填空
                    if (paper.getSubmitContent() == null || "".equals(paper.getSubmitContent())){
                        paper.setScore(new BigDecimal(0));
                    }else {
                        //有填答案
                        String[] answers = paper.getSubmitContent().split(" ", -1);
                        List<ExamOption> optionList = examOptionMapper.selectList(new QueryWrapper<ExamOption>().
                                eq(ExamOption.EXAMQUESTIONID, paper.getExamQuestionId()).
                                orderByAsc(ExamOption.ID));
                        if (answers.length != optionList.size()){
                            paper.setScore(new BigDecimal(0));
                        }else {
                            boolean existError = false;
                            for (int i=0; i<answers.length; i++){
                                if (!answers[i].equals(optionList.get(i).getContent())){
                                    existError = true;
                                    break;
                                }
                            }
                            if (existError){
                                paper.setScore(new BigDecimal(0));
                            }else {
                                paper.setScore(new BigDecimal(completion));
                                score += completion;
                            }
                        }
                    }
                }

                examPaperMapper.insert(paper);
            }

            //删除保存的答题信息
            examSaveMapper.delete(new QueryWrapper<ExamSave>().
                    eq(ExamSave.USERID, paperList.get(0).getUserId()).
                    eq(ExamSave.EXAMID, paperList.get(0).getExamId()));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("score", score);

            return AjaxResult.success("提交成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("提交失败，请联系管理员", e.getMessage());
        }
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
//        Workbook wb = new HSSFWorkbook();
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
        ExcelTool.createCell(row0, 5, style, "得分");
        ExcelTool.createCell(row0, 6, style, "签名");
        ExcelTool.createCell(row0, 7, style, "归属组织");

        Row row = sheet.createRow(r++);
        //组装一行数据
        ExcelTool.createCell(row, 0, style, export.getName());
        ExcelTool.createCell(row, 1, style, export.getSex());
        ExcelTool.createCell(row, 2, style, export.getMobile());
        ExcelTool.createCell(row, 3, style, export.getOrgName());
        ExcelTool.createCell(row, 4, style, export.getTime());
        ExcelTool.createCell(row, 5, style, export.getScore());
        ExcelTool.createCell(row, 6, style, export.getImg());
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
}

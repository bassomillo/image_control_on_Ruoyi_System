package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.democratic.entity.Exam;
import com.ruoyi.project.democratic.entity.ExamOption;
import com.ruoyi.project.democratic.entity.ExamQuestion;
import com.ruoyi.project.democratic.entity.ExamQuestionType;
import com.ruoyi.project.democratic.entity.VO.ExamBaseVO;
import com.ruoyi.project.democratic.mapper.ExamMapper;
import com.ruoyi.project.democratic.mapper.ExamOptionMapper;
import com.ruoyi.project.democratic.mapper.ExamQuestionMapper;
import com.ruoyi.project.democratic.mapper.ExamQuestionTypeMapper;
import com.ruoyi.project.democratic.service.IExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.tool.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

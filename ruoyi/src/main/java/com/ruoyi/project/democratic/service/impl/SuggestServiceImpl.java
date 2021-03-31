package com.ruoyi.project.democratic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.FastdfsClientUtil;
import com.ruoyi.project.democratic.entity.SuggestBox;
import com.ruoyi.project.democratic.entity.UploadFiles;
import com.ruoyi.project.democratic.entity.VO.SuggestBackVO;
import com.ruoyi.project.democratic.mapper.SuggestMapper;
import com.ruoyi.project.democratic.mapper.UploadFilesMapper;
import com.ruoyi.project.democratic.service.SuggestService;
import com.ruoyi.project.democratic.tool.ToolUtils;
import com.ruoyi.project.tool.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxr
 * @since 2021-03-29
 */
@Service
public class SuggestServiceImpl extends ServiceImpl<SuggestMapper, SuggestBox> implements SuggestService {

    @Autowired
    private SuggestMapper suggestMapper;
    @Autowired
    private UploadFilesMapper uploadFilesMapper;

    /**
     * 发送建言-首页
     * @param suggest
     * @param fileList
     * @return
     */
    @Override
    public AjaxResult insertSuggest(SuggestBox suggest, List<Integer> fileList) {
        try {
            suggest.setCreateDate(new Date());
            save(suggest);

            if (fileList != null && fileList.size() != 0){
                List<UploadFiles> files = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                        in(UploadFiles.ID, fileList));
                for (UploadFiles f : files){
                    f.setTargetId(suggest.getId());
                    f.setTargetType("suggest");
                    uploadFilesMapper.updateById(f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发送失败，请联系管理员", e.getMessage());
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 查找回复记录列表-首页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getTopSuggestList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            //分页插件
            PageHelper.startPage(pageNum, pageSize);
            QueryWrapper<SuggestBox> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SuggestBox.CREATEUSERID, userId).
                    eq(SuggestBox.PARENTID, 0).
                    orderByDesc(SuggestBox.CREATEDATE);
            List<SuggestBox> suggestList = suggestMapper.selectList(queryWrapper);
            PageInfo pageInfo = new PageInfo<>(suggestList, pageSize);

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
     * 根据id查询建言详情
     * @param id
     * @return
     */
    @Override
    public AjaxResult getSuggestDetailById(Integer id) {
        try {
            QueryWrapper<SuggestBox> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SuggestBox.ID, id);
            //查询记录详情
            SuggestBox suggest = suggestMapper.selectOne(queryWrapper);
            //查询回复记录
            List<SuggestBox> requireList = suggestMapper.selectList(new QueryWrapper<SuggestBox>().
                    eq(SuggestBox.PARENTID, id).
                    orderByAsc(SuggestBox.CREATEDATE));
            //查询附件
            List<UploadFiles> fileList = uploadFilesMapper.selectList(new QueryWrapper<UploadFiles>().
                    eq(UploadFiles.TARGETID, id).
                    eq(UploadFiles.TARGETTYPE, "suggest").
                    eq(UploadFiles.STATUS, "ok"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detail", suggest);
            jsonObject.put("require", requireList);
            jsonObject.put("file", fileList);

            return AjaxResult.success("查询成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("查询失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 条件查询建言列表-后台
     * @param content
     * @param year
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult getBackSuggestList(String content, Integer year, Integer pageNum, Integer pageSize) {
        try {
            //转义%
            content = Str.fuzzyQuery(content);

            PageHelper.startPage(pageNum, pageSize);
            List<SuggestBackVO> suggestList = suggestMapper.getBackSuggestList(content, year==null ? null : year.toString());
            for (SuggestBackVO suggest : suggestList){
                //生成组织架构
                ToolUtils tool = new ToolUtils();
                String orgName = tool.getOrgName(suggest.getOrgId());
                suggest.setOrgName(orgName);
            }
            PageInfo pageInfo = new PageInfo<>(suggestList, pageSize);

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
     * 批量删除
     * @param idList
     * @return
     */
    @Override
    public AjaxResult deleteSuggest(List<Integer> idList) {
        try {
            List<SuggestBox> suggestList = suggestMapper.selectList(new QueryWrapper<SuggestBox>().
                    in(SuggestBox.ID, idList).
                    or().
                    in(SuggestBox.PARENTID, idList));
            for (SuggestBox suggest : suggestList){
                suggest.setIsShow(0);
            }
            boolean flag = updateBatchById(suggestList);

            if (flag){
                return AjaxResult.success("删除成功");
            }else {
                return AjaxResult.error("删除失败，请联系管理员");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("删除失败，请联系管理员", e.getMessage());
        }
    }

    @Override
    public AjaxResult replySuggest(String content, Integer suggestId, Integer userId) {
        try {
            //插入回复
            SuggestBox suggestBox = new SuggestBox();
            suggestBox.setCreateDate(new Date());
            suggestBox.setContent(content);
            suggestBox.setParentId(suggestId);
            suggestBox.setCreateUserId(userId);
            save(suggestBox);

            //设置记录回复字段状态
            SuggestBox suggestBox1 = suggestMapper.selectOne(new QueryWrapper<SuggestBox>().
                    eq(SuggestBox.ID, suggestId));
            suggestBox1.setReplied(1);
            updateById(suggestBox1);

            return AjaxResult.success("回复成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("回复失败，请联系管理员", e.getMessage());
        }
    }

    /**
     * 上传首页建言文件
     * @param file
     * @param userId
     * @return
     */
    @Override
    public AjaxResult uploadSuggestFile(MultipartFile file, Integer userId) {
        try {
            //上传文件至服务器
//            FastdfsClientUtil fastDfs = new FastdfsClientUtil();
//            String url = fastDfs.uploadFile(file);
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
}

package com.ruoyi.project.chairmanOnline.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.ruoyi.common.utils.file.FileTypeUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.chairmanOnline.entity.QO.SocketChatRecordQO;
import com.ruoyi.project.chairmanOnline.entity.SocketChatOrgCommissioner;
import com.ruoyi.project.chairmanOnline.entity.SocketChatRecord;
import com.ruoyi.project.chairmanOnline.service.SocketChatOrgComService;
import com.ruoyi.project.chairmanOnline.service.SocketChatRecordService;
import com.ruoyi.project.common.FastdfsClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 聊天记录(SocketChatRecord)表控制层
 *
 * @author weide
 * @since 2021-04-15 15:50:32
 */
@Api(tags = "web端-民主管理-总经理在线-hwd")
@RestController
@RequestMapping("socketChatRecord")
public class SocketChatRecordController {
    /**
     * 服务对象
     */
    @Resource
    private SocketChatRecordService socketChatRecordService;

    @Resource
    FastdfsClientUtil fastDfsUtil;

    @Resource
    private SocketChatOrgComService socketChatOrgComService;


    @ApiIgnore
    @ApiOperation("总经理、咨询师查询聊天记录,按时间降序")
    @PostMapping("selectChatRecords")
    public AjaxResult selectChatRecords(Integer conversionId,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        SocketChatRecord socketChatRecordQM = new SocketChatRecord();
        socketChatRecordQM.setConversationid(conversionId);
        return AjaxResult.success(this.socketChatRecordService.queryAll(socketChatRecordQM, pageNum, pageSize));
    }


    @ApiOperation("用户查询聊天记录,按时间降序")
    @PostMapping("selectChatRecordsWithChairmanId")
    public AjaxResult selectChatRecordsWithChairmanId(@RequestParam int userId1, @RequestParam int userId2,
                                                      @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        //如果此用户的角色是秘书，则获取所属总经理id
        userId1 = socketChatOrgComService.getCommissionerByUserId(userId1);
        userId2 = socketChatOrgComService.getCommissionerByUserId(userId2);
        return AjaxResult.success(this.socketChatRecordService.queryChatRecord(userId1, userId2, pageNum, pageSize));
    }


    @ApiOperation("聊天记录搜索,按时间降序")
    @PostMapping("selectChatRecordsByCondition")
    public AjaxResult selectChatRecordsByCondition(@RequestBody SocketChatRecordQO socketChatRecordQO,
                                                   @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return AjaxResult.success(this.socketChatRecordService.selectChatRecordsByCondition(socketChatRecordQO, pageNum, pageSize));
    }


    @ApiIgnore
    @ApiOperation("fastDFS文件上传")
    @PostMapping("fastUpload")
    public AjaxResult upload(MultipartFile file) throws IOException {
        fastDfsUtil.uploadFile(file);
        return AjaxResult.success(fastDfsUtil.uploadFile(file));
    }


    @ApiIgnore
    @ApiOperation("fastDFS文件下载")
    @PostMapping("fastDownload")
    public void download(HttpServletResponse response,String fileUrl) throws IOException {
         fastDfsUtil.downLoadFile(response ,fileUrl,"file"+String.valueOf(System.currentTimeMillis()));
    }


    @ApiOperation("消息已读")
    @PostMapping("recordIsRead")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登陆用户的id")})
    public AjaxResult recordIsRead(@RequestParam int userId,@RequestParam int conversationId){
        return  AjaxResult.success(socketChatRecordService.chatRecordsIsRead(userId,conversationId));
    }



    @ApiOperation("文件上传")
    @PostMapping("upload")
    public AjaxResult upload2(MultipartFile file) throws IOException {
        String uploadUrl = FileUploadUtils.upload(file);
        return AjaxResult.success(uploadUrl);
    }


    @ApiOperation("文件下载")
    @GetMapping("download")
    public void download2(HttpServletResponse response, HttpServletRequest request,String fileUrl) throws IOException {

        String fileName = FileUtils.getFileName(fileUrl);
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
//        response.setContentType("image/png;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
        FileUtils.writeBytes(fileUrl, response.getOutputStream());
    }
}

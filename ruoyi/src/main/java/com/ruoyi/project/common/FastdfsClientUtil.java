package com.ruoyi.project.common;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

@Component("fastDfsUtil")
@Slf4j
public class FastdfsClientUtil {
	@Resource
	private FastFileStorageClient storageClient;
	
	/**上传文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile file) throws IOException{
		//文件名扩展名
		String suffixName = FilenameUtils.getExtension(file.getOriginalFilename());
		InputStream inputStream = file.getInputStream();
		StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(), suffixName, null);
		inputStream.close();
		return  storePath.getFullPath();
	}

	/**
	 * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream (file);
        StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()), null);
        inputStream.close();
        return storePath.getFullPath();
    }

	/**
	 * 上传图片
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String uploadImage(MultipartFile file) throws IOException{
		//文件扩展名
		String suffixName = FilenameUtils.getExtension(file.getOriginalFilename());
		Set<MetaData> metaDataSet = new HashSet<>();
		InputStream inputStream = file.getInputStream();
		FastImageFile fastImageFile = new FastImageFile(inputStream,file.getSize(),suffixName,metaDataSet);
		StorePath storePath = storageClient.uploadImage(fastImageFile);
		inputStream.close();
		return  storePath.getFullPath();
	}

	/**
	 * 上传图片并压缩
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String uploadImageAndCrtThumb(MultipartFile file) throws IOException{
		//文件名扩展名
		String suffixName = FilenameUtils.getExtension(file.getOriginalFilename());
		InputStream inputStream = file.getInputStream();
		//上传图片并生成缩略图
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream,file.getSize(),suffixName,null);
		inputStream.close();
		return  storePath.getFullPath();
	}

	/**删除文件
	 * @param fileUrl
	 */
	public boolean deleteFile(String fileUrl) {
		if (StringUtils.isBlank(fileUrl)) {
			return false;
		}
		try {
			StorePath storePath = StorePath.parseFromUrl(fileUrl);
	        storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
	        return true;
		} catch (Exception e) {
	         log.error("删除文件出错", e);
	         return false;
		}
	}
	/**下载文件(返回二进制流)
	 * @param fileUrl 文件的路径
	 * @throws Exception
	 */
	public byte[] downLoadFile(String fileUrl) {
		StorePath storePath = StorePath.parseFromUrl(fileUrl);
		//获取文件
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        return bytes;
	}
	/**下载文件
	 * @param response
	 * @param fileUrl 文件的路径
	 * @throws Exception 
	 */
	public void downLoadFile(HttpServletResponse response, String fileUrl, String fileName) throws IOException {
		StorePath storePath = StorePath.parseFromUrl(fileUrl);
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
        //使用以下setHeader方法,fileName可为中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + suffix, "UTF-8"));
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + suffix);
		response.setContentType("application/msexcel;charset=utf-8");
        OutputStream output = response.getOutputStream();
        output.write(bytes);
		output.flush();
        output.close();
	}

	public void downLoadExcel(HttpServletResponse response, String fileUrl, String fileName) throws IOException {

		StorePath storePath = StorePath.parseFromUrl(fileUrl);
		byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
		String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
		//使用以下setHeader方法,fileName可为中文
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + suffix, "UTF-8"));
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + suffix);
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		OutputStream output = response.getOutputStream();
		output.write(bytes);
		output.flush();
		output.close();
	}

	
}

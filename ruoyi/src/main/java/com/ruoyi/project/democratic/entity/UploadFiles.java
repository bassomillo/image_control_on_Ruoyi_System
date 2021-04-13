package com.ruoyi.project.democratic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件实体类
 * </p>
 *
 * @author cxr
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UploadFiles extends Model<UploadFiles> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 云文件ID
     */
    @TableField("globalId")
    private String globalId;

    /**
     * 文件的HashID
     */
    @TableField("hashId")
    private String hashId;

    /**
     * 所存目标ID
     */
    @TableField("targetId")
    private Integer targetId;

    /**
     * 目标类型
     */
    @TableField("targetType")
    private String targetType;

    /**
     * 文件使用的模块类型
     */
    @TableField("useType")
    private String useType;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件uri
     */
    private String uri;

    /**
     * 后缀
     */
    private String ext;

    /**
     * 文件大小
     */
    @TableField("fileSize")
    private Integer fileSize;

    /**
     * ETAG
     */
    private String etag;

    /**
     * 长度（音视频则为时长，PPT/文档为页数）
     */
    private Integer length;

    /**
     * 描述
     */
    private String description;

    /**
     * 文件上传状态
     */
    private String status;

    /**
     * 文件转换时的查询转换进度用的Hash值
     */
    @TableField("convertHash")
    private String convertHash;

    /**
     * 文件转换状态
     */
    @TableField("convertStatus")
    private String convertStatus;

    /**
     * 文件转换参数
     */
    @TableField("convertParams")
    private String convertParams;

    /**
     * 元信息
     */
    private String metas;

    /**
     * 元信息
     */
    private String metas2;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 是否公开文件
     */
    @TableField("isPublic")
    private Boolean isPublic;

    /**
     * 是否可下载
     */
    @TableField("canDownload")
    private Boolean canDownload;

    @TableField("usedCount")
    private Integer usedCount;

    /**
     * 更新用户名
     */
    @TableField("updatedUserId")
    private Integer updatedUserId;

    /**
     * 文件最后更新时间
     */
    @TableField("updatedTime")
    private Integer updatedTime;

    /**
     * 文件上传人
     */
    @TableField("createdUserId")
    private Integer createdUserId;

    /**
     * 文件上传时间
     */
    @TableField("createdTime")
    private Integer createdTime;


    public static final String ID = "id";

    public static final String GLOBALID = "globalId";

    public static final String HASHID = "hashId";

    public static final String TARGETID = "targetId";

    public static final String TARGETTYPE = "targetType";

    public static final String USETYPE = "useType";

    public static final String FILENAME = "filename";

    public static final String URI = "uri";

    public static final String EXT = "ext";

    public static final String FILESIZE = "fileSize";

    public static final String ETAG = "etag";

    public static final String LENGTH = "length";

    public static final String DESCRIPTION = "description";

    public static final String STATUS = "status";

    public static final String CONVERTHASH = "convertHash";

    public static final String CONVERTSTATUS = "convertStatus";

    public static final String CONVERTPARAMS = "convertParams";

    public static final String METAS = "metas";

    public static final String METAS2 = "metas2";

    public static final String TYPE = "type";

    public static final String ISPUBLIC = "isPublic";

    public static final String CANDOWNLOAD = "canDownload";

    public static final String USEDCOUNT = "usedCount";

    public static final String UPDATEDUSERID = "updatedUserId";

    public static final String UPDATEDTIME = "updatedTime";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String CREATEDTIME = "createdTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

package com.ruoyi.project.union.entity;

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
 * 用户绑定
 * </p>
 *
 * @author zjy
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBind extends Model<UserBind> {

    private static final long serialVersionUID=1L;

    /**
     * 用户绑定ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户绑定类型
     */
    private String type;

    /**
     * 来源方用户ID
     */
    @TableField("fromId")
    private String fromId;

    /**
     * 被绑定的用户ID
     */
    @TableField("toId")
    private Integer toId;

    /**
     * oauth token
     */
    private String token;

    /**
     * oauth refresh token
     */
    @TableField("refreshToken")
    private String refreshToken;

    /**
     * token过期时间
     */
    @TableField("expiredTime")
    private Integer expiredTime;

    /**
     * 绑定时间
     */
    @TableField("createdTime")
    private Integer createdTime;


    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String FROMID = "fromId";

    public static final String TOID = "toId";

    public static final String TOKEN = "token";

    public static final String REFRESHTOKEN = "refreshToken";

    public static final String EXPIREDTIME = "expiredTime";

    public static final String CREATEDTIME = "createdTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

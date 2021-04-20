package com.ruoyi.project.chairmanOnline.entity.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @param
 * @Date 2021/4/16
 * @Author weide
 * @description
 **/
@Data
public class WebSocketSystemMessageVO implements Serializable {
    private static final long serialVersionUID = -77165233333699782L;

    private Integer onlineId;
    private Integer offlineId;
    private Set<Integer> currentOnlineIds;


}

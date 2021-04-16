package com.ruoyi.project.tool;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Str {

	// 不存在的id
	public final static String NOT_EXIT_ID = "!@#$%^";

	// BaseUser未查询到对应数据
	public final static String NOT_EXIT_USER = "不存在此用户！";

	// 当前根据token存入redis的登录用户key
	public final static String LOGINING_USER = "loginUser";

	public final static String NOT_EXIST_MSG = "当前无登录用户或已过期，请重新登录！";

	public final static String NOT_EQUAL_MSG = "请求用户与当前登录用户不一致，请刷新重试！";

	public final static String ERROR_MSG = "当前登录验证解析失败，请重新登录！";

	public static String removeOtherStr(String str) {
		if( str == null ) return "";
		String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。 、？]";
		return str.replaceAll(regEx, "");
	}

	/**
	 * 转义%等字符
	 * @param name
	 * @return
	 */
	public static String fuzzyQuery(String name) {
		if (StringUtils.isNotBlank(name)) {
			name = name.replaceAll("%", "/%");
			name = name.replaceAll("_", "/_");
		}
		return name;
	}

	public static List<Integer> getOrgIds(String orgCodes) {
		String[] orgs = orgCodes.split("\\.");
		List<Integer> orgIds = new ArrayList<>();
		for(int i = 0; i < orgs.length; i++) {
			orgIds.add(Integer.valueOf(orgs[i]));
		}
		return orgIds;
	}

	public static List<Integer> getOrgChildIds(Integer orgId, String orgCodes) {
		String[] orgs = orgCodes.split("\\.");
		List<Integer> orgIds = new ArrayList<>();
		boolean isAdd = false;
		for(int i = 0; i < orgs.length; i++) {
			if(Integer.valueOf(orgs[i]).equals(orgId))
				isAdd = true;
			if(isAdd)
				orgIds.add(Integer.valueOf(orgs[i]));
		}
		return orgIds;
	}

    private Str() {}
	
}

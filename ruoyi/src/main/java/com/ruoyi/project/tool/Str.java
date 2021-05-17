package com.ruoyi.project.tool;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Str {

	public final static String INITIAL_PASSWORD = "oX*t3&xR";

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

	/**
	 * 获取A机构下所有子机构的id
	 * @param orgId A机构id
	 * @param orgCodes A机构orgCode
	 * @return 子机构List
	 */
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

	/**
	 * 获取A机构的机构等级
	 * @param orgId A机构id
	 * @param orgCodes A机构orgCode
	 * @return 子机构List
	 */
	public static Integer getOrgLevel(Integer orgId, String orgCodes) {
		String[] orgs = orgCodes.split("\\.");
		List<Integer> orgIds = new ArrayList<>();
		Integer level = 0;
		for(int i = 0; i < orgs.length; i++) {
			level++;
			if(Integer.valueOf(orgs[i]).equals(orgId))
				break;
		}
		return level;
	}

	public static void StringConstitute(String args) {
		// 用于记录字符种类
		StringBuffer recordType = new StringBuffer();
		// 用于记录字符个数
		int[] recordNumber = new int[args.length()];
		for (int i = 0; i < args.length(); i++) {
			// 用于临时计数
			int count = 0;
			// 将args.charAt(i)的值赋给medium
			String medium = args.charAt(i) + "";
			// 判断recordType中没有medium中的字符
			if (!recordType.toString().contains(medium)) {
				// 将medium中的字符添加到recordType中
				recordType.append(medium);
				count++;
				// 用于计算medium中的字符在字符串中的个数
				for (int j = i + 1; j < args.length(); j++) {
					// 如果args.charAt(j)中的字符与medium中的字符相同时
					if (medium.equals(args.charAt(j) + "")) {
						count++;
					}
				}
				// 将计数工具的值赋给recordNumber数组
				recordNumber[recordType.length()] = count;
			}
		}
		for (int i = 0; i < recordType.length(); i++) {
			System.out.println("字符" + recordType.charAt(i) + "共有" + recordNumber[i + 1] + "个");
		}
	}

	// 验证日期格式是否正确
	public static boolean dateCheck(String str) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");// 括号内为日期格式，y代表年份，M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
		try {
			sd.setLenient(false);// 此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
			sd.parse(str);// 从给定字符串的开始解析文本，以生成一个日期
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 验证字符串是否为纯数字
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}


	private Str() {}
	
}

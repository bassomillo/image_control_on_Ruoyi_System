package com.ruoyi.common.core.text;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class R extends JSONObject {
	private static final long serialVersionUID = -8157613083634272196L;
	public R() {
		put("code", 200);
		put("msg", "成功");
	}

	public static R error() {
		return error(500, "失败");
	}

	public static R error(String msg) {
		if(StringUtils.isEmpty(msg)){
			return error();
		}
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R data(Object obj) {
		R r = new R();
		r.put("data", obj);
		return r;
	}

	public static R result(List<?> list, Object total) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("total", total);
		return R.ok(m);
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok(String msg,Map<String, Object> map) {
		R r = new R();
		r.put("msg", msg);
		r.putAll(map);
		return r;
	}

	public static R toAjax(int rows)
	{
		return rows > 0 ? R.ok() : R.error();
	}

	/**
	 * 响应返回结果
	 *
	 * @param result 结果
	 * @return 操作结果
	 */
	public static R toAjax(boolean result)
	{
		return result ? R.ok() : R.error();
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
package com.focustech.cief.filemanage.dataserver.common.utils;

import com.focustech.core.utils.StringUtils;

/**
 * http相关的工具类
 * *
 * @author lihaijun
 *
 */
public class HttpUtil {
	/**
	 * 获取请求url的根路径
	 * *
	 * @param requestUrl
	 * @return
	 */
	public static String getRootPath(String requestUrl){
		if(!StringUtils.isEmpty(requestUrl)){
			int lastIndexOf = requestUrl.lastIndexOf("/");
			if(lastIndexOf < 0){
				lastIndexOf = requestUrl.lastIndexOf("\\");
			}
			return requestUrl.substring(0, lastIndexOf);
		}
		return "";
	}
}

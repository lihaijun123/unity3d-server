package com.focustech.cief.filemanage.dataserver.common.utils;
/**
 *
 * *
 * @author lihaijun
 *
 */
public class StringUtil {
	/**
	 * 字符串是否为空
	 * *
	 * @param strValue
	 * @return
	 */
	public static boolean isEmpty(String strValue){
		return null == strValue || "".equals(strValue) || "".equals(strValue.trim());
	}
}

package com.focustech.cief.filemanage.dataserver.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * *
 * @author lihaijun
 *
 */
public class DateUtil {
	public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YMD = "yyyyMMdd";

	/**
	 * 当前时间
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * *
	 * @return Date
	 */
	public static Date getCurrentDateTime(){
		return new Date();
	}

	/**
	 * 当前时间
	 * 格式：yyyy-MM-dd
	 * *
	 * @return String
	 */
	public static String getCurrentDateStr(){
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YMD);
		return dateFormat.format(getCurrentDateTime());
	}

	/**
	 * 当前时间
	 * 格式：yyyy-MM-dd
	 * *
	 * @return String
	 */
	public static String getCurrentDateStr(String fromatStr){
		DateFormat dateFormat = new SimpleDateFormat(fromatStr);
		return dateFormat.format(getCurrentDateTime());
	}

	public static void main(String[] args){
		DateUtil.getCurrentDateStr(DateUtil.DATE_FORMAT_YMDHMS);
	}
}

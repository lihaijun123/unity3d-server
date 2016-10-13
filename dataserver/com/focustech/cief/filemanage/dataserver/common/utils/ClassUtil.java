package com.focustech.cief.filemanage.dataserver.common.utils;
/**
 *
 * *
 * @author lihaijun
 *
 */
public class ClassUtil {
	public static Object getInstance(String name) {
		Object obj = null;
		try {
			Class instance = Class.forName(name);
			obj = instance.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}

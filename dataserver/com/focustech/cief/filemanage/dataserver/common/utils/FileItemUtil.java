package com.focustech.cief.filemanage.dataserver.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class FileItemUtil {
	/**
	 * 获取文件格式
	 * 比如a.jpg返回.jpg
	 * *
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") >= 0) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		return extName;
	}

	/**
	 * 获取文件名称
	 * *
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		if (fileName.lastIndexOf(".") >= 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}

	/**
	 * 获取文件格式名称
	 * 比如a.jpg返回jpg
	 * *
	 * @param fileName
	 * @return
	 */
	public static String getShortFileExtName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") >= 0) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		else {
			return "";
		}
		return extName.substring(1, extName.length());
	}

	/**
	 * 通过调用构造函数实例化对象
	 * *
	 * @param className
	 * @param paramType
	 * @param paramValue
	 * @return
	 */
	public static Object newInstanceByConst(String className, Class[] paramType, Object[] paramValue){
		Object obj = null;
		try {
			Class baseFileCompt = Class.forName(className);
			Constructor fileComponentCtor = baseFileCompt.getDeclaredConstructor(paramType);
			obj = fileComponentCtor.newInstance(paramValue);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static void main(String[] args){
		System.out.println(getFileExtName("/s/a.jpg"));
	}
}

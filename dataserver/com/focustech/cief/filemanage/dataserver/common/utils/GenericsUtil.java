package com.focustech.cief.filemanage.dataserver.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
public class GenericsUtil {
	private static final Logger log = Logger.getLogger(GenericsUtil.class);
	private GenericsUtil() {
	}
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
        Type params[] = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class)params[index];
	}
}

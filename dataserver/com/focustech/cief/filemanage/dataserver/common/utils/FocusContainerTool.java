package com.focustech.cief.filemanage.dataserver.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.focustech.common.utils.SpringContextUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
public abstract class FocusContainerTool {
	// 容器上下文
    public static ApplicationContext applicationContext;

    /**
     * @param className
     * @return
     */
    static public Object lookup(Class<?> classType) {
        final String className = classType.getCanonicalName();
        String simpleClassName = StringUtils.substring(className, StringUtils.lastIndexOf(className, ".") + 1);
        simpleClassName =
            StringUtils.lowerCase(StringUtils.substring(simpleClassName, 0, 1))
            + StringUtils.substring(simpleClassName, 1);
        return lookup(simpleClassName);
    }

    /**
     * @param beanId
     * @return
     */
    static public Object lookup(String beanId) {
        Object bean = null;
        try {
            bean = SpringContextUtil.getBean(beanId);
            return bean;
        }
        catch (Exception e) {
            return null;
        }
    }
}

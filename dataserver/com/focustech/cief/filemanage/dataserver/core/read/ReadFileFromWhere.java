package com.focustech.cief.filemanage.dataserver.core.read;

import java.io.InputStream;

/**
 * 从何处读取文件
 * *
 * @author lihaijun
 *
 */
public interface ReadFileFromWhere {
	/**
	 *
	 * *
	 * @param key
	 * @return
	 */
	InputStream read(String key);
}

package com.focustech.cief.filemanage.dataserver.core.write;

import java.io.File;
import java.io.InputStream;

import com.focustech.cief.filemanage.dataserver.model.AbstractFile;

/**
 * 文件写入何处接口
 * *
 * @author lihaijun
 *
 */
public interface WriteFileToWhere {
	/**
	 * 写文件
	 * *
	 * @param file
	 * @return
	 */
	public String write(String key, InputStream inputStream);
	/**
	 *交换文件
	 *保持各自的key不变，交换key对应的文件内容体
	 * *
	 * @param orginFile 源文件
	 * @param targetFile 目标文件
	 * @return
	 */
	void exchange(AbstractFile orginFile, AbstractFile targetFile);
}

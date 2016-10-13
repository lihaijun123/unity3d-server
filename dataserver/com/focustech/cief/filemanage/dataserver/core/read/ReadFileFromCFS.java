package com.focustech.cief.filemanage.dataserver.core.read;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 从虚拟展览馆文件系统（cfs）中读取文件数据
 * *
 * @author lihaijun
 *
 */
public class ReadFileFromCFS implements ReadFileFromWhere{
	private final Logger logger = LoggerFactory.getLogger(ReadFileFromCFS.class.getName());
	@Override
	public InputStream read(String key) {
		logger.info("ReadFileFromCFS：" + key);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(new File(key));
			bis = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bis;
	}
}

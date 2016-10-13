package com.focustech.cief.filemanage.dataserver.core.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.FileUtils;

/**
 * 写入虚拟展览馆文件系统（cfs）
 * *
 * @author lihaijun
 *
 */
public class WriteFileToCFS implements WriteFileToWhere{
	private final Logger logger = LoggerFactory.getLogger(WriteFileToCFS.class.getName());
	private FileItem fileItem;

	private File outputFolder;

	public WriteFileToCFS(){

	}

	public WriteFileToCFS(FileItem fileItem, File outputFolder){
		this.fileItem = fileItem;
		this.outputFolder = outputFolder;
	}

	@Override
	public String write(String key, InputStream inputStream) {
		try {
			logger.info("WriteFileToCFS：inputStream");
			fileItem.write(this.outputFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void exchange(AbstractFile orginFile, AbstractFile targetFile) {
		try {
			String originalFilePhysicalPath = FilePathUtil.getFilePhysicalPath(orginFile);
			File originalPhFile = new File(originalFilePhysicalPath);
			FileOutputStream fileOutputStream = new FileOutputStream(originalPhFile);
			fileOutputStream.write(FileUtils.getFileBytes(FilePathUtil.getFilePhysicalPath(targetFile)));
			//从磁盘删除目标文件
			FileUtils.delete(FilePathUtil.getFilePhysicalPath(targetFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

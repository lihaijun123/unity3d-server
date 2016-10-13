package com.focustech.cief.filemanage.dataserver.core.processor.before;

import com.focustech.cief.filemanage.dataserver.exception.FileBeforeProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;

/**
 * 前置处理器-文件装饰接口
 * *
 * @author lihaijun
 *
 */
public interface IFileBeforeProcessor {
	/**
	 * 保存文件前所做的操作
	 * *
	 * @return
	 */
	public AbstractFile doBeforeSave() throws FileBeforeProcessorException;
}

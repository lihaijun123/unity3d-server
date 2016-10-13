package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.util.Map;

import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
/**
 * 文件保存后处理接口
 * *
 * @author lihaijun
 *
 */
public interface IFileAfterProcessor {
	/**
	 * 保存文件后所做的操作
	 * *
	 * @return
	 */
	public Map<String, String> doAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap) throws FileAfterProcessorException;

	/**
	 * 返回map中访问值的key
	 * *
	 * @return
	 */
	public String[] getReturnMapKeyAry();
}

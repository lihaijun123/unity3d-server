package com.focustech.cief.filemanage.dataserver.core.processor.before;

import com.focustech.cief.filemanage.dataserver.exception.FileBeforeProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;

/**
 * 前置处理器-默认的文件装饰类（处理未找到前置处理器的），对要特殊处理的则要对应具体的装饰类
 * *
 * @author lihaijun
 *
 */
public class OtherFileBeforeProcessor extends AbstractFileBeforeProcessor{

	public OtherFileBeforeProcessor(IFileBeforeProcessor fileComponent){
		super(fileComponent);
	}

	@Override
	public AbstractFile doBeforeSave() throws FileBeforeProcessorException {
		//以后可能会添加更多的属性
		return super.doDefaultBeforeProcessor();
	}

	@Override
	protected String getNewFileName(String fileName, String extName) {
		return super.getDefaultNewFileName(fileName, extName, "");
	}

	@Override
	protected String getVisitAddr(String localName) {

		return super.getDefaultVisitAddr(localName);
	}
}

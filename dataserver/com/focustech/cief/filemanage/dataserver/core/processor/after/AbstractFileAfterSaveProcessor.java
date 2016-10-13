package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.util.Map;

import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.processor.AbstractProcessor;
import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.SpringContextUtil;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 后置处理器-文件保存后处理的抽象父类
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractFileAfterSaveProcessor extends AbstractProcessor implements IFileAfterProcessor{

	/**附件类型*/
	public static final String ATTACH_TYPE = "attachType";
	/**模型文件格式过滤*/
	public static final String ATTACH_FORMAT_STR = "attachFormatStr";
	/**模型文件是否组件化*/
	public static final String IS_COMP = "is_comp";
	/**模型文件访问ID前缀*/
	public final static String MODEL_FILE_ID_PREFIX = "modelFileId_";
	/**模型文件访问url前缀*/
	public final static String MODEL_FILE_URL_PREFIX = "modelFileUrl_";
	/**模型文件路径*/
	public final static String MODEL_FILE_PATH_PREFIX = "modelFilePath_";
	/**
	 * 模型文件验证
	 * 是否合法模型文件
	 * *
	 */
	public final static String IS_VALID_MODEL_FILE = "isValid";
	/**file Service处理类*/
	protected IBaseFileService<AbstractFile> fileService = (IBaseFileService<AbstractFile>)SpringContextUtil.getBean("baseFileServiceImpl");

	protected String[] returnMapKeyAry;

	@Override
	public Map<String, String> doAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap) throws FileAfterProcessorException{
		this.paramMap = paramMap;
		if(paramMap.containsKey(FileParam.FILE_SERVER)){
			fileServer = (DataServerNode)paramMap.get(FileParam.FILE_SERVER);
		}
		if(paramMap.containsKey(FileParam.SUB_BLOCK_NAME)){
			subBlockName = TCUtil.sv(paramMap.get(FileParam.SUB_BLOCK_NAME));
		}
		return null;
	}
	/**
	 * 构建map的Key
	 * *
	 * @param map
	 * @return
	 */
	protected void extractReturnMapKeyAry(Map<String, String> map){
		if(null != map){
			this.returnMapKeyAry = new String[map.size()];
			int i = 0;
			for(Map.Entry<String, String> mapData : map.entrySet()){
				returnMapKeyAry[i] = mapData.getKey();
				i ++;
			}
		}
	}
}

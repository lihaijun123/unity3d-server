package com.focustech.cief.filemanage.dataserver.core.write;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.focustech.cief.filemanage.dataserver.common.utils.ClassUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileItemUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.StringUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileType;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;
import com.focustech.cief.filemanage.dataserver.core.processor.after.AbstractFileAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.after.IFileAfterProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.before.FileTypeBeforeProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.before.IFileBeforeProcessor;
import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.exception.FileBeforeProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.SpringContextUtil;
import com.focustech.common.utils.TCUtil;
import com.focustech.core.Assert;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * 写文件类
 * *
 * @author lihaijun
 *
 */
public class FileWrite implements IFileWrite {

	public final static Log log = LogFactory.getLog(FileWrite.class.getName());

	private IBaseFileService<AbstractFile> baseFileService = (IBaseFileService)SpringContextUtil.getBean("baseFileServiceImpl");

	/**
	 * 保存文件
	 * *
	 * @param fileItem 文件流
	 * @param subBlockPath 本地保存路径
	 * @param subBlockVisitUrl 文件访问路径
	 * @param fileServer 服务器
	 * @param urlParam 请求参数
	 * @return 返回JSON信息
	 */
	@Override
	public JSONObject writeFile(FileItem fileItem, String subBlockName, DataServerNode fileServer, String urlParam){
	    Assert.notEmpty(fileItem);
		JSONObject resultJo = new JSONObject();
		String fileName = fileItem.getName();
        if (!StringUtil.isEmpty(fileName)) {
        	String subBlockPath = FilePathUtil.getSubBlockPhysicalPath(fileServer, subBlockName);
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put(FileParam.SUB_BLOCK_NAME, subBlockName);
        	paramMap.put(FileParam.URL_PARAM, urlParam);
        	paramMap.put(FileParam.FILE_SERVER, fileServer);
        	String fileId = "";
        	AbstractFile abstractFile = null;
        	InputStream inputStream = null;
        	boolean isDeleteFile = false;
        	try {
        		//文件保存前处理
        		abstractFile = processFileBeforeSave(fileItem, paramMap);
        		//设置data server
        		abstractFile.setServerNode(fileServer);
        		abstractFile.setServerSn(fileServer.getSn());
        		//写入文件系统
        		WriteFileToWhere fileToWhere = null;
        		String localName = abstractFile.getLocalName();
				//写到本地，为了做一些特殊处理
        		fileToWhere = new WriteFileToCFS(fileItem, new File(subBlockPath + localName));
        		fileToWhere.write("", fileItem.getInputStream());
        		//保存信息到数据库，返回文件id
        		fileId = String.valueOf(baseFileService.saveParentFile(abstractFile));
        		//文件保存后处理
        		resultJo.put(FileParam.FILE_ID, fileId);
        		resultJo.put(FileParam.FILE_URL, abstractFile.getVisitAddr());
        		processFileAfterSave(abstractFile, paramMap, resultJo);
        	} catch (Exception e) {
        		resultJo.put(FileParam.ERROR_RESPONSE, e.getMessage());
        	} finally {
        		if(inputStream != null){
        			try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        	}
        	if(isDeleteFile){
        		if(!FileType.MODEL.equals(abstractFile.getType())){
        			FileUtil.delFile(FilePathUtil.getFilePhysicalPath(abstractFile));
        		}
        	}
        }
        return resultJo;
	}

	/**
	 * 文件保存前操作，选择文件上传的实体类，可能根据不同文件需要特殊处理
	 * *
	 * @param fileItem
	 * @param paramMap
	 * @return
	 * @throws FileBeforeProcessorException
	 */
	protected AbstractFile processFileBeforeSave(FileItem fileItem, Map<String, Object> paramMap) throws FileBeforeProcessorException {
		Assert.notEmpty(fileItem);
		//根据文件类型获取具体文件类处理对象
		IFileBeforeProcessor baseFileProcessor = new FileTypeBeforeProcessor(fileItem, paramMap);
		//动态获取对应的装饰对象
		Class[] paramType = new Class[]{IFileBeforeProcessor.class};
		Object[] paramValue = new Object[]{baseFileProcessor};
		String fileExtName = FileItemUtil.getShortFileExtName(fileItem.getName()).toLowerCase();
		//获取文件装饰类
		String decClassName = FileTypeOrExtProcRelation.TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.containsKey(fileExtName) ? FileTypeOrExtProcRelation.TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.get(fileExtName) : FileTypeOrExtProcRelation.TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.get(FileTypeOrExtProcRelation.OTHER);
		log.debug("前置处理器名称:" + decClassName);
		IFileBeforeProcessor fileProcessor = (IFileBeforeProcessor)FileItemUtil.newInstanceByConst(decClassName, paramType, paramValue);
		return fileProcessor.doBeforeSave();
	}

	/**
	 * 文件保存后的操作
	 * *
	 * @param abstractFile
	 * @param urlParam
	 * @param jsonObj
	 * @throws FileAfterProcessorException
	 */
	private void processFileAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap, JSONObject jsonObj) throws FileAfterProcessorException{
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		if(!StringUtil.isEmpty(TCUtil.sv(urlParam))){
			//文件类型
			String fileType = abstractFile.getType();
			String decClassName = FileTypeOrExtProcRelation.TYPE_FILE_AFTER_SAVE_PROCESS_MAP.get(fileType);
			if(!StringUtil.isEmpty(decClassName)){
				log.debug("后置处理器名称:" + decClassName);
				IFileAfterProcessor fileProcessAfterSave = (AbstractFileAfterSaveProcessor)ClassUtil.getInstance(decClassName);
				Map<String, String> returnMap = fileProcessAfterSave.doAfterSave(abstractFile, paramMap);
				String[] returnMapKey = fileProcessAfterSave.getReturnMapKeyAry();
				if(null != returnMap && null != returnMapKey){
					for(int i = 0, j = returnMapKey.length; i < j; i ++){
						jsonObj.put(returnMapKey[i], returnMap.get(returnMapKey[i]));
					}
				}
			}
		}
	}

}

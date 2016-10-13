package com.focustech.cief.filemanage.dataserver.core.processor.before;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import com.focustech.cief.filemanage.dataserver.common.utils.ClassUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileItemUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.TCUtil;
import com.focustech.core.utils.StringUtils;
/**
 * 前置处理器-获取文件信息，根据文件扩展名称来实例化文件类，并且对文件进行包裹
 * 作为第一个处理类
 * *
 * @author lihaijun
 *
 */
public class FileTypeBeforeProcessor implements IFileBeforeProcessor {
	/**文件流对象*/
	private FileItem fileItem;
	/**参数*/
	private Map<String, Object> paramMap;

	public FileTypeBeforeProcessor(FileItem fileItem, Map<String, Object> paramMap){
		this.fileItem = fileItem;
		this.paramMap = paramMap;
	}

	public FileItem getFileItem() {
		return fileItem;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	/**
	 * 获取具体的文件类
	 * *
	 */
	@Override
	public AbstractFile doBeforeSave() {
		String processFileClass = "";
		String classKey = "";
		//客户端制定上传文件类型
		if(paramMap.containsKey(FileParam.URL_PARAM)){
			//url参数
			String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
			if(!StringUtils.isEmpty(urlParam)){
				JSONObject jsonObj = JSONObject.fromObject(urlParam);
				if(jsonObj.containsKey(FileParam.FILE_TYPE)){
					classKey = jsonObj.getString(FileParam.FILE_TYPE);
				}
			}
		}
		if(StringUtils.isEmpty(classKey)){
			//通过文件格式名称获取对应的处理类
			classKey = FileItemUtil.getShortFileExtName(fileItem.getName()).toLowerCase();
		}
		processFileClass = FileTypeOrExtProcRelation.TYPE_FILE_CLASS_MAP.containsKey(classKey) ? FileTypeOrExtProcRelation.TYPE_FILE_CLASS_MAP.get(classKey) : FileTypeOrExtProcRelation.TYPE_FILE_CLASS_MAP.get(FileTypeOrExtProcRelation.OTHER);
		return (AbstractFile)ClassUtil.getInstance(processFileClass);
	}

}

package com.focustech.cief.filemanage.dataserver.core.processor.before;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import com.focustech.cief.filemanage.dataserver.common.utils.DateUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileItemUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.ValueFormatUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.processor.AbstractProcessor;
import com.focustech.cief.filemanage.dataserver.exception.FileBeforeProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.TCUtil;
import com.focustech.core.utils.StringUtils;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 前置处理器-抽取文件的共同特征抽象父类
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractFileBeforeProcessor extends AbstractProcessor implements IFileBeforeProcessor {
	/**被装饰的文件类*/
	protected IFileBeforeProcessor fileBeforeProcessor;
	/**文件对象*/
	protected FileItem fileItem;
	/**
	 * *
	 * @param fileComponent
	 */
	public AbstractFileBeforeProcessor(IFileBeforeProcessor fileComponent){
		this.fileBeforeProcessor = fileComponent;
		if(fileComponent instanceof FileTypeBeforeProcessor){
			this.fileItem = ((FileTypeBeforeProcessor)this.fileBeforeProcessor).getFileItem();
			this.paramMap = ((FileTypeBeforeProcessor)this.fileBeforeProcessor).getParamMap();
			if(paramMap.containsKey(FileParam.FILE_SERVER)){
				fileServer = (DataServerNode)paramMap.get(FileParam.FILE_SERVER);
			}
			if(paramMap.containsKey(FileParam.SUB_BLOCK_NAME)){
				subBlockName = TCUtil.sv(paramMap.get(FileParam.SUB_BLOCK_NAME));
			}
		}
	}
	/**
	 * 缺省的装饰
	 * 文件的共同属性
	 * *
	 * @param fileItem
	 * @return
	 * @throws FileBeforeProcessorException
	 */
	protected AbstractFile doDefaultBeforeProcessor() throws FileBeforeProcessorException{
		AbstractFile abstractFile = fileBeforeProcessor.doBeforeSave();
		if(null != fileItem && null != paramMap){
			String fileName = fileItem.getName();
			float fielSize = fileItem.getSize();
			//扩展名格式
			String extName = FileItemUtil.getFileExtName(fileName);
			String shortExtName = FileItemUtil.getShortFileExtName(extName);
			String urlParam = TCUtil.sv(paramMap.get("urlParam"));
			if(StringUtils.isNotEmpty(urlParam)){
				JSONObject urlParamJo = JSONObject.fromObject(urlParam);
				if(urlParamJo.containsKey("fileName")){
					fileName = urlParamJo.getString("fileName");
					if(!fileName.contains(".")){
						fileName += "." + shortExtName;

					}
				}
			}
			abstractFile.setName(fileName);
			abstractFile.setExt(shortExtName);
			//存储kb
			abstractFile.setSize(fielSize > 0 ? ValueFormatUtil.fv(fielSize / 1024) : 0);
			//新文件名称
			String newFileName = getNewFileName(fileName, extName);
			abstractFile.setBlockName(subBlockName);
			abstractFile.setLocalName(newFileName);
			abstractFile.setVisitAddr(getVisitAddr(abstractFile.getLocalName()));
			abstractFile.setCreateTime(DateUtil.getCurrentDateTime());
		}
		return abstractFile;
	}

	/**
	 * 生成的新文件的名称
	 * *
	 * @param fileName 文件全称 比如：a.jpg
	 * @param extName  文件扩展名称：比如.jpg
	 * @return
	 */
    protected abstract String getNewFileName(String fileName, String extName);

    protected abstract String getVisitAddr(String localName);

    /**
     * 获取自定义的文件名称
     *
     * *
     * @param fileName
     * @param extName
     * @return
     */
    protected  String getDefaultNewFileName(String fileName, String extName, String defaultCustomName){
    	String newFileName = fileName;
		//String fileExt = FileItemUtil.getShortFileExtName(fileName);
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		//客户端是否制定重新生成文件名称
		String isChangeName = "";
		if(!StringUtils.isEmpty(urlParam)){
			JSONObject jo = JSONObject.fromObject(urlParam);
			if(jo.containsKey(FileParam.IS_CHANGE_NAME)){
				isChangeName = jo.getString(FileParam.IS_CHANGE_NAME);
			}
		}
		if(StringUtils.isEmpty(isChangeName)){
			if(StringUtils.isEmpty(defaultCustomName)){
				//如果是上传没有指定是否要更改文件名称，则随机生成唯一的文件名称
				newFileName = getFileNamePrefix(fileServer, subBlockName) + extName;
			}
			else {
				newFileName = defaultCustomName;
			}
		}
		return newFileName;
    }
    /**
     *
     * *
     * @param localName
     * @return
     */
    protected  String getDefaultVisitAddr(String localName){
    	String visitAddr = localName;
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		//客户端是否制定重新生成文件名称
		String isChangeName = "";
		if(!StringUtils.isEmpty(urlParam)){
			JSONObject jo = JSONObject.fromObject(urlParam);
			if(jo.containsKey(FileParam.IS_CHANGE_NAME)){
				isChangeName = jo.getString(FileParam.IS_CHANGE_NAME);
			}
		}
		if(!StringUtils.isEmpty(isChangeName)){
			visitAddr = getFileNameWithLocalInfo(fileServer, isChangeName, localName);
		}
		return visitAddr;
    }
}

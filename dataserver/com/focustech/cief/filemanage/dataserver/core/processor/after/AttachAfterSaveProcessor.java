package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.focustech.cief.filemanage.dataserver.common.utils.DateUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileItemUtil;
import com.focustech.cief.filemanage.dataserver.core.attachoperate.AttachFactory;
import com.focustech.cief.filemanage.dataserver.core.attachoperate.IAttach;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileType;
import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.ModelInfo;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * 后置处理器-3d模型文件zip上传，解压缩提供xml文件访问
 * *
 * @author lihaijun
 *
 */
public class AttachAfterSaveProcessor extends AbstractFileAfterSaveProcessor {

	@Override
	public Map<String, String> doAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap) throws FileAfterProcessorException {
		super.doAfterSave(abstractFile, paramMap);
		Map<String, String> returnMap = null;
		//url参数
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		JSONObject jsonObj = JSONObject.fromObject(urlParam);
		String attachType = "";
		if(jsonObj.containsKey(ATTACH_TYPE)){
			attachType = jsonObj.getString(ATTACH_TYPE);
		} else {
			attachType = jsonObj.getString(FileParam.FILE_TYPE);
		}
		//是模型附件
		if(FileType.MODEL.equals(attachType)){
			//过来模型返回文件的格式
			String modelExtFilter = "";
			boolean isComp = false;
			if(jsonObj.containsKey(ATTACH_FORMAT_STR)){
				modelExtFilter = jsonObj.getString(ATTACH_FORMAT_STR);
			}
			//模型组件
			if(jsonObj.containsKey(IS_COMP)){
				isComp = Boolean.parseBoolean(jsonObj.getString(IS_COMP));
			}
			returnMap = doUnZip(abstractFile, modelExtFilter, isComp);
		} else {
			returnMap = new HashMap<String, String>();
			//普通附件
		}
		//抽取里面key值放进数组里面
		extractReturnMapKeyAry(returnMap);
		return returnMap;
	}

	/**
	 * 解压压缩文件
	 * *
	 * @param abstractFile
	 * @return
	 */
	private Map<String, String> doUnZip(AbstractFile abstractFile, String modelExtFilter, boolean isComp){
		//附件的名字
		String attachName = abstractFile.getName();
		//获取附件操作对象，目前是zip附件，以后有其他格式附件
		IAttach attachOperate = AttachFactory.getAttach(FileItemUtil.getShortFileExtName(attachName), isComp);
		//如果上传的3d模型不是压缩包的，而是普通的场景文件，则不需要继续下面的操作
		if(null == attachOperate){
			return null;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		List<Map<String, String>> unCompressMap = attachOperate.unCompress(abstractFile, isComp, modelExtFilter);
		boolean isValidModelFile = isValidModelFile(unCompressMap);
		returnMap.put(IS_VALID_MODEL_FILE, TCUtil.sv(isValidModelFile));
		if(isValidModelFile && isComp){
			returnMap = saveModelFileDataList(returnMap, unCompressMap, abstractFile);
		}
		return returnMap;
	}
	/**
	 * 是否合法的模型文件
	 * *
	 * @param isComp
	 * @param isListContent
	 * @param unCompressMap
	 * @param saveModelFileDataList
	 */
	private boolean isValidModelFile(List<Map<String, String>> unCompressMap) {
		boolean isHaveTxtFile = false;
		boolean isHaveU3dFile = false;
		for(int i = 0, j = unCompressMap.size(); i < j; i ++){
			Map<String, String> map = unCompressMap.get(i);
			String fileName = map.get(FileParam.FILE_NAME);
			if(fileName.endsWith(IAttach.MODEL_JSON_FILE)){
				isHaveTxtFile = true;
			}
			if(fileName.endsWith(IAttach.MODEL_FILE_1)){
				isHaveU3dFile = true;
			}
		}
		return isHaveTxtFile && isHaveU3dFile;
	}

	/**
	 * 保存信息到数据库
	 * *
	 * @param modelFileNameAry 模型压缩文件里面的小文件名称数组
	 * @param subBlockVisitUrl 模型压缩文件里面的小文件访问子目录路径
	 * @param attachFile 模型压缩文件信息对象
	 * @return
	 */
	private Map<String, String> saveModelFileDataList(Map<String, String> returnMap, List<Map<String, String>> unCompressMap, AbstractFile attachFile){
		//模型文件路径
		if(!TCUtil.isEmpty(unCompressMap)){
			for (int i = 0, j = unCompressMap.size(); i < j; i ++) {
				saveModelFileData(i, unCompressMap.get(i), attachFile, returnMap);
			}
		}
		return returnMap;
	}
	/**
	 *
	 * 保存模型解压后需要记录到数据库的文件，
	 * 可以是x3dm,x3dScene,jpg,png等文件格式
	 * *
	 * @param visitSubFoldAddr 子目录的访问路径
	 * @param attachFile 压缩文件
	 * @param isComp 是否组件化的
	 * @param returnMap 返回的map
	 * @param modelFileNameAry 模型文件数组
	 * @param isReturn 是否向客户端返回文件的信息
	 */
	private void saveModelFileData(int lineNo, Map<String, String> modelFileMap, AbstractFile attachFile, Map<String, String> returnMap) {
		//模型压缩文件的Sn
		Long parentFileSn = -1L;
		if(null != attachFile){
			parentFileSn = attachFile.getSn();
			ModelInfo modelInfo = null;
			String modelFilePath = modelFileMap.get(FileParam.FILE_NAME);
			String blockName = modelFileMap.get(FileParam.BlOCK_NAME);
			String shortFileExtName = FileItemUtil.getShortFileExtName(modelFilePath);
			//因为模型核心文件是单独放在日期文件夹下面的，所以这边要重新设置文件访问路径
			/*if(shortFileExtName.equals(IAttach.MODEL_JSON_FILE) && Boolean.parseBoolean(isComp)){
				visitAddr = FilePathUtil.removeLastSeparator(FilePathUtil.removeLastSeparator(visitSubFoldAddr)) + FileParam.URL_SEPARATOR + modelFilePath;
				modelFilePath = FilePathUtil.removeFirstSeparator(modelFilePath);
			}*/
			modelInfo = new ModelInfo();
			modelInfo.setName(modelFilePath);
			modelInfo.setExt(shortFileExtName);
			modelInfo.setBlockName(blockName);
			modelInfo.setLocalName(modelFilePath);
			DataServerNode serverNode = attachFile.getServerNode();
			modelInfo.setVisitAddr(getFileNameWithLocalInfo(serverNode, blockName, modelInfo.getLocalName()));
			modelInfo.setServerSn(serverNode.getSn());
			modelInfo.setParentFileSn(parentFileSn);
			modelInfo.setCreateTime(DateUtil.getCurrentDateTime());
			//保存到数据库
			String sn = String.valueOf(fileService.saveChildFile(modelInfo));
			//模型访问路径不暴露给客户端
			//String visitUrl = modelInfo.getVisitAddr();
			//返回信息
			returnMap.put(MODEL_FILE_ID_PREFIX + lineNo, sn);
			returnMap.put(MODEL_FILE_URL_PREFIX + lineNo, "now do not support");
			returnMap.put(MODEL_FILE_PATH_PREFIX + lineNo, modelFilePath);
		}
	}

	@Override
	public String[] getReturnMapKeyAry() {
		return super.returnMapKeyAry;
	}
}

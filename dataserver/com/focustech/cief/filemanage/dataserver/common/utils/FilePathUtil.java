package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;
import com.focustech.cief.filemanage.dataserver.core.subfoldname.SubFoldNameManager;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.StringUtils;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * 文件路径工具类
 * *
 * @author lihaijun
 *
 */
public class FilePathUtil {
	/**
	 * 获取子块路径
	 * *
	 * @param fileServer
	 * @param subBlockName
	 * @return
	 */
	public static String getSubBlockPhysicalPath(DataServerNode fileServer, String subBlockName){
		return fileServer.getFileRootPath() + fileServer.getRootFoldName() + FileParam.FILE_SEPARATOR + subBlockName + FileParam.FILE_SEPARATOR;
	}
	/**
	 * 获取子块路径
	 * *
	 * @param fileServer
	 * @param subBlockName
	 * @return
	 */
	public static String getSubBlockPhysicalPath(AbstractFile abstractFile){
		DataServerNode fileServer = abstractFile.getServerNode();
		return fileServer.getFileRootPath() + fileServer.getRootFoldName() + FileParam.FILE_SEPARATOR + abstractFile.getBlockName() + FileParam.FILE_SEPARATOR;
	}

	/**
	 * 获取文件本地物理路径
	 * *
	 * @param fileServer
	 * @param visitAddr 文件的访问路径
	 * @return
	 */
	public static String getFilePhysicalPath(AbstractFile abstractFile){
		DataServerNode fileServer = abstractFile.getServerNode();
		return fileServer.getFileRootPath() + fileServer.getRootFoldName() + FileParam.FILE_SEPARATOR + abstractFile.getBlockName() + FileParam.FILE_SEPARATOR + abstractFile.getLocalName();
	}
	/**
	 * 获取子块访问路径
	 * *
	 * @param fileServer
	 * @param subBlockName
	 * @return
	 */
	public static String getSubBlockVisitUrl(DataServerNode fileServer, String subBlockName){
		//return fileServer.getUrl() + FileParam.URL_SEPARATOR + fileServer.getRootFoldName() + FileParam.URL_SEPARATOR + subBlockName + FileParam.URL_SEPARATOR;
		return subBlockName + FileParam.URL_SEPARATOR;
	}
	/**
	 *获取子块访问路径
	 * *
	 * @param requestUrl
	 * @param fileServer
	 * @param subBlockName
	 * @return
	 */
	public static String getSubBlockVisitUrl(String requestUrl, DataServerNode fileServer, String subBlockName){
		//return requestUrl + FileParam.URL_SEPARATOR + getRootBlockName(fileServer) + FileParam.URL_SEPARATOR + subBlockName + FileParam.URL_SEPARATOR;
		return getSubBlockVisitUrl(fileServer, subBlockName);
	}

	/**
	 * 提取参数
	 * *
	 * @param abstractFile
	 * @return
	 */
	public static Map<String, String> extractParameter(AbstractFile abstractFile){
		Assert.notNull(abstractFile);
		Map<String, String> paramMap = new HashMap<String, String>();
		//文件物理路径
		String filePhysicalPath = getFilePhysicalPath(abstractFile);
		//子块目录名称
		String subBlockName = abstractFile.getBlockName();
		//子块物理路径
		String subBlockPhysicalAddr = getSubBlockPhysicalPath(abstractFile);
		//子块访问url
		DataServerNode fileServer = abstractFile.getServerNode();
		String subBlockVisitUrl = getSubBlockVisitUrl(fileServer, subBlockName);
		paramMap.put(FileParam.FILE_PHYSICAL_PATH, filePhysicalPath);
		paramMap.put(FileParam.SUB_BLOCK_NAME, subBlockName);
		paramMap.put(FileParam.SUB_BLOCK_PHYSICAL_PATH, subBlockPhysicalAddr);
		paramMap.put(FileParam.SUB_BLOCK_VISIT_URL, subBlockVisitUrl);
		return paramMap;
	}
	/**
	 * 获取路径中文件名称
	 * 例如：演播大厅_ys/c03-bv/c04-chr-0003-01.x3dm
	 * 返回 c04-chr-0003-01.x3dm
	 * *
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path){
		return path.substring(path.lastIndexOf(path.contains(File.separator) ? File.separator : FileParam.URL_SEPARATOR) + 1, path.length());
	}
	/**
	 *
	 * *
	 * @param subBlockPhysicalPath
	 * @return
	 */
	public static String getSubBlockName(String subBlockPhysicalPath){
		String s1 = subBlockPhysicalPath.substring(0, subBlockPhysicalPath.lastIndexOf(FileParam.FILE_SEPARATOR));
		return s1.substring(s1.lastIndexOf(FileParam.FILE_SEPARATOR) + 1, s1.length());
	}
	/**
	 * 去除路径最后一个文件路径分隔符
	 * *
	 * @param path
	 * @return
	 */
	public static String removeLastSeparator(String path){
		String sepaator = path.contains(File.separator) ? File.separator : FileParam.URL_SEPARATOR;
		return path.substring(0, path.lastIndexOf(sepaator));
	}
	/**
	 *去除路径第一个文件路径分隔符
	 * *
	 * @param path
	 * @return
	 */
	public static String removeFirstSeparator(String path){
		String sepaator = path.contains(File.separator) ? File.separator : FileParam.URL_SEPARATOR;
		return path.substring(path.lastIndexOf(sepaator) + 1);
	}
	/**
	 * 获取组件化目录物理路径
	 * *
	 * @param subBlockPhysicalPath
	 * @return
	 */
	public static String getCompBlockPhysicalPath(AbstractFile abstractFile){
		return getSubBlockPhysicalPath(abstractFile, FileTypeOrExtProcRelation.SUB_FOLDER_COMPONENTS);
	}

	/**
	 * 获取组件化目录物理路径
	 * *
	 * @param subBlockPhysicalPath
	 * @return
	 */
	public static String getDataBlockPhysicalPath(AbstractFile abstractFile){
		return getSubBlockPhysicalPath(abstractFile, FileTypeOrExtProcRelation.SUB_FOLDER_DATE);
	}
	/**
	 * 获取子块本地物理路径
	 * *
	 * @param abstractFile
	 * @param customSubBlockType 指定block类型，如果为空，则默认定位到当前文件的块路径
	 * @return
	 */
	public static String getSubBlockPhysicalPath(AbstractFile abstractFile, String customSubBlockType){
		if(StringUtils.isEmpty(customSubBlockType)){
			//自己block路径
			return getFilePhysicalPath(abstractFile);
		}
		DataServerNode fileServer = abstractFile.getServerNode();
		return  fileServer.getFileRootPath() + fileServer.getRootFoldName() + FileParam.FILE_SEPARATOR + SubFoldNameManager.getRealSubFoldName(customSubBlockType) + File.separator;
	}

	/**
	 * 获取组件化目录访问url路径
	 * *
	 * @param subBlockPhysicalPath
	 * @return
	 */
	public static String getCompBlockVisitUrl(String subBlockVisitUrl){
		return removeLastSeparator(removeLastSeparator(subBlockVisitUrl))
		+ FileParam.URL_SEPARATOR
		+ SubFoldNameManager.getRealSubFoldName(FileTypeOrExtProcRelation.SUB_FOLDER_COMPONENTS)
		+ FileParam.URL_SEPARATOR;
	}
}

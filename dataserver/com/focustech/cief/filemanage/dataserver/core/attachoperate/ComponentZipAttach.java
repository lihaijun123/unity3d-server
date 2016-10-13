package com.focustech.cief.filemanage.dataserver.core.attachoperate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.focustech.cief.filemanage.dataserver.common.utils.FileItemUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
/**
 * zip附件操作
 * *
 * @author lihaijun
 *
 */
public class ComponentZipAttach implements IAttach {

	@Override
	public List<Map<String, String>> unCompress(AbstractFile abstractFile, boolean isComp, String attachFormatStr) {
		List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
		//解压文件输出block
		String outputRootBlockPath = FilePathUtil.getSubBlockPhysicalPath(abstractFile);
		if(isComp){
			//解压到组件化block中
			outputRootBlockPath = FilePathUtil.getCompBlockPhysicalPath(abstractFile);
		}
		File outputDirectoryFile = new File(outputRootBlockPath);
		if (!outputDirectoryFile.exists()) {
			outputDirectoryFile.mkdir();
		}
		ZipFile zipFile = null;
		Enumeration e = null;;
		try {
			//zip源文件
			zipFile = new ZipFile(FilePathUtil.getFilePhysicalPath(abstractFile), "GBK");
			e = zipFile.getEntries();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		ZipEntry zipEntry = null;
		while (e.hasMoreElements()) {
			zipEntry = (ZipEntry) e.nextElement();
			String zipEntryName = zipEntry.getName();
			if (zipEntry.isDirectory()) {

			} else {
				zipEntryName = FilePathUtil.getFileNameFromPath(zipEntryName.replace('\\', '/'));
				String zipEntryExt = FileItemUtil.getShortFileExtName(zipEntryName);
				String subBlockName = FilePathUtil.getSubBlockName(outputRootBlockPath);
				String packageFileOutputPath = outputRootBlockPath + zipEntryName;
				//只返回后缀名匹配的模型文件
				if(isComp && FileUtil.isContainFilterFileExt(zipEntryExt, attachFormatStr, DEFAULT_ATTACH_FORMAT_ARY)){
					Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
					//模型txt文件解压放在日期block下
					if(MODEL_JSON_FILE.equals(zipEntryExt)){
						//移动到时间目录里面
						String dataBlockPath = FilePathUtil.getDataBlockPhysicalPath(abstractFile);
						subBlockName = FilePathUtil.getSubBlockName(dataBlockPath);
						//创建时间块目录
						File sceneFileOutputDic = new File(packageFileOutputPath);
						if(!sceneFileOutputDic.exists()){
							sceneFileOutputDic.mkdir();
						}
						File dataBlockDic = new File(dataBlockPath);
						if(!dataBlockDic.exists()){
							dataBlockDic.mkdir();
						}
						packageFileOutputPath = dataBlockPath + zipEntryName;
					} else {
						subBlockName = FilePathUtil.getSubBlockName(outputRootBlockPath);
					}
					returnMap.put(FileParam.FILE_NAME, zipEntryName);
					returnMap.put(FileParam.BlOCK_NAME, subBlockName);
					returnList.add(returnMap);
				}
				/*//解压包内文件解压路径
				if(isComp && MODEL_JSON_FILE.equals(zipEntryExt)){
					//移动到时间目录里面
					packageFileOutputPath = FilePathUtil.getDataBlockPhysicalPath(abstractFile);
					subBlockName = FilePathUtil.getSubBlockName(outputRootBlockPath);
					//创建时间目录
					File sceneFileOutputDic = new File(packageFileOutputPath);
					if(!sceneFileOutputDic.exists()){
						sceneFileOutputDic.mkdir();
					}
					packageFileOutputPath += File.separator + zipEntryName;
				} else {
					packageFileOutputPath = outputRootBlockPath + zipEntryName;
				}*/
				File f = new File(packageFileOutputPath);
				InputStream in = null;
				FileOutputStream out = null;
				try {
					f.createNewFile();
					in = zipFile.getInputStream(zipEntry);
					out = new FileOutputStream(f);
					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
				finally {
					if(null != out){
						try {
							out.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if(null != in){
						try {
							in.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
		//释放资源
		ZipFile.closeQuietly(zipFile);
		//删除zip文件
		//FileUtil.delFile(zipFilePath);
		/*if(!StringUtil.isEmpty(modelFilePaths)){
			returnMap.put(FileParam.MODEL_FILE_PATHS, modelFilePaths.substring(0, modelFilePaths.length() - 1));
		}*/
		return returnList;
	}

	/**
	 * 创建解压缩的子目录
	 *
	 * @param directory
	 * @param subDirectory
	 */
	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if ("".equals(subDirectory) && !fl.exists())
				fl.mkdir();
			else if (!"".equals(subDirectory)) {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (!subFile.exists()) {
						subFile.mkdir();
					}
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

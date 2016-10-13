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

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.core.utils.StringUtils;
/**
 * zip附件操作
 * *
 * @author lihaijun
 *
 */
public class ZipAttach implements IAttach {

	/**
	 *	是否包含过来的文件格式
	 * *
	 * @param attachFormatStr
	 * @return
	 */
	private boolean isContainFilterFileExt(String fileExt, String attachFormatStr){
		String[] extAry = DEFAULT_ATTACH_FORMAT_ARY;
		if(!StringUtils.isEmpty(attachFormatStr)){
			try {
				extAry = attachFormatStr.split(",");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		boolean flag = false;
		for (String ext : extAry) {
			if(fileExt.equals(ext)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	@Override
	public List<Map<String, String>> unCompress(AbstractFile abstractFile, boolean isComp, String attachFormatStr) {
		List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
		//模型文件路径和名称字符串
		String outputRootBlockPath = FilePathUtil.getSubBlockPhysicalPath(abstractFile);
		File outputDirectoryFile = new File(outputRootBlockPath);
		if (!outputDirectoryFile.exists()) {
			outputDirectoryFile.mkdir();
		}
		ZipFile zipFile = null;
		Enumeration e = null;;
		try {
			zipFile = new ZipFile(FilePathUtil.getFilePhysicalPath(abstractFile), "GBK");
			e = zipFile.getEntries();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		ZipEntry zipEntry = null;
		while (e.hasMoreElements()) {
			zipEntry = (ZipEntry) e.nextElement();
			String zipEntryName = zipEntry.getName();
			String subBlockName = FilePathUtil.getSubBlockName(outputRootBlockPath);
			if (zipEntry.isDirectory()) {
				zipEntryName = zipEntryName.substring(0, zipEntryName.length() - 1);
				File f = new File(outputRootBlockPath + File.separator + zipEntryName);
				f.mkdir();
			} else {
				zipEntryName = zipEntryName.replace('\\', '/');
				if (zipEntryName.indexOf("/") != -1) {
					createDirectory(outputRootBlockPath, zipEntryName.substring(0,zipEntryName.lastIndexOf("/")));
				}
				String zipEntryExt = zipEntryName.subSequence(zipEntryName.lastIndexOf(".") + 1, zipEntryName.length()).toString();
				if(isContainFilterFileExt(zipEntryExt, attachFormatStr)){
					Map<String, String> returnMap = new ConcurrentHashMap<String, String>();
					returnMap.put(FileParam.FILE_NAME, zipEntryName);
					returnMap.put(FileParam.BlOCK_NAME, subBlockName);
					returnList.add(returnMap);
				}

				File f = new File(outputRootBlockPath + FileParam.FILE_SEPARATOR + zipEntryName);
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
					e1.printStackTrace();
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

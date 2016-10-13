package com.focustech.cief.filemanage.dataserver.core.attachoperate;

import java.util.List;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.model.AbstractFile;

/**
 * 附件操作，目前解压支持zip
 * 后续支持rar，等等
 * *
 * @author lihaijun
 *
 */
public interface IAttach {
	/**3d模型文件格式,目前3种*/
	public static final String MODEL_JSON_FILE = "txt";
	public static final String MODEL_FILE_1 = "unity3d";
	public static final String MODEL_FILE_2 = "assetbundle";
	public static final String[] DEFAULT_ATTACH_FORMAT_ARY = new String[]{MODEL_JSON_FILE, MODEL_FILE_1, MODEL_FILE_2};
	/**
	 * 解压
	 * *
	 * @param abstractFile zip源文件
	 * @param isComp 是否组件化
	 * @param attachFormatStr 压缩包内匹配的文件后缀
	 * @return
	 */
	public List<Map<String, String>> unCompress(AbstractFile abstractFile, boolean isComp, String attachFormatStr);
}

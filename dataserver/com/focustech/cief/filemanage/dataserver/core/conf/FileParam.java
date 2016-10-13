package com.focustech.cief.filemanage.dataserver.core.conf;

import java.io.File;

/**
 *	参数
 * *
 * @author lihaijun
 *
 */
public interface FileParam {
	/***/
	public static final String FILE_URL = "fileUrl";
	/**文件编号*/
	public static final String FILE_ID = "fileId";
	/***/
	public static final String RETURN_JO = "jsonValue";
	/**目录分隔符*/
	public static final String FILE_SEPARATOR = File.separator;
	/**url访问目录分隔符*/
	public static final String URL_SEPARATOR = "/";
	/**文件访问路径*/
	public static final String FILE_VISIT_URL = "fileVisitUrl";
	/**url参数*/
	public static final String URL_PARAM = "urlParam";

	public static final String FILE_SERVER = "fileServer";
	/**原文件SN*/
	public static final String ORIGN_SN = "orinSn";
	/**文件类型*/
	public static final String FILE_TYPE = "type";

	public static final String FILE_NAME_PREFIX = "C";
	/**
	 * 文件夹类型-默认是存放在日期命名的文件夹下面，
	 * 如果指定则存放在指定的文件夹下面1-人物模型文件夹2-3D产品体验区文件夹3-演播厅视频直播模型文件夹
	 * */
	public static final String SUB_FOLDER_TYPE = "folderType";
	/**模型文件路径,多个用逗号分隔*/
	public static final String MODEL_FILE_PATHS = "modelFilePaths";
	/**模型图片文件路径,多个用逗号分隔*/
	public static final String MODEL_FILE_OTHER_PATHS = "modelFileOtherPaths";
	/**图片格式*/
	public static final String PIC_EXE_JPG = "jpg";
	/**物理路径*/
	public final static String FILE_PHYSICAL_PATH = "physicalAddr";
	/**子目录名称*/
	public final static String SUB_BLOCK_NAME = "subFoldName";
	/**物理子目录路径*/
	public final static String SUB_BLOCK_PHYSICAL_PATH = "physicalSubFoldAddr";
	/**访问路径的子目录*/
	public final static String SUB_BLOCK_VISIT_URL = "visitSubFoldAddr";
	/**图片格式链接符*/
	public final static String PIC_EXE_JOIN_STR = "\\*";
	/**文件下载名称*/
	public final static String DOWNLOAD_FILE_NAME = "fileName";
	public final static String DOWNLOAD_FILE_VISIT_ADDR = "visitAddr";
	/**文件下载路径*/
	public final static String DOWNLOAD_FILE_PATH = "filePath";
	/**是否改变名称-上传文件的时候是否需要改变名称*/
	public final static String IS_CHANGE_NAME = "isChangeName";

	public final static String FILE_NAME = "fileName";
	public final static String BlOCK_NAME = "blockName";
	public final static String BlOCK_ID = "blockid";
	public final static String ERROR_RESPONSE = "error_response";


}

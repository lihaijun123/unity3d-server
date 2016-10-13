package com.focustech.cief.filemanage.dataserver.core.conf;

import java.util.HashMap;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.core.processor.after.AttachAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.after.PanoramaFileAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.after.PicAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.after.VideoAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.before.OtherFileBeforeProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.before.PicBeforeProcessor;
import com.focustech.cief.filemanage.dataserver.core.subfoldname.SubFoldDateNameStrategy;
import com.focustech.cief.filemanage.dataserver.core.subfoldname.SubFoldFixedNameStrategy;
import com.focustech.cief.filemanage.dataserver.model.AlbumInfo;
import com.focustech.cief.filemanage.dataserver.model.AttachInfo;
import com.focustech.cief.filemanage.dataserver.model.AudioInfo;
import com.focustech.cief.filemanage.dataserver.model.ModelInfo;
import com.focustech.cief.filemanage.dataserver.model.OtherFile;
import com.focustech.cief.filemanage.dataserver.model.PanoramaInfo;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
import com.focustech.cief.filemanage.dataserver.model.VideoInfo;

/**
 * 上传的文件类型或者格式和处理类的对应关系
 * *
 * @author lihaijun
 *
 */
public class FileTypeOrExtProcRelation {
	/**默认*/
	public final static String OTHER = "other";
	/**图片*/
	public final static String JPG = "jpg";
	/**图片*/
	public final static String JIF = "gif";
	/**图片*/
	public final static String JPEG = "jpeg";
	/**图片*/
	public final static String PNG = "png";
	/**图片*/
	public final static String BMP = "bmp";
	/**图册*/
	public final static String ALBUM = "album";
	/**视频*/
	public final static String WMV = "wmv";
	public final static String FLV = "flv";
	public final static String MP4 = "mp4";
	public final static String SWF = "swf";
	/**音频*/
	public final static String MP3 = "mp3";
	/**音频*/
	public final static String WMA = "wma";
	/**附件*/
	public final static String RAR = "rar";
	public final static String ZIP = "zip";
	/**3d模型*/
	public final static String DS3 = "3ds";
	/**3d模型*/
	public final static String MA = "ma";
	/**3d模型*/
	public final static String MB = "mb";
	public final static String XML = "xml";
	public final static String UNITY3D = "unity3d";
	/**360全景*/
	public final static String FLASH = "flash";
	/**子目录名称-时间格式*/
	public final static String SUB_FOLDER_DATE = "0";
	/**子目录名称-任务模型*/
	public final static String SUB_FOLDER_PEOPLE_MODEL = "1";//"skinnedModel";
	/**子目录名称-3D产品体验区*/
	public final static String SUB_FOLDER_EXPERIENCE_ZONE_3D = "2";//"chanpintiyanqu";
	/**子目录名称-演播厅视频录制*/
	public final static String SUB_FOLDER_STUDIO_LIVE = "3";//"studiolive";
	/**子目录名称-模型组件化*/
	public final static String SUB_FOLDER_COMPONENTS = "4";//"components";
	/**子目录名称-模型组件化*/
	public final static String SUB_FOLDER_COMPONENTS_NAME = "components";
	/**文件类型对应的文件类*/
	public final static Map<String, String> TYPE_FILE_CLASS_MAP = new HashMap<String, String>();
	/**文件保存前对应的处理类*/
	public final static Map<String, String> TYPE_FILE_BEFORE_SAVE_PROCESS_MAP = new HashMap<String, String>();
	/**文件保存后对应的处理类*/
	public final static Map<String, String> TYPE_FILE_AFTER_SAVE_PROCESS_MAP = new HashMap<String, String>();
	/**存放文件的子目录文件夹名称对应的类关系*/
	public final static Map<String, String> SUB_FOLDER_TYPE_CLASS_MAP = new HashMap<String, String>();
	/**存放文件的子目录文件夹名称对应关系*/
	public final static Map<String, String> SUB_FOLDER_TYPE_NAME_MAP = new HashMap<String, String>();
	static
	{
		//图片
		TYPE_FILE_CLASS_MAP.put(JPG, PicInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(JIF, PicInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(JPEG, PicInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(PNG, PicInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(BMP, PicInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.PIC, PicInfo.class.getName());
		//图册
		TYPE_FILE_CLASS_MAP.put(ALBUM, AlbumInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.ALBUM, AlbumInfo.class.getName());
		//视频
		TYPE_FILE_CLASS_MAP.put(WMV, VideoInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FLV, VideoInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.VIDEO, VideoInfo.class.getName());
		//音频
		TYPE_FILE_CLASS_MAP.put(MP3, AudioInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(WMA, AudioInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.AUDIO, AudioInfo.class.getName());
		//附件
		TYPE_FILE_CLASS_MAP.put(RAR, AttachInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(ZIP, AttachInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.ATTACH, AttachInfo.class.getName());
		//3d模型
		TYPE_FILE_CLASS_MAP.put(DS3, ModelInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(MA, ModelInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(MB, ModelInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(XML, ModelInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.MODEL, ModelInfo.class.getName());
		//360全景
		TYPE_FILE_CLASS_MAP.put(FLASH, PanoramaInfo.class.getName());
		TYPE_FILE_CLASS_MAP.put(FileType.PANORAMA, PanoramaInfo.class.getName());
		//其他类型文件
		TYPE_FILE_CLASS_MAP.put(OTHER, OtherFile.class.getName());
		//装饰类
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(JPG, PicBeforeProcessor.class.getName());
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(JIF, PicBeforeProcessor.class.getName());
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(JPEG, PicBeforeProcessor.class.getName());
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(PNG, PicBeforeProcessor.class.getName());
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(BMP, PicBeforeProcessor.class.getName());
		TYPE_FILE_BEFORE_SAVE_PROCESS_MAP.put(OTHER, OtherFileBeforeProcessor.class.getName());
		//视频
		TYPE_FILE_AFTER_SAVE_PROCESS_MAP.put(FileType.VIDEO, VideoAfterSaveProcessor.class.getName());
		//图片
		TYPE_FILE_AFTER_SAVE_PROCESS_MAP.put(FileType.PIC, PicAfterSaveProcessor.class.getName());
		//附件
		TYPE_FILE_AFTER_SAVE_PROCESS_MAP.put(FileType.ATTACH, AttachAfterSaveProcessor.class.getName());
		TYPE_FILE_AFTER_SAVE_PROCESS_MAP.put(FileType.MODEL, AttachAfterSaveProcessor.class.getName());
		//360全景
		TYPE_FILE_AFTER_SAVE_PROCESS_MAP.put(FileType.PANORAMA, PanoramaFileAfterSaveProcessor.class.getName());
		//子文件夹名称
		SUB_FOLDER_TYPE_CLASS_MAP.put(SUB_FOLDER_DATE, SubFoldDateNameStrategy.class.getName());
		SUB_FOLDER_TYPE_CLASS_MAP.put(SUB_FOLDER_EXPERIENCE_ZONE_3D, SubFoldDateNameStrategy.class.getName());
		SUB_FOLDER_TYPE_CLASS_MAP.put(SUB_FOLDER_STUDIO_LIVE, SubFoldDateNameStrategy.class.getName());
		SUB_FOLDER_TYPE_CLASS_MAP.put(SUB_FOLDER_PEOPLE_MODEL, SubFoldFixedNameStrategy.class.getName());
		SUB_FOLDER_TYPE_CLASS_MAP.put(SUB_FOLDER_COMPONENTS, SubFoldFixedNameStrategy.class.getName());
		SUB_FOLDER_TYPE_NAME_MAP.put(SUB_FOLDER_DATE, "");
		SUB_FOLDER_TYPE_NAME_MAP.put(SUB_FOLDER_EXPERIENCE_ZONE_3D, "chanpintiyanqu");
		SUB_FOLDER_TYPE_NAME_MAP.put(SUB_FOLDER_STUDIO_LIVE, "studiolive");
		SUB_FOLDER_TYPE_NAME_MAP.put(SUB_FOLDER_PEOPLE_MODEL, "skinnedModel");
		SUB_FOLDER_TYPE_NAME_MAP.put(SUB_FOLDER_COMPONENTS, SUB_FOLDER_COMPONENTS_NAME);
	}
}

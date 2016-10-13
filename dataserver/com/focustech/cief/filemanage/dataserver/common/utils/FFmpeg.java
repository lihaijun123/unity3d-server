package com.focustech.cief.filemanage.dataserver.common.utils;

import com.focustech.cief.filemanage.dataserver.core.conf.VideoScreenshot;

/**
 * 视频音频处理(window)
 * *
 * @author lihaijun
 *
 */
public abstract class FFmpeg {

	public static FFmpeg getInstance(String platform){
		if(platform.equals(FFMPEG_LINUX)){
			return new FFmpegLinux();
		}
		if(platform.equals(FFMPEG_WINDOWS)){
			return new FFmpegWindow();
		}
		return null;
	}

	/**截图工具版本*/
	public final static String FFMPEG_WINDOWS = "windows";
	public final static String FFMPEG_LINUX = "linux";
	/**
	 * 获取视频截图
	 * *
	 * @param orinFilePath
	 */
	public abstract void buildVideoScreenshot(VideoScreenshot videoScreenshot);

	/**
	 * 利用FFmpeg获取音频信息
	 * *
	 * @return 视频信息
	 */
	public abstract StringBuffer getVideoInfo(VideoScreenshot videoScreenshot);

	/**
	 * 获取播放时长
	 * *
	 * @param str
	 * @return
	 */
	public abstract String dealVideoInfo(String str);
	/**
	 *
	 * *
	 * @return
	 */
	public abstract String getPlatForm();

	public abstract String getToolLocalPath();


}

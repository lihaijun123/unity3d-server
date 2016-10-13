package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.util.HashMap;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.exception.FileSaveException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.VideoInfo;
/**
 * 后置处理器-处理视频时长和截图等信息
 * *
 * @author lihaijun
 *
 */
public class VideoAfterSaveProcessor extends AbstractScreenshotProcessor{
	/**
	 * 保存数据
	 * *
	 * @param fileId
	 * @param filePlayLength
	 * @param newPic
	 * @return
	 */
	public Map<String, String> saveInfo(String filePlayLength, AbstractFile newPic, AbstractFile videoFile) throws FileSaveException{
		Map<String, String> returnMap;
		try {
			VideoInfo videoInfo = (VideoInfo) videoFile;
			videoInfo.setLength(filePlayLength);
			//更新视频播放时长
			fileService.saveParentFile(videoInfo);
			//保存截图
			newPic.setServerSn(videoInfo.getServerSn());
			String sn = String.valueOf(fileService.saveChildFile(newPic));
			returnMap = new HashMap<String, String>();
			returnMap.put(SCREENSHOT_ID, sn);
			returnMap.put(SCREENSHOT_URL, newPic.getVisitAddr());
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileSaveException("视频文件处理时保存数据到数据库时发生异常");
		}
		return returnMap;
	}

}

package com.focustech.cief.filemanage.dataserver.core.processor.after;
import java.util.HashMap;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.exception.FileSaveException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
/**
 *后置处理器-360全景
 * *
 * @author lihaijun
 *
 */
public class PanoramaFileAfterSaveProcessor extends AbstractScreenshotProcessor {

	@Override
	public Map<String, String> saveInfo(String filePlayLength, AbstractFile newPic, AbstractFile panoramaFile) throws FileSaveException {
		Map<String, String> returnMap;
		try {
			//PanoramaInfo videoInfo = (PanoramaInfo) videoFile;
			//fileService.save(videoInfo);
			//保存截图
			newPic.setServerSn(panoramaFile.getServerSn());
			String sn = String.valueOf(fileService.saveChildFile(newPic));
			returnMap = new HashMap<String, String>();
			returnMap.put(SCREENSHOT_ID, sn);
			returnMap.put(SCREENSHOT_URL, newPic.getVisitAddr());
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileSaveException("360全景文件处理时保存数据到数据库时发生异常");
		}
		return returnMap;
	}

}

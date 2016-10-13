package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.io.File;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.focustech.cief.filemanage.dataserver.common.utils.DateUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FFmpeg;
import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.ImageScalingUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.ValueFormatUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.VideoScreenshot;
import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.exception.FileSaveException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * 后置处理器-截图处理
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractScreenshotProcessor extends AbstractFileAfterSaveProcessor {

	protected final static Log log = LogFactory.getLog(AbstractScreenshotProcessor.class.getName());
	/**截图名称前缀*/
	protected static final String PIC_NAME_PREFIX = "-screenshot-";
	/**截图的编号*/
	protected static final String SCREENSHOT_ID = "screenshotId";
	/**截图的访问路径*/
	protected static final String SCREENSHOT_URL = "screenshotUrl";
	/**截图的尺寸*/
	protected static final String SCREENSHOT_SIZE = "screenshotSize";
	/**从第几秒截图*/
	protected static final String SCREENSHOT_FROM_TIME = "screenshotFromTime";
	@Override
	public Map<String, String> doAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap) throws FileAfterProcessorException {
		super.doAfterSave(abstractFile, paramMap);
		Map<String, String> extrParamMap = FilePathUtil.extractParameter(abstractFile);
		String filePhysicalPath = extrParamMap.get(FileParam.FILE_PHYSICAL_PATH);
		String subBlockPhysicalPath = extrParamMap.get(FileParam.SUB_BLOCK_PHYSICAL_PATH);
		Map<String, String> returnMap = null;
		//url参数
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		JSONObject jsonObj = JSONObject.fromObject(urlParam);
		if(jsonObj.containsKey(SCREENSHOT_SIZE) && jsonObj.containsKey(SCREENSHOT_FROM_TIME)){
			String screenshotSize = jsonObj.getString(SCREENSHOT_SIZE);
			String fromTime = jsonObj.getString(SCREENSHOT_FROM_TIME);
			//封装截图对象
			VideoScreenshot vScreenshot = new VideoScreenshot();
			vScreenshot.setOrinFilePath(filePhysicalPath);
			FFmpeg fFmpeg = FFmpeg.getInstance(MessageUtils.getInfoValue("DEPLOY_SYSTEM"));
			//截取视频播放时长
			String filePlayLength = fFmpeg.dealVideoInfo(fFmpeg.getVideoInfo(vScreenshot).toString());
			//构建图片对象
			int width = Integer.parseInt(screenshotSize.split(FileParam.PIC_EXE_JOIN_STR)[0]);
			int height = Integer.parseInt(screenshotSize.split(FileParam.PIC_EXE_JOIN_STR)[1]);
			PicInfo picInfo = new PicInfo();
			picInfo.setName(abstractFile.getName() + "-视频截图-" + width + "-" + height);
			picInfo.setWidth(width);
			picInfo.setHeight(height);
			picInfo.setExt(FileParam.PIC_EXE_JPG);
			picInfo.setSize(0);
			String picName = getPicName(vScreenshot, picInfo);
			picInfo.setBlockName(abstractFile.getBlockName());
			picInfo.setLocalName(picName);
			picInfo.setVisitAddr(picName);
			picInfo.setCreateTime(DateUtil.getCurrentDateTime());
			picInfo.setParentFileSn(abstractFile.getSn());
			try {
				//构建截图
				vScreenshot.setFromTime(fromTime);
				vScreenshot.setPicSize(screenshotSize);
				vScreenshot.setDisFilePath(subBlockPhysicalPath + picName);
				fFmpeg.buildVideoScreenshot(vScreenshot);
				//获取图片size
				File screenshotImage = new File(vScreenshot.getDisFilePath());
				byte[] imageAry = ImageScalingUtil.buildImageByteAry(screenshotImage);
				float imageSize = ValueFormatUtil.fv((float)imageAry.length / 1024);
				picInfo.setSize(imageSize);
				DataServerNode dataServerNode = abstractFile.getServerNode();
				if(dataServerNode != null){
					picInfo.setServerNode(dataServerNode);
				}
				returnMap = saveInfo(filePlayLength, picInfo, abstractFile);
			} catch (FileSaveException e) {
				log.error("截图工具没找到" + fFmpeg.getToolLocalPath());
				throw new FileAfterProcessorException("截图工具没找到" + fFmpeg.getToolLocalPath());
			}
		}
		/*else {
			throw new RuntimeException("截图参数传的不对,格式应该为{screenshotSize:xx,screenshotFromTime:xx}");
		}*/
		return returnMap;
	}

	@Override
	public String[] getReturnMapKeyAry() {
		return new String[]{SCREENSHOT_ID, SCREENSHOT_URL};
	}
	/**
	 *
	 * *
	 * @param filePlayLength
	 * @param newPic
	 * @param videoFile
	 * @return
	 * @throws FileSaveException
	 */
	public abstract Map<String, String> saveInfo(String filePlayLength, AbstractFile newPic, AbstractFile videoFile) throws FileSaveException;

	/**
	 * *
	 * @param vScreenshot
	 * @return
	 */
	private String getPicName(VideoScreenshot vScreenshot, PicInfo picInfo){
		return getFileNamePrefix(fileServer, subBlockName) + PIC_NAME_PREFIX + picInfo.getWidth() + "-" + picInfo.getHeight() + "." + FileParam.PIC_EXE_JPG;
	}

}

package com.focustech.cief.filemanage.dataserver.core.conf;
/**
 * 视频截图
 * *
 * @author lihaijun
 *
 */
public class VideoScreenshot {
	/**待截图文件硬盘路径*/
	private String orinFilePath;
	/**截图的文件保存路径*/
	private String disFilePath;
	/**图片尺寸*/
	private String picSize;
	/**从第几秒开始截图*/
	private String fromTime;
	public String getOrinFilePath() {
		return orinFilePath;
	}
	public void setOrinFilePath(String orinFilePath) {
		this.orinFilePath = orinFilePath;
	}
	public String getDisFilePath() {
		return disFilePath;
	}
	public void setDisFilePath(String disFilePath) {
		this.disFilePath = disFilePath;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
}

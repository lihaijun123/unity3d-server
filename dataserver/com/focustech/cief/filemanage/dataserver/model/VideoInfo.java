package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * 视频
 * wmv,flv
 * *
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.VIDEO)
public class VideoInfo extends AbstractFile {
	private static final long serialVersionUID = 1L;
	/**标题*/
	private String title;
	/**视频时长(HH:mm:ss格式)*/
	private String length;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Override
	public String getType() {
		return FileType.VIDEO;
	}
}

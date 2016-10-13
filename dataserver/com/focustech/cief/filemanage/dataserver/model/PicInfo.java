package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * *
 * 图片
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.PIC)
public class PicInfo  extends AbstractFile{
	private static final long serialVersionUID = 1L;
	/**宽度*/
	private int width;
	/**高度*/
	private int height;

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String getType() {
		return FileType.PIC;
	}
}

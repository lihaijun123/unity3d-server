package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * 3d模型
 * 格式（1.3ds，2.ma，3.mb）
 * *
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.MODEL)
public class ModelInfo extends AbstractFile {
	private static final long serialVersionUID = 1L;

	@Override
	public String getType() {
		return FileType.MODEL;
	}

}

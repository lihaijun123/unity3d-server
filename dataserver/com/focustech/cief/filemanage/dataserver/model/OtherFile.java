package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * 其他类型文件
 * *
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.OTHER)
public class OtherFile extends AbstractFile {

	private static final long serialVersionUID = 1L;

	@Override
	public String getType() {
		return FileType.OTHER;
	}

}

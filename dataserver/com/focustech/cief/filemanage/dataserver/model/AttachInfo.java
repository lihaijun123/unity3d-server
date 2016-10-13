package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * 附件
 * *
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.ATTACH)
public class AttachInfo extends AbstractFile {
	private static final long serialVersionUID = 1L;

	@Override
	public String getType() {
		return FileType.ATTACH;
	}

}

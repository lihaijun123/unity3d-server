package com.focustech.cief.filemanage.dataserver.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.focustech.cief.filemanage.dataserver.core.conf.FileType;

/**
 * 360全景
 * flash
 * *
 * @author lihaijun
 *
 */
@Entity
@DiscriminatorValue(value = FileType.PANORAMA)
public class PanoramaInfo extends AbstractFile {
	private static final long serialVersionUID = 1L;

	@Override
	public String getType() {
		return FileType.PANORAMA;
	}

}

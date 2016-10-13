package com.focustech.cief.filemanage.dataserver.core.subfoldname;

import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;

/**
 *
 * 固定名称的文件夹存放文件
 * *
 * @author lihaijun
 *
 */
public class SubFoldFixedNameStrategy extends AbstractSubFoldNameStrategy {

	@Override
	public String getSubFoldName(String subFoldType) {
		return FileTypeOrExtProcRelation.SUB_FOLDER_TYPE_NAME_MAP.get(subFoldType);
	}
}

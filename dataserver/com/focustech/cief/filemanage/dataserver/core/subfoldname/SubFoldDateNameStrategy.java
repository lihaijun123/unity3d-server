package com.focustech.cief.filemanage.dataserver.core.subfoldname;

import com.focustech.cief.filemanage.dataserver.common.utils.DateUtil;

public class SubFoldDateNameStrategy extends AbstractSubFoldNameStrategy {

	@Override
	public String getSubFoldName(String subFoldType) {
		return DateUtil.getCurrentDateStr();
	}

	public static SubFoldDateNameStrategy getInstance(){
		return new SubFoldDateNameStrategy();
	}
}

package com.focustech.cief.filemanage.dataserver.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
import com.focustech.cief.filemanage.dataserver.model.VideoInfo;
import com.focustech.common.utils.TCUtil;

/**
 * cief file对象到map的转换
 * *
 * @author lihaijun
 *
 */
public class CFSFileMapTransform {

	public static Map<String, String> convertToMap(AbstractFile abstractFile){
		if(abstractFile != null){
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("VISIT_ADDR", abstractFile.getVisitAddr());
			if(abstractFile instanceof VideoInfo){
				rMap.put("LENGTH", ((VideoInfo)abstractFile).getLength());
			}
			if(abstractFile instanceof PicInfo){
				rMap.put("WIDTH", TCUtil.sv(((PicInfo)abstractFile).getWidth()));
				rMap.put("HEIGHT", TCUtil.sv(((PicInfo)abstractFile).getHeight()));
			}
			rMap.put("TYPE", abstractFile.getType());
			rMap.put("NAME", abstractFile.getName());
			rMap.put("SIZE", TCUtil.sv(abstractFile.getSize()));
			rMap.put("EXT", abstractFile.getExt());
			rMap.put("PARENT_FILE_SN", TCUtil.sv(abstractFile.getParentFileSn()));
			return rMap;
		}
		return null;
	}

}

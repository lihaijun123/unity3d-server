package com.focustech.cief.filemanage.dataserver.core.attachoperate;

import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;

/**
 * 压缩包类型，主要是格式，目前是zip
 * *
 * @author lihaijun
 *
 */
public class AttachFactory {
	/**
	 * 获取压缩文件处理类
	 * *
	 * @param type
	 * @return
	 */
	public static IAttach getAttach(String type, boolean isComp){
		IAttach attach = null;
		if(FileTypeOrExtProcRelation.ZIP.equals(type.toLowerCase())){
			if(isComp){
				attach = new ComponentZipAttach();
			} else {
				attach = new ZipAttach();
			}
		}
		else {
			//对其他格式的解压
		}
		return attach;
	}
}

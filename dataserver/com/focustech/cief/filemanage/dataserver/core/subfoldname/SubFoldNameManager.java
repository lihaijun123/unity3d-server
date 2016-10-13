package com.focustech.cief.filemanage.dataserver.core.subfoldname;

import com.focustech.cief.filemanage.dataserver.common.utils.ClassUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;

/**
 *	上传文件主目录下面的子目录命名管理类
 * *
 * @author lihaijun
 *
 */
public class SubFoldNameManager {
	/**方法命名抽象*/
	private AbstractSubFoldNameStrategy subFoldNameStrategy;

	public String getSubFoldName(String foldType){
		return subFoldNameStrategy.getSubFoldName(foldType);
	}

	public AbstractSubFoldNameStrategy getSubFoldNameStrategy() {
		return subFoldNameStrategy;
	}

	public void setSubFoldNameStrategy(AbstractSubFoldNameStrategy subFoldNameStrategy) {
		this.subFoldNameStrategy = subFoldNameStrategy;
	}

	public static SubFoldNameManager getInstance(){
		return new SubFoldNameManager();
	}
	/**
	 * 获取实际的子文件目录名称
	 * *
	 * @param subFoldType
	 * @return
	 */
	public static String getRealSubFoldName(String subFoldType) {
		SubFoldNameManager subFoldNameManager = SubFoldNameManager.getInstance();
        String subFoldClassName = FileTypeOrExtProcRelation.SUB_FOLDER_TYPE_CLASS_MAP.containsKey(subFoldType) ? FileTypeOrExtProcRelation.SUB_FOLDER_TYPE_CLASS_MAP.get(subFoldType) : FileTypeOrExtProcRelation.SUB_FOLDER_TYPE_CLASS_MAP.get(FileTypeOrExtProcRelation.SUB_FOLDER_DATE);
        subFoldNameManager.setSubFoldNameStrategy((AbstractSubFoldNameStrategy)ClassUtil.getInstance(subFoldClassName));
        String subFoldName = subFoldNameManager.getSubFoldName(subFoldType);
		return subFoldName;
	}
}

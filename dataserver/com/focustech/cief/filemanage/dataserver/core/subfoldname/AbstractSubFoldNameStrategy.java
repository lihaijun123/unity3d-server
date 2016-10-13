package com.focustech.cief.filemanage.dataserver.core.subfoldname;
/**
 * 文件存储的子目录可能有很多种命名方式，方便以后扩展
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractSubFoldNameStrategy {
	/**
	 * 获取子目录的名称
	 * *
	 * @return
	 */
	public abstract String getSubFoldName(String subFoldType);
}

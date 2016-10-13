package com.focustech.cief.filemanage.dataserver.common.urlstatic;
/**
 *
 * *
 * @author lihaijun
 *
 */
public interface FieldCoder {
	/**
	 *
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public String encode(String val, String defaultVal);
	/**
	 *
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public String decode(String val, String defaultVal);
}

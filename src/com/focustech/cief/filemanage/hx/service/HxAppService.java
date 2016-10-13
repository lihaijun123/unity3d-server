package com.focustech.cief.filemanage.hx.service;

import net.sf.json.JSONObject;

/**
 *
 * *
 * @author lihaijun
 *
 */
public interface HxAppService {
	/**
	 * 根据id找到unity文件
	 * *
	 * @param id
	 * @return
	 */
	public JSONObject findUnityFile(String xmlId, int mobileType);
	/**
	 * 记录设备下载untiy文件记录
	 * *
	 * @param deviceId
	 * @param xmlId
	 * @return
	 */
	public JSONObject recordDownload(String deviceId, String xmlId);
	/**
	 * 获取树数据
	 * *
	 * @param deviceId
	 * @return
	 */
	public JSONObject listTree(String deviceId, int mobileType);
	/**
	 * 文件下载链接
	 * *
	 * @return
	 */
	public JSONObject listDownloadFile();
}

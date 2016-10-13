package com.focustech.cief.filemanage.app.service;

import java.util.List;
import java.util.Map;

/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface AppInfoDetailService<T> {
	/**
	 * app编号
	 * *
	 * @param appId
	 * @return
	 */
	T select(String appId);
	/**
	 *
	 * *
	 * @param sn 序列号
	 * @return
	 */
	T select(Long sn);
	/**
	 *
	 * *
	 * @param appSn
	 * @return
	 */
	List<T> selectByAppSn(Long appSn);
	/**
	 *
	 * *
	 * @param appDetail
	 */
	void updateOrInsert(T appDetail);
	/**
	 *
	 * *
	 * @param systemType
	 * @return
	 */
	List<T> list(Integer systemType);
	/**
	 *
	 * *
	 * @return
	 */
	List<T> listAll();
	/**
	 *
	 * *
	 * @param num
	 * @param sizeLimit
	 * @return
	 */
	Map<Long, Long> getAppFile(int num, int sizeLimit);

}

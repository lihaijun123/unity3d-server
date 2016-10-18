package com.focustech.cief.filemanage.app.service;

import java.util.List;

/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface AppInfoService<T> {

	void insertOrUpdate(T app);

	T select(Long sn);

	List<T> list(Integer status);
	List<T> list(Integer status, boolean isNeedReg);
	
	String createQrCodeContent(T app);

}

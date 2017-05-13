package com.focustech.cief.filemanage.log.service;

import java.util.List;

import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface StatAppUseTimeLogService<T> {

	void insertOrUpdate(T t);

	void update(T t);

	T select(String appName, Long userId);
	T select(String appName, String userInfo);

	public List<StatAppUseTimeLog> list();
}

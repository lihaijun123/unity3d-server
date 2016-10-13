package com.focustech.cief.filemanage.log.service;

import java.util.List;
import java.util.Map;

/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface StatScanImgLogService<T> {

	void insertOrUpdate(T t);

	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam);
}

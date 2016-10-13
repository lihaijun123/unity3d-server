package com.focustech.cief.filemanage.log.dao;

import java.util.List;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface StatScanImgLogDao<T> extends BaseHibernateDao<T> {

	/**
	 *
	 * *
	 * @param startDay
	 * @param endDay
	 * @param extendParam
	 * @return
	 */
	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam);
}

package com.focustech.cief.filemanage.log.dao;

import java.util.List;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface StatAppUseTimeLogDao<T> extends BaseHibernateDao<T> {

	T select(String appName, Long userId);

	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam);

	public List<T> list();

	StatAppUseTimeLog select(String appName, String userInfo);

}

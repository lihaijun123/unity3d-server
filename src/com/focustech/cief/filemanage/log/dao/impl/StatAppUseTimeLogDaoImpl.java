package com.focustech.cief.filemanage.log.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.log.dao.StatAppUseTimeLogDao;
import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class StatAppUseTimeLogDaoImpl extends OssHibernateDaoSupport<StatAppUseTimeLog> implements StatAppUseTimeLogDao<StatAppUseTimeLog> {

	@Override
	public StatAppUseTimeLog select(String appName, Long userId) {
		Criteria criteria = getCurrentSession().createCriteria(StatAppUseTimeLog.class);
		criteria.add(Restrictions.eq("appName", appName));
		criteria.add(Restrictions.eq("userId", userId));
		return (StatAppUseTimeLog) criteria.uniqueResult();
	}

	@Override
	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatAppUseTimeLog> list() {
		Criteria criteria = getCurrentSession().createCriteria(StatAppUseTimeLog.class);
		criteria.addOrder(Order.desc("useTime"));
		criteria.addOrder(Order.desc("appName"));
		return criteria.list();
	}

	@Override
	public StatAppUseTimeLog select(String appName, String userInfo) {
		Criteria criteria = getCurrentSession().createCriteria(StatAppUseTimeLog.class);
		criteria.add(Restrictions.eq("appName", appName));
		criteria.add(Restrictions.eq("userInfo", userInfo));
		return (StatAppUseTimeLog) criteria.uniqueResult();
	}
}

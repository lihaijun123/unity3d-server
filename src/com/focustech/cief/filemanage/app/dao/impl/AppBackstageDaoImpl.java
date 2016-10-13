package com.focustech.cief.filemanage.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.app.dao.AppInfoDao;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class AppBackstageDaoImpl extends OssHibernateDaoSupport<AppInfo> implements AppInfoDao<AppInfo>{

	@Override
	public List<AppInfo> list(Integer status) {
		Criteria c = getCurrentSession().createCriteria(AppInfo.class);
		if(status != null){
			c.add(Restrictions.or(Restrictions.eq("status", status), Restrictions.eq("status", 33)));
		}
		c.addOrder(Order.desc("updateTime"));
		return c.list();
	}

}

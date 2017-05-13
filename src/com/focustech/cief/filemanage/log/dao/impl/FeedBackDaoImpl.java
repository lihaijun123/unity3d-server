package com.focustech.cief.filemanage.log.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.log.dao.FeedBackDao;
import com.focustech.cief.filemanage.log.model.FeedBack;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class FeedBackDaoImpl extends OssHibernateDaoSupport<FeedBack> implements FeedBackDao<FeedBack> {

	@Override
	public List<FeedBack> list() {
		Criteria criteria = getCurrentSession().createCriteria(FeedBack.class);
		criteria.addOrder(Order.desc("addTime"));
		return criteria.list();
	}
}

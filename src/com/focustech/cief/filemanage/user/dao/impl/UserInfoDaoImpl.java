package com.focustech.cief.filemanage.user.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.user.dao.UserInfoDao;
import com.focustech.cief.filemanage.user.model.UserInfo;
import com.focustech.common.utils.StringUtils;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class UserInfoDaoImpl extends OssHibernateDaoSupport<UserInfo> implements UserInfoDao<UserInfo> {

	@Override
	public UserInfo exist(String mobile, String email, String appName) {
		Criteria criteria = getCurrentSession().createCriteria(UserInfo.class);
		if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(email)){
			criteria.add(Restrictions.or(Restrictions.eq("mobile", mobile), Restrictions.eq("email", email)));
		} else if(StringUtils.isNotEmpty(mobile)){
			criteria.add(Restrictions.eq("mobile", mobile));
		} else {
			criteria.add(Restrictions.eq("email", email));
		}
		criteria.add(Restrictions.eq("name", appName));
		return (UserInfo) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> list() {
		Criteria criteria = getCurrentSession().createCriteria(UserInfo.class);
		criteria.addOrder(Order.desc("addTime"));
		criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.asc("mobile"));
		criteria.addOrder(Order.asc("email"));
		return criteria.list();
	}

}

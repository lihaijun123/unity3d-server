package com.focustech.cief.filemanage.wxuser.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.wxuser.dao.WxUserInfoDao;
import com.focustech.cief.filemanage.wxuser.model.WxUserInfo;
import com.focustech.common.utils.StringUtils;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class WxUserInfoDaoImpl extends OssHibernateDaoSupport<WxUserInfo> implements WxUserInfoDao<WxUserInfo> {

	@Override
	public WxUserInfo exist(String mobile) {
		Criteria criteria = getCurrentSession().createCriteria(WxUserInfo.class);
		if(StringUtils.isNotEmpty(mobile)){
			criteria.add(Restrictions.eq("mobile", mobile));
		}
		return (WxUserInfo) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WxUserInfo> list() {
		Criteria criteria = getCurrentSession().createCriteria(WxUserInfo.class);
		criteria.addOrder(Order.desc("addTime"));
		criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.asc("mobile"));
		return criteria.list();
	}

}

package com.focustech.cief.filemanage.hx.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.hx.dao.HxDownloadDao;
import com.focustech.cief.filemanage.hx.model.HxDownload;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class HxDownloadDaoImpl extends OssHibernateDaoSupport<HxDownload> implements HxDownloadDao<HxDownload>{

	@Override
	public HxDownload select(String deviceId, long hxInfoSn) {
		Criteria criteria = getCurrentSession().createCriteria(HxDownload.class);
		criteria.add(Restrictions.eq("deviceId", deviceId)).add(Restrictions.eq("hxInfoSn", hxInfoSn));
		return (HxDownload) criteria.uniqueResult();
	}

	@Override
	public List<HxDownload> selectList(String deviceId) {
		Criteria criteria = getCurrentSession().createCriteria(HxDownload.class);
		criteria.add(Restrictions.eq("deviceId", deviceId));
		return criteria.list();
	}

}

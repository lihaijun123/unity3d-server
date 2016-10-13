package com.focustech.cief.filemanage.hx.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.hx.dao.XmlDetailDao;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class XmlDetailDaoImpl extends OssHibernateDaoSupport<XmlDetail> implements XmlDetailDao<XmlDetail> {

	@Override
	public void deleteByXmlInfo(long xmlInfoSn) {
		String hql = "delete " + XmlDetail.class.getName() + " _xml_detail where _xml_detail.xmlInfoSn=?";
		getCurrentSession().createQuery(hql).setLong(0, xmlInfoSn).executeUpdate();
	}

	@Override
	public List<XmlDetail> list(long xmInfoSn) {
		Criteria criteria = getCurrentSession().createCriteria(XmlDetail.class.getName());
		criteria.add(Restrictions.eq("xmlInfoSn", xmInfoSn));
		return criteria.list();
	}

	@Override
	public List<XmlDetail> selectListByName(String name) {
		Criteria criteria = getCurrentSession().createCriteria(XmlDetail.class.getName());
		criteria.add(Restrictions.eq("name", name));
		return criteria.list();
	}
}

package com.focustech.cief.filemanage.hx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.hx.dao.XmlInfoDao;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class XmlInfoDaoImpl extends OssHibernateDaoSupport<XmlInfo> implements XmlInfoDao<XmlInfo> {

	@Override
	public List<XmlInfo> list() {
		String hql = "from " + XmlInfo.class.getName();
		return getCurrentSession().createQuery(hql).list();
	}

	@Override
	public void updateByType(int type) {
		String hql = "update " + XmlInfo.class.getName() + " _xml_info set xmlType=?";
		getCurrentSession().createQuery(hql).setInteger(0, 0).executeUpdate();
	}

	@Override
	public List<XmlInfo> list(int type) {
		String hql = "from " + XmlInfo.class.getName() + " _xml_info where xmlType=?";
		return getCurrentSession().createQuery(hql).setInteger(0, 1).list();
	}

}

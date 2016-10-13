package com.focustech.cief.filemanage.hx.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface XmlInfoDao<T extends XmlInfo> extends BaseHibernateDao<T>{

	public List<T> list();

	public void updateByType(int type);

	public List<XmlInfo> list(int type);
}

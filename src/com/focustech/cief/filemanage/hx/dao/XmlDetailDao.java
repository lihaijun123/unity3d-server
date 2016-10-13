package com.focustech.cief.filemanage.hx.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface XmlDetailDao<T extends XmlDetail> extends BaseHibernateDao<T>{

	public void deleteByXmlInfo(long xmlInfoSn);

	public List<T> list(long xmInfoSn);

	public List<T> selectListByName(String name);
}

package com.focustech.cief.filemanage.hx.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.hx.model.HxInfo;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface HxInfoDao<T extends HxInfo> extends BaseHibernateDao<T>{

	public List<T> list(long xmlInfoSn);

	public List<T> listChild(long parentSn);

	public void delteChilds(long parentSn);

	public List<T> selectByXmlId(long xmlInfoSn);

}

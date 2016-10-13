package com.focustech.cief.filemanage.app.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface AppInfoDao<T> extends BaseHibernateDao<T> {

	List<T> list(Integer status);

}

package com.focustech.cief.filemanage.log.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface FeedBackDao<T> extends BaseHibernateDao<T> {


	public List<T> list();


}

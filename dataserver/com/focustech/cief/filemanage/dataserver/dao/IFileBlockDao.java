package com.focustech.cief.filemanage.dataserver.dao;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface IFileBlockDao<T>  extends BaseHibernateDao<T> {
	/**
	 *
	 * *
	 * @param blockName
	 * @return
	 */
	public T select(String blockName);

}

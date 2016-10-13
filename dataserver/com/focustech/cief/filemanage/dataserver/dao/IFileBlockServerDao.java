package com.focustech.cief.filemanage.dataserver.dao;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface IFileBlockServerDao<T> extends BaseHibernateDao<T> {
	/**
	 *
	 * *
	 * @param blockSn
	 * @param serverSn
	 * @return
	 */
	public T select(long blockSn, long serverSn);
}

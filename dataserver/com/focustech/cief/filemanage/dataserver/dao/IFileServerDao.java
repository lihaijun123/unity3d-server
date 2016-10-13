package com.focustech.cief.filemanage.dataserver.dao;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 * 文件服务器数据库操作
 * *
 * @author lihaijun
 *
 */
public interface IFileServerDao<T> extends BaseHibernateDao<T> {
	/**
	 * 通过url获取文件服务器
	 * *
	 * @param url
	 * @return
	 */
	public T select(String serverDomain, int serverPort);

	/**
	 * 由上传文件的大小来获取可用的服务器
	 * *
	 * @param bytesTotal
	 * @return
	 */
	public T getUsableByFileSize(double bytesTotal);
}

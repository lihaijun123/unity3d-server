package com.focustech.cief.filemanage.hx.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.hx.model.HxDownload;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface HxDownloadDao<T extends HxDownload> extends BaseHibernateDao<T>{
	/**
	 *
	 * *
	 * @param deviceId
	 * @param hxInfoSn
	 * @return
	 */
	public T select(String deviceId, long hxInfoSn);
	/**
	 *
	 * *
	 * @param deviceId
	 * @return
	 */
	public List<T> selectList(String deviceId);
}

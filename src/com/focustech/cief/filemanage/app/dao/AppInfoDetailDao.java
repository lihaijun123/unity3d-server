package com.focustech.cief.filemanage.app.dao;

import java.util.List;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface AppInfoDetailDao<T> extends BaseHibernateDao<T> {

	T selectByAppId(String appId);

	List<T> list(Integer systemType);

	List<T> selectByAppSn(Long appSn);

	List<T> listAll();

	Map<Long, Long> getAppFile(int num, int sizeLimit);

}

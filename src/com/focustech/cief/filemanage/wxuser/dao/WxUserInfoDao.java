package com.focustech.cief.filemanage.wxuser.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.wxuser.model.WxUserInfo;
/**
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface WxUserInfoDao<T extends WxUserInfo> extends BaseHibernateDao<T> {

	T exist(String mobile);

	List<T> list();

}

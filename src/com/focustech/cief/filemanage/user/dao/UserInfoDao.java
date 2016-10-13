package com.focustech.cief.filemanage.user.dao;

import java.util.List;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.user.model.UserInfo;
/**
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface UserInfoDao<T extends UserInfo> extends BaseHibernateDao<T> {

	T exist(String mobile, String email, String appName);

	List<UserInfo> list();

}

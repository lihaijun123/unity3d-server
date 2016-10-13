package com.focustech.cief.filemanage.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.user.dao.UserInfoDao;
import com.focustech.cief.filemanage.user.model.UserInfo;
import com.focustech.cief.filemanage.user.service.UserInfoService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService<UserInfo> {

	@Autowired
	private UserInfoDao<UserInfo> userInfoDao;

	@Override
	public UserInfo exist(String mobile, String email, String appName) {
		return userInfoDao.exist(mobile, email, appName);
	}

	@Override
	public void insertOrUpdate(UserInfo t) {
		userInfoDao.insertOrUpdate(t);
	}

	@Override
	public List<UserInfo> list() {
		return userInfoDao.list();
	}
}

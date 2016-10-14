package com.focustech.cief.filemanage.wxuser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.wxuser.dao.WxUserInfoDao;
import com.focustech.cief.filemanage.wxuser.model.WxUserInfo;
import com.focustech.cief.filemanage.wxuser.service.WxUserInfoService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class WxUserInfoServiceImpl implements WxUserInfoService<WxUserInfo> {

	@Autowired
	private WxUserInfoDao<WxUserInfo> userInfoDao;

	@Override
	public WxUserInfo exist(String mobile) {
		return userInfoDao.exist(mobile);
	}

	@Override
	public void insertOrUpdate(WxUserInfo t) {
		userInfoDao.insertOrUpdate(t);
	}

	@Override
	public List<WxUserInfo> list() {
		return userInfoDao.list();
	}
}

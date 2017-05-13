package com.focustech.cief.filemanage.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.log.dao.StatAppUseTimeLogDao;
import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
import com.focustech.cief.filemanage.log.service.StatAppUseTimeLogService;
import com.focustech.cief.filemanage.user.dao.UserInfoDao;
import com.focustech.cief.filemanage.user.model.UserInfo;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class StatAppUseTimeLogServiceImpl implements StatAppUseTimeLogService<StatAppUseTimeLog> {
	@Autowired
	private StatAppUseTimeLogDao<StatAppUseTimeLog> appUseTimeLogDao;
	@Autowired
	private UserInfoDao<UserInfo> userInfoDao;

	@Override
	public void insertOrUpdate(StatAppUseTimeLog appUseTimeLog) {
		appUseTimeLogDao.insertOrUpdate(appUseTimeLog);
	}

	@Override
	public StatAppUseTimeLog select(String appName, Long userId) {
		return appUseTimeLogDao.select(appName, userId);
	}
	
	@Override
	public void update(StatAppUseTimeLog t) {
		appUseTimeLogDao.update(t);
	}

	@Override
	public List<StatAppUseTimeLog> list() {
		//List<StatAppUseTimeLog> filter = new ArrayList<StatAppUseTimeLog>();
		List<StatAppUseTimeLog> list = appUseTimeLogDao.list();
		/*for (StatAppUseTimeLog statAppUseTimeLog : list) {
			Long userId = statAppUseTimeLog.getUserId();
			if(userId != null){
				UserInfo userInfo = userInfoDao.select(userId);
				if(userInfo != null){
					statAppUseTimeLog.setUserInfo(userInfo);
					filter.add(statAppUseTimeLog);
				}
			}
		}
		return filter;*/
		return list;
	}

	@Override
	public StatAppUseTimeLog select(String appName, String userInfo) {
		return appUseTimeLogDao.select(appName, userInfo);
	}
}

package com.focustech.cief.filemanage.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.app.dao.AppInfoDetailDao;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoDetailService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class AppBackstageDetailServiceImpl  implements AppInfoDetailService<AppInfoDetail>{
	@Autowired
	private AppInfoDetailDao<AppInfoDetail> appBackstageDetailDao;

	@Override
	public AppInfoDetail select(String appId) {

		return appBackstageDetailDao.selectByAppId(appId);
	}

	@Override
	public void updateOrInsert(AppInfoDetail appDetail) {
		appBackstageDetailDao.insertOrUpdate(appDetail);
	}

	@Override
	public List<AppInfoDetail> list(Integer systemType) {
		return appBackstageDetailDao.list(systemType);
	}

	@Override
	public AppInfoDetail select(Long sn) {
		return appBackstageDetailDao.select(sn);
	}

	@Override
	public List<AppInfoDetail> selectByAppSn(Long appSn) {
		return appBackstageDetailDao.selectByAppSn(appSn);
	}

	@Override
	public List<AppInfoDetail> listAll() {
		return appBackstageDetailDao.listAll();
	}

	@Override
	public Map<Long, Long> getAppFile(int num, int sizeLimit) {
		return appBackstageDetailDao.getAppFile(num, sizeLimit);
	}

}

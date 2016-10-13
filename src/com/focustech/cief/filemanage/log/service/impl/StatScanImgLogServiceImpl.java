package com.focustech.cief.filemanage.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.log.dao.StatScanImgLogDao;
import com.focustech.cief.filemanage.log.model.StatScanImgLog;
import com.focustech.cief.filemanage.log.service.StatScanImgLogService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class StatScanImgLogServiceImpl implements StatScanImgLogService<StatScanImgLog> {
	@Autowired
	private StatScanImgLogDao<StatScanImgLog> scanImgLogDao;

	@Override
	public void insertOrUpdate(StatScanImgLog t) {
		scanImgLogDao.insertOrUpdate(t);
	}

	@Override
	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam) {
		return scanImgLogDao.getChartData(startDay, endDay, extendParam);
	}



}

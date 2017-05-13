package com.focustech.cief.filemanage.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.log.dao.FeedBackDao;
import com.focustech.cief.filemanage.log.model.FeedBack;
import com.focustech.cief.filemanage.log.service.FeedBackService;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService<FeedBack> {
	@Autowired
	private FeedBackDao<FeedBack> feedBackDao;
	@Override
	public void insertOrUpdate(FeedBack feedBack) {
		feedBackDao.insertOrUpdate(feedBack);
	}

	@Override
	public List<FeedBack> list() {
		return feedBackDao.list();
	}

}

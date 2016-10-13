package com.focustech.cief.filemanage.hx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.hx.dao.XmlDetailDao;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
import com.focustech.cief.filemanage.hx.service.XmlDetailService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class XmlDetailServiceImpl implements XmlDetailService<XmlDetail> {
	@Autowired
	private XmlDetailDao<XmlDetail> xmlDetailDao;


	@Override
	public void delete(long xmlInfoSn) {
		xmlDetailDao.delete(xmlInfoSn);
	}

	@Override
	public List<XmlDetail> list(long xmInfoSn) {
		return xmlDetailDao.list(xmInfoSn);
	}


}

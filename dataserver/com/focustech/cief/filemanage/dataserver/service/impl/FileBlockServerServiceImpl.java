package com.focustech.cief.filemanage.dataserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.dao.IFileBlockServerDao;
import com.focustech.cief.filemanage.dataserver.model.FileBlockServer;
import com.focustech.cief.filemanage.dataserver.service.IFileBlockServerService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class FileBlockServerServiceImpl implements IFileBlockServerService<FileBlockServer> {
	@Autowired
	IFileBlockServerDao<FileBlockServer> blockServerDao;
	@Override
	public FileBlockServer save(FileBlockServer t) {
		blockServerDao.insertOrUpdate(t);
		return t;
	}
	@Override
	public FileBlockServer select(long blockSn, long serverSn) {

		return null;
	}

}

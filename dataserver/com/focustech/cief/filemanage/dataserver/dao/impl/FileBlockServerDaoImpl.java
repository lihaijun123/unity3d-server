package com.focustech.cief.filemanage.dataserver.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.IFileBlockServerDao;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.dataserver.model.FileBlockServer;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class FileBlockServerDaoImpl extends OssHibernateDaoSupport<FileBlockServer> implements IFileBlockServerDao<FileBlockServer> {

	@Override
	public FileBlockServer select(long blockSn, long serverSn) {
		Criteria criteria = getCurrentSession().createCriteria(FileBlockServer.class);
		criteria.add(Restrictions.eq("fileBlock.sn", blockSn));
		criteria.add(Restrictions.eq("serverSn", serverSn));
		return (FileBlockServer) criteria.uniqueResult();
	}

}

package com.focustech.cief.filemanage.dataserver.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.IFileBlockDao;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.dataserver.model.FileBlock;

@Repository
public class FileBlockDaoImpl extends OssHibernateDaoSupport<FileBlock> implements IFileBlockDao<FileBlock> {

	@Override
	public FileBlock select(String blockName) {
		Criteria criteria = getCurrentSession().createCriteria(FileBlock.class);
		criteria.add(Restrictions.eq("name", blockName));
		return (FileBlock) criteria.uniqueResult();
	}

}

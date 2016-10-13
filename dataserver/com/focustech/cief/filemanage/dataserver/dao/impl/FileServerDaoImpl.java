package com.focustech.cief.filemanage.dataserver.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.IFileServerDao;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
/**
 * *
 * @author lihaijun
 *
 */
@Repository
public class FileServerDaoImpl extends OssHibernateDaoSupport<FileServer> implements IFileServerDao<FileServer> {

	@Override
	public FileServer getUsableByFileSize(double bytesTotal) {
		String hql = " FROM " + FileServer.class.getName() + " _server_ "
		           + " WHERE _server_.flagUsable = 1 "
		           + " AND _server_.maxFileAmount > _server_.curFileAmount "
		           + " AND (_server_.hardDiscsCapacity - " + bytesTotal + ") > 0 "
		           + " ORDER BY _server_.hardDiscsCapacity - " + bytesTotal + " desc";
		List<FileServer> fileServerList = getCurrentSession().createQuery(hql).list();
		if(null == fileServerList && fileServerList.size() == 0){
			return null;
		}
		return fileServerList.get(0);
	}

	@Override
	public FileServer select(String serverDomain, int serverPort) {
		String hql = " FROM " + FileServer.class.getName() + " WHERE serverIp = ? and serverPort = ?";
		return (FileServer)getCurrentSession().createQuery(hql).setString(0, serverDomain).setInteger(1, serverPort).uniqueResult();
	}

}

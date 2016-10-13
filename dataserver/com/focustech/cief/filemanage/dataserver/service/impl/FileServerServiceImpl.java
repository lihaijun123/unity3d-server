package com.focustech.cief.filemanage.dataserver.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToWhere;
import com.focustech.cief.filemanage.dataserver.dao.IBaseFileDao;
import com.focustech.cief.filemanage.dataserver.dao.IFileServerDao;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.cief.filemanage.dataserver.service.IFileServerService;
/**
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class FileServerServiceImpl implements IFileServerService<FileServer> {
	private Logger log = LoggerFactory.getLogger(FileServerServiceImpl.class.getName());
	@Autowired
	private IFileServerDao<FileServer> fileServerDao;
	@Autowired
	private IBaseFileDao<AbstractFile> baseFileDao;
	@Override
	public void save(FileServer server) {
		fileServerDao.insertOrUpdate(server);
	}

	@Override
	public FileServer getById(long id) {
		return fileServerDao.select(id);
	}

	@Override
	public FileServer getUsableByFileSize(double bytesTotal) {
		return fileServerDao.getUsableByFileSize(bytesTotal);
	}

	@Override
	public FileServer select(String serverDomain, int serverPort) {
		return fileServerDao.select(serverDomain, serverPort);
	}
	@Override
	public void reDeploy() {
		List<AbstractFile> selectAll = baseFileDao.list();
		ReadFileFromCompanyFS fileToRead = ReadFileFromCompanyFS.newInstance();
		InputStream inputStream = null;
		WriteFileToWhere fileToWhere = null;
		for (AbstractFile cfsFile : selectAll) {
			String key = cfsFile.getLocalName();
			boolean existFlag = false;
			try {
				fileToRead.read(key);
			} catch (Exception e) {
				log.info("key :" + key + ", 才FFS中已经存在");
				existFlag = true;
			}
			if(existFlag){
				continue;
			}
			String filePath = FilePathUtil.getFilePhysicalPath(cfsFile);
			Long sn = cfsFile.getSn();
			try {
				log.info("开始写入file，sn：" + sn);
				inputStream = new FileInputStream(new File(filePath));
				fileToWhere = WriteFileToCompanyFS.newInstance();
				fileToWhere.write(key, inputStream);
				log.info("成功写入ffs，key：" + key);
			} catch (FileNotFoundException e) {
				log.error("不存在路径：" + filePath);
				e.printStackTrace();
			} finally {
				if(inputStream != null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fileToWhere != null){
					fileToWhere = null;
				}
			}
			log.info(sn + "-转换成功");
		}
		log.info("所有文件转换成功");
	}

}

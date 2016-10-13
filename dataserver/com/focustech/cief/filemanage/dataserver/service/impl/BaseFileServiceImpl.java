package com.focustech.cief.filemanage.dataserver.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FileUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileType;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToCFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToWhere;
import com.focustech.cief.filemanage.dataserver.dao.IBaseFileDao;
import com.focustech.cief.filemanage.dataserver.dao.IFileServerDao;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.DateUtils;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.TCUtil;
import com.focustech.core.Assert;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class BaseFileServiceImpl implements IBaseFileService<AbstractFile>{
	/**逻辑删除标志*/
	private static final int IS_DELETE = 1;
	@Autowired
	private IBaseFileDao<AbstractFile> baseFileDao;
	@Autowired
	private IFileServerDao<FileServer> fileServerDao;
	@Autowired
	private DataServerNode serverNode;
	@Value(value = "${dataserver.file.visit.url}")
	private String visitServerUrl;
	@Override
	public long save(AbstractFile file) {
		Assert.notNull(file);
		baseFileDao.insertOrUpdate(file);
		//更新文件服务器表中文件数量字段值
		//DataServerNode fileServer = file.getServerNode();
		//fileServer.setCurFileAmount(fileServer.getCurFileAmount() + 1);
		//fileServerDao.update(fileServer);
		//信息存入缓存
		//cacheHanderProxy.addFileInfoCache(file.getSn(), CFSFileMapTransform.convertToMap(file));
		return file.getSn();
	}

	@Override
	public long saveParentFile(AbstractFile file) {
		Assert.notNull(file);
		return save(file);
	}
	@Override
	public long saveChildFile(AbstractFile abstractFile) {
		Assert.notNull(abstractFile);
		String fsSystem = MessageUtils.getInfoValue("FS_SYSTEM");
		if("FFS".equals(fsSystem)){
			WriteFileToWhere fileToWhere = WriteFileToCompanyFS.newInstance();
			String filePhysicalPath = FilePathUtil.getFilePhysicalPath(abstractFile);
			File localFile = new File(filePhysicalPath);
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(localFile);
				fileToWhere.write(abstractFile.getLocalName(), fileInputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(fileInputStream != null){
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			FileUtil.delFile(filePhysicalPath);
		}
		return save(abstractFile);
	}

	@Override
	public void deleteByPhysics(String subFoldName, String fileName) {
		Assert.notEmpty(subFoldName);
		Assert.notEmpty(fileName);
		AbstractFile file = baseFileDao.getFile(subFoldName, fileName);
		if(null != file){
			long serverSn = file.getServerSn();
			FileServer fileServer = fileServerDao.select(serverSn);
			//物理路径
			String fileRealPath = fileServer.getFileRootPath() + fileServer.getRootFoldName() + "\\" + subFoldName + "\\" + fileName;
			//删除文件
			FileUtil.delFile(fileRealPath);
			//删除数据库中文件的记录
			baseFileDao.delete(file);
			//更新文件服务器表中文件数量字段值
			int curFileAmount = fileServer.getCurFileAmount();
			fileServer.setCurFileAmount(curFileAmount > 0 ? curFileAmount - 1 : 0);
			fileServerDao.update(fileServer);
		}
	}

	@Override
	public void deleteByLogic(String subFoldName, String fileName) {
		Assert.notEmpty(subFoldName);
		Assert.notEmpty(fileName);
		AbstractFile file = baseFileDao.getFile(subFoldName, fileName);
		if(null != file){
			file.setIsDelete(IS_DELETE);
			baseFileDao.update(file);
		}
	}

	@Override
	public void update(String subFoldName, String fileName, AbstractFile file) {
		//删除旧文件
		deleteByPhysics(subFoldName, fileName);
		//保存新文件
		save(file);
	}

	@Override
	public AbstractFile findBySn(long sn) {
		Assert.notEmpty(sn);
		return (AbstractFile)baseFileDao.select(sn);
	}

	@Override
	public Map<String, String> getFileLocalPath(String fileSn) {
		Assert.notEmpty(fileSn);
		Map<String, String> returnMap = new HashMap<String, String>();
		String downloadFileName = "";
		String downloadFilePath = "";
		//获取serverId
		AbstractFile file = baseFileDao.select(Long.parseLong(fileSn));
		file.setServerNode(serverNode);
		if(null != file){
			/*boolean isComponentModel = file.getVisitAddr().indexOf(FileTypeOrExtProcRelation.SUB_FOLDER_COMPONENTS_NAME) != -1;
			if(FileType.MODEL.equals(file.getType()) && !isComponentModel){
				//如果是3d模型的话，那么获取的是压缩文件里面的小文件，但是下载应该下载整个模型压缩文件
				Long parentFileSn = file.getParentFileSn();
				//压缩文件
				file = baseFileDao.select(parentFileSn);
			}*/
			//物理路径
			Map<String, String> filePathMap = FilePathUtil.extractParameter(file);
			downloadFileName = file.getName();
			downloadFilePath = filePathMap.get(FileParam.FILE_PHYSICAL_PATH);
			returnMap.put(FileParam.DOWNLOAD_FILE_NAME, downloadFileName);
			returnMap.put(FileParam.DOWNLOAD_FILE_PATH, downloadFilePath);
			returnMap.put(FileParam.DOWNLOAD_FILE_VISIT_ADDR, file.getVisitAddr());
		}
		return returnMap;
	}

	@Override
	public AbstractFile getFile(String subFoldName, String fileName) {
		return baseFileDao.getFile(subFoldName, fileName);
	}

	@Deprecated
	@Override
	public AbstractFile copy(String sn, String type) {
		/*//源文件
		AbstractFile sourceFile = baseFileDao.select(Long.parseLong(sn));
		FileServer fileServer = sourceFile.getFileServer();
		String sourceFilePath = FilePathUtil.getFilePhysicalPath(sourceFile);
		File sourceFile = new File(sourceFilePath);
		FileOutputStream fileInputStream = new FileOutputStream(originalPhFile);
		//拷贝
		//保存目标文件到数据库
		baseFileDao.clearSession();
		AbstractFile targetFile = sourceFile;
		targetFile.setSn(null);
		targetFile.setVisitAddr(targetVisitAddr);
		targetFile.setVisitFrequency(0L);
		targetFile.setCreateTime(DateUtil.getCurrentDateTime());
		targetFile.setRemark("复制");
		baseFileDao.insertOrUpdate(targetFile);
		return targetFile;*/
		return null;
	}

	@Override
	public AbstractFile updateTargetFileVisitAddrToOrinFile(String originalSn, String targetSn) {
		AbstractFile originalFile = baseFileDao.select(Long.parseLong(originalSn));
		try {
			AbstractFile targetFile = baseFileDao.select(Long.parseLong(targetSn));
			//更新源文件
			if(FileType.PIC.equals(targetFile.getType()) && FileType.PIC.equals(originalFile.getType())){
				((PicInfo)originalFile).setWidth(((PicInfo)targetFile).getWidth());
				((PicInfo)originalFile).setHeight(((PicInfo)targetFile).getHeight());
			}
			originalFile.setSize(targetFile.getSize());
			originalFile.setRemark("内容被更新(图片编辑器的特殊处理)" + DateUtils.getCurDate());
			//写入文件系统
    		String fsSystem = MessageUtils.getInfoValue("FS_SYSTEM");
    		WriteFileToWhere fileToWhere = null;
    		if("FFS".equals(fsSystem)){
    			fileToWhere = WriteFileToCompanyFS.newInstance();

    		} else {
    			fileToWhere = new WriteFileToCFS();
    		}
    		fileToWhere.exchange(originalFile, targetFile);
			save(originalFile);
			//删除目标文件元数据
			baseFileDao.delete(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return originalFile;
	}

	@Override
	public long getFileSnByVisitAddr(String visitAddr) {
		return baseFileDao.getFileSnByVisitAddr(visitAddr);
	}

	@Override
	public Map<String, String> select(Long id) {
		return baseFileDao.selectById(id);
	}

	@Override
	public float countSize(String ids) {
		return baseFileDao.countSize(ids);
	}

	@Override
	public List<Map<String, String>> selectAll() {
		return baseFileDao.selectAll();
	}

	@Override
	public AbstractFile fetchFile(String serverId, String blockId, String cfsFileName) {
		AbstractFile fetchFile = baseFileDao.fetchFile(serverId, blockId, cfsFileName);
		if(fetchFile != null){
			//更新访问次数
			Long visitFrequency = TCUtil.lv(fetchFile.getVisitFrequency());
			fetchFile.setVisitFrequency(++ visitFrequency);
		}
		return fetchFile;
	}

	@Override
	public List<Map<String, String>> getInitList(Long serverSn, int size, long fileLimitSize) {
		return baseFileDao.getInitList(serverSn, size, fileLimitSize) ;
	}

	@Override
	public byte[] readFile(Long xmlFileSn) {
		Map<String, String> downloadInfoMap = getFileLocalPath(TCUtil.sv(xmlFileSn));
		String filePath = downloadInfoMap.get(FileParam.DOWNLOAD_FILE_PATH);
		ReadFileFromCFS readFileFromWhere = new ReadFileFromCFS();
		InputStream inputStream = readFileFromWhere.read(filePath);
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] byteAry = null;
		try {
			int c = bis.read();
			while(c != -1){
				out.write(c);
				c = bis.read();
			}
			byteAry = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteAry;
	}

	@Override
	public List<AbstractFile> list(String fileName) {
		return baseFileDao.list(fileName);
	}
	/**
	 *
	 * *
	 * @return
	 */
	public AbstractFile getFirstByName(String fileName){
		AbstractFile abstractFile = null;
		List<AbstractFile> fileList = baseFileDao.list(fileName);
		if(ListUtils.isNotEmpty(fileList)){
			abstractFile = fileList.get(0);
		}
		return abstractFile;
	}

	@Override
	public String getVisitServerUrl() {
		return visitServerUrl;
	}
}

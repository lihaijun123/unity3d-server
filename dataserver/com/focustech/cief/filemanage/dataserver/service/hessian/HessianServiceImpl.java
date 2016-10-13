package com.focustech.cief.filemanage.dataserver.service.hessian;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromWhere;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.StringUtils;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
import com.focustech.focus3d.bundle.fileserver.data.api.FileConst;
import com.focustech.focus3d.bundle.fileserver.data.rpc.DataServerHessianService;
/**
 * 文件读取服务
 * *
 * @author lihaijun
 *
 */
public class HessianServiceImpl implements DataServerHessianService {
	private static final Logger log = LoggerFactory.getLogger(HessianServiceImpl.class);
	@Autowired
	protected IBaseFileService<AbstractFile> baseFileService;
	public static final int DATA_NUM_SIZE = 100;
	public static final int DATA_LIMIT_SIZE = 50 * 1024 * 1024;//字节
	@Autowired
	private DataServerNode serverNode;

	private ExecutorService pool = Executors.newFixedThreadPool(4);

	@PostConstruct
	public void init(){
		Long serverSn = serverNode.getSn();
		log.info("filesn:" + serverSn);

	}

	@Override
	public byte[] fetchFile(String cfsFileName) {
		byte[] rv = new byte[0];
		if(!StringUtils.isEmpty(cfsFileName)){
    		cfsFileName = HttpUtil.decodeUrl(cfsFileName);
		 	//从磁盘读取
			//解析文件名称，文件名称带有元数据信息
			String rawData = "";
			if(cfsFileName.contains("-")){
				rawData = cfsFileName.substring(0, cfsFileName.indexOf("-"));
			} else {
				rawData = cfsFileName.substring(0, cfsFileName.indexOf("."));
			}
			AbstractFile cfsFile = null;
			if(rawData.length() > 10){
				String serverId = rawData.substring(1, 2);
				String blockId = rawData.substring(2, rawData.length() - 10);
				String decodeText = EncryptUtil.decodeText(blockId);
				if(StringUtils.isNotEmpty(decodeText)){
					cfsFile = baseFileService.fetchFile(serverId, decodeText, cfsFileName);
				}
			}
			if(cfsFile != null){
				try {
					rv = readData(cfsFile);
					storeToMemecache(cfsFileName, rv);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if(!StringUtils.isEmpty(cfsFileName)){
				 	//从磁盘读取
					//解析文件名称，文件名称带有元数据信息
					List<AbstractFile> fileList = baseFileService.list(cfsFileName);
					if(ListUtils.isNotEmpty(fileList)){
						try {
							AbstractFile abstractFile = fileList.get(0);
							abstractFile.setServerNode(serverNode);
							rv = readData(abstractFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return rv;
			}
		}
		return rv;
	}
	@Override
	public byte[] fetchFileBySn(Long sn) {
		byte[] rv = new byte[0];
		AbstractFile cfsFile = baseFileService.findBySn(sn);
		if(cfsFile != null){
			try {
				rv = readData(cfsFile);
				storeToMemecache(cfsFile.getVisitAddr(), rv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rv;
	}
	@Override
	public Map<String, String> select(Long sn) {
		Map<String, String> data = baseFileService.select(sn);
		String metaKey = FileConst.MEMCACHE_KEY_FILE_META + sn;
		pool.execute(new StoreToMemcacheTask(metaKey, data));
		return data;
	}

	/**
	 *
	 * *
	 * @param cfsFileName
	 * @param rv
	 */
	private void storeToMemecache(String cfsFileName, byte[] rv) {
		if(!isEmpty(rv) && rv.length <= DATA_LIMIT_SIZE){
			pool.execute(new StoreToMemcacheTask(cfsFileName, rv));
		}

	}
	/**
	 *
	 * *
	 * @param rv
	 * @return
	 */
	private boolean isEmpty(byte[] rv) {
		return rv == null || rv.length == 0;
	}
	/**
	 * 读取数据
	 * *
	 * @param cfsFile
	 * @return
	 */
	public byte[] readData(AbstractFile cfsFile){
		byte[] rv = new byte[0];
		String key = FilePathUtil.getFilePhysicalPath(cfsFile);
		FileInputStream fis = null;
		InputStream bis = null;
		try {
			String fsSystem = MessageUtils.getInfoValue("FS_SYSTEM");
			ReadFileFromWhere fileFromWhere = null;
    		if("CFS".equals(fsSystem)){
    			fileFromWhere = new ReadFileFromCFS();
    		} else {
    			key = cfsFile.getLocalName();
    			fileFromWhere = ReadFileFromCompanyFS.newInstance();
    		}
			bis = fileFromWhere.read(key);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] bzp = new byte[1024];
			int length = 0;
			while((length = bis.read(bzp)) != -1){
				outputStream.write(bzp, 0, length);
			}
			rv = outputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rv;
	}
	/**
	 *
	 * *
	 * @author lihaijun
	 *
	 */
	class StoreToMemcacheTask implements Runnable {
		private String key;
		private Object data;

		public StoreToMemcacheTask(String key, Object data){
			this.key = key;
			this.data = data;
		}
		@Override
		public void run() {
			//存储到缓存服务器
		}
	}


}

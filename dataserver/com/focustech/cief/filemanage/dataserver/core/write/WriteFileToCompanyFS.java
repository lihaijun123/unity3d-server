package com.focustech.cief.filemanage.dataserver.core.write;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cassandra.extend.client.FFSClient;
import org.apache.cassandra.extend.client.FFSClient.BlockSize;
import org.apache.cassandra.extend.client.FFSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromWhere;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.hessian.HessianServiceImpl;
import com.focustech.common.utils.MessageUtils;

/**
 * 写入公司文件系统
 * *
 * @author lihaijun
 *
 */
public class WriteFileToCompanyFS implements WriteFileToWhere{
	private final Logger log = LoggerFactory.getLogger(WriteFileToCFS.class.getName());

	private FFSClient client;

	private String tableName;

	private static WriteFileToCompanyFS companyFS;

	private static ExecutorService pool = Executors.newFixedThreadPool(4);

	private WriteFileToCompanyFS(){

	}

	public static WriteFileToCompanyFS newInstance(){
		if(companyFS == null){
			synchronized (ReadFileFromCompanyFS.class) {
				if(companyFS == null){
					String hosts = MessageUtils.getInfoValue("HOSTS");
					String dsName = MessageUtils.getInfoValue("DS_NAME");
					String tableName = MessageUtils.getInfoValue("TABLE_NAME");
					FFSClient client = FFSClient.getInstance(hosts, dsName, BlockSize.BlockSize_5M);
					companyFS = new WriteFileToCompanyFS();
					companyFS.setClient(client);
					companyFS.setTableName(tableName);
				}
			}
		}
		return companyFS;
	}

	@Override
	public String write(String key, InputStream inputStream) {
		try {
			client.putBlob(this.tableName, key, inputStream, null);
			pool.execute(new StoreToMemcacheTask(key));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FFSException e) {
			e.printStackTrace();
		}
		return key;
	}
	@Override
	public void exchange(AbstractFile orginFile, AbstractFile targetFile) {
		ReadFileFromWhere fileFromWhere = ReadFileFromCompanyFS.newInstance();
		InputStream targetFileData = fileFromWhere.read(targetFile.getLocalName());
		write(orginFile.getLocalName(), targetFileData);
	}

	public FFSClient getClient() {
		return client;
	}

	public void setClient(FFSClient client) {
		this.client = client;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 *
	 * *
	 * @author lihaijun
	 *
	 */
	class StoreToMemcacheTask implements Runnable {
		private String key;

		public StoreToMemcacheTask(String key){
			this.key = key;
		}

		@Override
		public void run() {
			try {
				InputStream inputStream = ReadFileFromCompanyFS.newInstance().read(key);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					int len;
					byte[] b = new byte[1024];
					while((len = inputStream.read(b)) != -1){
						out.write(b, 0, len);
					}
					byte[] byteArray = out.toByteArray();
					int length = byteArray.length;
					if(length > 0 && length <= HessianServiceImpl.DATA_LIMIT_SIZE){
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(out != null){
						out.close();
					}
					if(inputStream != null){
						inputStream.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		try {
			String hosts = "192.168.2.11";
			String dsName = "CIEF";
			FFSClient client = FFSClient.getInstance(hosts, dsName, BlockSize.BlockSize_5M);
			client.putBlob("COM_FILE_DATA", "123456789", "33333".getBytes(), null);
			System.out.println(333333);
		} catch (FFSException e) {
			e.printStackTrace();
		}
	}
}

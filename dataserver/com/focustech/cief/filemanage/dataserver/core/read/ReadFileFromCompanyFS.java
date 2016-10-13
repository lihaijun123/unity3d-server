package com.focustech.cief.filemanage.dataserver.core.read;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.cassandra.extend.client.BlobBean;
import org.apache.cassandra.extend.client.FFSClient;
import org.apache.cassandra.extend.client.FFSClient.BlockSize;
import org.apache.cassandra.extend.client.FFSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.focustech.common.utils.MessageUtils;
/**
 * 从公司文件系统中读取文件数据
 * *
 * @author lihaijun
 *
 */
public class ReadFileFromCompanyFS implements ReadFileFromWhere {
	private final Logger logger = LoggerFactory.getLogger(ReadFileFromCompanyFS.class.getName());
	private FFSClient client;
	private String tableName;
	private static ReadFileFromCompanyFS companyFS;

	private ReadFileFromCompanyFS(){

	}

	public static ReadFileFromCompanyFS newInstance(){
		if(companyFS == null){
			synchronized (ReadFileFromCompanyFS.class) {
				if(companyFS == null){
					String hosts = MessageUtils.getInfoValue("HOSTS");
					String dsName = MessageUtils.getInfoValue("DS_NAME");
					String tableName = MessageUtils.getInfoValue("TABLE_NAME");
					FFSClient client = FFSClient.getInstance(hosts, dsName, BlockSize.BlockSize_5M);
					companyFS = new ReadFileFromCompanyFS();
					companyFS.setClient(client);
					companyFS.setTableName(tableName);
				}
			}
		}
		return companyFS;
	}

	@Override
	public InputStream read(String key) {
		logger.info("ReadFileFromCompanyFS：" + key);
		ByteArrayInputStream arrayInputStream = null;
		try {
			BlobBean bean = this.client.getBlobBean(this.tableName, key);
			arrayInputStream = new ByteArrayInputStream(bean.getBlob());
		} catch (FFSException e) {
			e.printStackTrace();
		}
		return arrayInputStream;
	}

	private void setClient(FFSClient client) {
		this.client = client;
	}

	private void setTableName(String tableName) {
		this.tableName = tableName;
	}
}

package com.focustech.cief.filemanage.dataserver.core.processor;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.SHAUtil;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 处理器
 * *
 * @author lihaijun
 *
 */
public class AbstractProcessor {
	/**参数*/
	protected Map<String, Object> paramMap;

	protected DataServerNode fileServer;

	protected String subBlockName;
	/**
     * 获取文件名前缀
     * *
     * @return
     */
    protected String getFileNamePrefix(DataServerNode fileServer, String subBlockName){
    	String fileServerId = TCUtil.sv(fileServer.getSn());
    	String result = "";
    	try {
			byte[] sha1Digest = SHAUtil.getSHA1Digest(UUID.randomUUID().toString() + System.currentTimeMillis());
			result = SHAUtil.byte2Hex(sha1Digest);
		} catch (IOException e) {
			result = UUID.randomUUID().toString();
		}
    	return FileParam.FILE_NAME_PREFIX + fileServerId + EncryptUtil.encodeText(subBlockName) + result.substring(14, 24).toLowerCase();
    }

	/**
     * 获取文件名前缀
     * *
     * @return
     */
    protected String getFileNameWithLocalInfo(DataServerNode fileServer, String subBlockName, String localName){
    	return getFileNamePrefix(fileServer, subBlockName) + "-" + localName;
    }
}

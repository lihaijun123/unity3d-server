package com.focustech.cief.filemanage.dataserver.dao;

import java.util.List;
import java.util.Map;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.BaseHibernateDao;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
/**
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface IBaseFileDao<T extends AbstractFile> extends BaseHibernateDao<T> {
	/**
	 * 获取serverID
	 * *
	 * @param subFoldName
	 * @param fileName
	 * @return
	 */
	public T getFile(String subFoldName, String fileName);
	/**
	 * 通过访问路径获取文件SN
	 * *
	 * @param visitAddr
	 * @return
	 */
	public long getFileSnByVisitAddr(String visitAddr);
	/**
	 * 清理缓存session
	 * *
	 */
	public void clearSession();
	/**
	 *
	 * *
	 * @param id
	 * @return
	 */
	public Map<String, String> selectById(long id);
	/**
	 * 获取所有数据
	 * *
	 * @return
	 */
	public List<Map<String, String>> selectAll();
	/**
	 *
	 * @param ids
	 * @return
	 */
	public float countSize(String ids);
	/**
	 * 获取文件
	 * *
	 * @param cfsFileName
	 * @param cfsFileName2
	 * @param blockId
	 * @return
	 */
	public T fetchFile(String serverId, String blockId, String cfsFileName);

	/**
	 *
	 * *
	 * @return
	 */
	public List<T> list();


	public List<Map<String, String>> getInitList(Long serverSn, int size, long fileLimitSize);

	public List<T> list(String fileName);
}

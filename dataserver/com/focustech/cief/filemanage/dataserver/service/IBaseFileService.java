package com.focustech.cief.filemanage.dataserver.service;

import java.util.List;
import java.util.Map;


/**
 * *
 * @author lihaijun
 *
 */
public interface IBaseFileService<T> {
	/**
	 * 存储或者更新文件
	 * *
	 * @param file
	 * @return
	 */
	public long save(T file);
	/**
	 * 存储或者更新文件(父文件)
	 * *
	 * @param file
	 * @return
	 */
	public long saveParentFile(T file);
	/**
	 * 存储或者更新文件(子文件)
	 * *
	 * @param file
	 * @return
	 */
	public long saveChildFile(T file);
	/**
	 * 物理删除文件
	 * *
	 * @param subFoldName
	 * @param fileName
	 */
	public void deleteByPhysics(String subFoldName, String fileName);

	/**
	 * 逻辑删除文件
	 * *
	 * @param subFoldName
	 * @param fileName
	 */
	public void deleteByLogic(String subFoldName, String fileName);

	/**
	 * 文件更新
	 * *
	 * @param subFoldName
	 * @param fileName
	 * @param file
	 */
	public void update(String subFoldName, String fileName, T file);

	/**
	 * 通过ID获取信息
	 * *
	 * @param id
	 * @return
	 */
	public T findBySn(long sn);

	/**
	 * 获取文件在服务器上的物理路径
	 * *
	 * @param subFoldName 子目录名称
	 * @param fileName 文件名称
	 * @return
	 */
	public Map<String, String> getFileLocalPath(String fileSn);
	/**
	 *
	 * *
	 * @param subFoldName 子目录名称
	 * @param fileName 文件名称
	 * @return
	 */
	public T getFile(String subFoldName, String fileName);
	/**
	 * 拷贝文件
	 * *
	 * @param sn 源文件SN
	 * @param type 源文件类型
	 */
	public T copy(String sn, String type);
	/**
	 *
	 * 把目标文件的访问路径更新到原始文件的访问路径上
	 * *
	 * @param originalSn 原始文件SN
	 * @param targetSn 目标文件SN
	 * @return
	 */
	public T updateTargetFileVisitAddrToOrinFile(String originalSn, String targetSn);
	/**
	 * 通过访问路径获取文件SN
	 * *
	 * @param visitAddr
	 * @return
	 */
	public long getFileSnByVisitAddr(String visitAddr);
	/**
	 * 根据文件编号获取文件信息
	 * *
	 * @param id
	 * @return
	 */
	public Map<String, String> select(Long id);
	/**
	 * 计算文件容量
	 * *
	 * @param ids
	 * @return
	 */
	public float countSize(String ids);
	/**
	 * 获取所有文件数据
	 * *
	 * @return
	 */
	public List<Map<String, String>> selectAll();
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
	 * 获取初始化列表
	 * *
	 * @param long1
	 * @return
	 */
	public List<Map<String, String>> getInitList(Long serverSn, int sizeLimit, long fileLimitSize);


	public byte[] readFile(Long xmlFileSn);

	public List<T> list(String fileName);

	public T getFirstByName(String fileName);

	public String getVisitServerUrl();
}

package com.focustech.cief.filemanage.dataserver.service;
/**
 * *
 * @author lihaijun
 *
 */
public interface IFileServerService<T> {
	/**
	 * 添加新的文件服务器
	 * *
	 * @param server
	 */
	public void save(T server);
	/**
	 * 由id获取文件服务器
	 * *
	 * @param id
	 * @return
	 */
	public T getById(long id);

	/**
	 * 根据url获取文件服务器
	 * *
	 * @param url
	 * @return
	 */
	public T select(String serverDomain, int serverPort);

	/**
	 * 由上传文件的大小来获取可用的服务器
	 * *
	 * @param bytesTotal
	 * @return
	 */
	public T getUsableByFileSize(double bytesTotal);

	public void reDeploy();
}

package com.focustech.cief.filemanage.dataserver.service;
/**
 *
 * *
 * @author lihaijun
 *
 */
public interface IFileBlockServerService<T> {
	/**
	 * 保存
	 * *
	 * @param t
	 * @return
	 */
	public T save(T t);
	/**
	 *
	 * *
	 * @param blockSn
	 * @param serverSn
	 * @return
	 */
	public T select(long blockSn, long serverSn);
}

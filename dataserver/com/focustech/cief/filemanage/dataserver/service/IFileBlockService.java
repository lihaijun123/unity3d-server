package com.focustech.cief.filemanage.dataserver.service;

import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

public interface IFileBlockService<T> {
	/**
	 * 保存
	 * *
	 * @param block
	 * @return
	 */
	public long save(T block);
	/**
	 * 根据名称查询
	 * *
	 * @param blockName
	 * @return
	 */
	public T select(String blockName);
	/**
	 *
	 * *
	 * @param sn
	 * @return
	 */
	public T select(Long sn);
	/**
	 *
	 * *
	 * @param blockName
	 * @return
	 */
	public T save(String blockName);
	/**
	 *
	 * *
	 * @param urlParam
	 * @param fileServer
	 * @return
	 */
	public T createBlock(String urlParam, DataServerNode dataServerNode);
}

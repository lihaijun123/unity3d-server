package com.focustech.cief.filemanage.hx.service;

import java.util.List;

/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface XmlInfoService<T>{
	public static final int ACTIVE_XML = 1;

	public List<T> list();

	public T select(long sn);

	public void save(T t);

	public List<T> list(int type);
}

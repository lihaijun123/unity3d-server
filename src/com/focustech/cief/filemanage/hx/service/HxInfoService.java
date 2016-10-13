package com.focustech.cief.filemanage.hx.service;

import java.util.List;

/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface HxInfoService<T> {

	public void save(T t);

	public T select(long sn);

	public List<T> list(long xmlInfoSn);

	public void delete(long xmlInfoSn);
}

package com.focustech.cief.filemanage.log.service;

import java.util.List;
/**
 *
 * *
 * @author lihaijun
 *
 * @param <T>
 */
public interface FeedBackService<T> {

	void insertOrUpdate(T t);



	public List<T> list();
}

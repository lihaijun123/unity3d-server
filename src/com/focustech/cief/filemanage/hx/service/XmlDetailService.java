package com.focustech.cief.filemanage.hx.service;

import java.util.List;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
public interface XmlDetailService<T> {

	public void delete(long xmlInfoSn);

	public List<T> list(long xmInfoSn);
}

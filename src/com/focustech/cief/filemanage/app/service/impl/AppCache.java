package com.focustech.cief.filemanage.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoDetailService;
/**
 *
 * *
 * @author lihaijun
 *
 */

public class AppCache {

	private Logger log = LoggerFactory.getLogger(AppCache.class.getName());
	@Autowired
	private AppInfoDetailService<AppInfoDetail> appBackstageDetailService;

	private static Map<String, AppInfoDetail> cache = new ConcurrentHashMap<String, AppInfoDetail>();

	public static final String KEY_APP = "app_";

	public static final String KEY_APPBK = "appbk_";
	/**
	 * 初始化
	 * *
	 * @param req
	 * @param model
	 */
	public void init() {
		List<AppInfoDetail> listAll = appBackstageDetailService.listAll();
		for (AppInfoDetail app : listAll) {
			if(app.getAppFileSn() != null){
				cache.put(KEY_APPBK + app.getAppId(), app);
			}
		}
	}
	/**
	 *
	 * *
	 * @param <T>
	 * @param key
	 * @param t
	 * @return
	 */
	public <T> T get(String appId, Class t){
		return (T) cache.get(KEY_APPBK + appId);
	}

	public void put(AppInfoDetail app){
		cache.put(KEY_APPBK + app.getAppId(), app);
	}
}

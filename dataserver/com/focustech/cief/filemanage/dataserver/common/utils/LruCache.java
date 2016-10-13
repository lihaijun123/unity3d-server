package com.focustech.cief.filemanage.dataserver.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LruCache {
	private Map<Object, byte[]> store;
	private int limitSize;
	/**
	 *
	 * *
	 * @param limitSize 缓存最大上限
	 * @param maxOutSize 淘汰上限，当容量大于淘汰上限时 执行淘汰策略
	 */
	public LruCache(final int limitSize, final int maxOutSize){
		if(limitSize < 0){
			throw new RuntimeException("请指定缓存最大数量");
		}
		if(maxOutSize < 0){
			throw new RuntimeException("请指定缓存最大淘汰数量");
		}
		if(maxOutSize > limitSize){
			throw new RuntimeException("淘汰数量不能大于缓存最大数量");
		}
		this.limitSize = limitSize;
		this.store = new LinkedHashMap<Object, byte[]>(){
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(Entry<Object, byte[]> eldest) {
				return size() > maxOutSize;
			}
		};
	}
	/**
	 *
	 * *
	 * @param key
	 * @param value
	 */
	public void put(Object key, byte[] value) {
		synchronized (store) {
			if(size() <= limitSize){
				store.put(key, value);
			}
		}
	}
	/**
	 *
	 * *
	 * @param key
	 * @return
	 */
	public byte[] get(Object key) {
		synchronized (store) {
			return store.get(key);
		}
	}
	/**
	 *
	 * *
	 * @return
	 */
	public int size(){
		return store.size();
	}
	/**
	 *
	 * *
	 * @param key
	 * @return
	 */
	public boolean containKey(String key){
		return store.containsKey(key);
	}
}

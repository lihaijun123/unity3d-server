package com.focustech.cief.filemanage.dataserver.core.conf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SuffixContentTypeConfig {

	public static final String CONTENT_TYPE_JPEG = "image/jpeg";
	public static final String CONTENT_TYPE_WMA = "audio/wma";
	public static final String CONTENT_TYPE_MP3 = "audio/mp3";
	public static final String CONTENT_TYPE_OGG = "audio/ogg";
	public static final String CONTENT_TYPE_OGV = "video/ogv";
	public static final String CONTENT_TYPE_MP4 = "video/mp4";

	public static final String SUFFIX_OGV = ".ogv";
	public static final String SUFFIX_OGG = ".ogg";
	public static final String SUFFIX_JPEG = ".jpeg";
	public static final String SUFFIX_JPG = ".jpg";
	public static final String SUFFIX_MP3 = ".mp3";
	public static final String SUFFIX_WMA = ".wma";
	public static final String SUFFIX_MP4 = ".mp4";

	public static Map<String, String> suffixContentTypeMap = new ConcurrentHashMap<String, String>();

	static {
		suffixContentTypeMap.put(SUFFIX_JPEG, CONTENT_TYPE_JPEG);
		suffixContentTypeMap.put(SUFFIX_OGG, CONTENT_TYPE_OGG);
		suffixContentTypeMap.put(SUFFIX_OGV, CONTENT_TYPE_OGV);
		suffixContentTypeMap.put(SUFFIX_JPEG, CONTENT_TYPE_JPEG);
		suffixContentTypeMap.put(SUFFIX_JPG, CONTENT_TYPE_JPEG);
		suffixContentTypeMap.put(SUFFIX_MP3, CONTENT_TYPE_MP3);
		suffixContentTypeMap.put(SUFFIX_WMA, CONTENT_TYPE_WMA);
		suffixContentTypeMap.put(SUFFIX_MP4, CONTENT_TYPE_MP4);
	}

}

package com.focustech.cief.filemanage.app.controller;

import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
/**
 * 多线程下载封装类
 * *
 * @author lihaijun
 *
 */
public class Range {
	private Integer begin;
	private Integer end;
	private Integer length;
	private Integer totalLength;
	public Range(String range, Integer fileSize){
		this.totalLength = fileSize;
		range = range.replaceAll("bytes=", "");
		String[] split = range.split("-");
		if(split.length == 1){
			String str1 = split[0].trim();
			if(StringUtils.isNotEmpty(str1)){
				begin = TCUtil.iv(str1);
			}
			end = totalLength - 1;
		}
		if(split.length == 2){
			String str1 = split[0].trim();
			if(StringUtils.isNotEmpty(str1)){
				begin = TCUtil.iv(str1);
			}
			String str2 = split[1].trim();
			if(StringUtils.isNotEmpty(str2)){
				end = TCUtil.iv(str2);
			}
		}
		if(begin != null && end == null){
			//bytes 0-
			length = (fileSize - begin);
		}
		if(begin != null && end != null){
			// bytes 0-1
			length = (end - begin + 1);
		}
	}

	@Override
	public String toString() {
		return "bytes " + getBegin() + "-" + getEnd() + "/" + getTotalLength() + ";" + length;
	}

	public String toRange(){
		return "bytes " + getBegin() + "-" + getEnd() + "/" + getTotalLength();
	}

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
}

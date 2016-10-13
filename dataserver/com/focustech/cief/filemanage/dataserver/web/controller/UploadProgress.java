package com.focustech.cief.filemanage.dataserver.web.controller;

import net.sf.json.JSONObject;

import com.focustech.common.utils.MathUtils;
import com.focustech.common.utils.TCUtil;

/**
 * 上传进度
 * *
 * @author lihaijun
 *
 */
public class UploadProgress {

	private boolean uploadProgress = false;//是否计算进度

	private String progressKey = "";//进度数据存放的key

	private long uploading;//已经上传的字节数

	private long totalSize;//文件总字节数

	private int count;//文件数量

	private double percentage;//进度百分比

	public boolean isUploadProgress() {
		return uploadProgress;
	}

	public void setUploadProgress(boolean uploadProgress) {
		this.uploadProgress = uploadProgress;
	}

	public String getProgressKey() {
		return progressKey;
	}

	public void setProgressKey(String progressKey) {
		this.progressKey = progressKey;
	}

	public long getUploading() {
		return uploading;
	}

	public void setUploading(long uploading) {
		this.uploading = uploading;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		JSONObject jo = new JSONObject();
		jo.put("uploading", TCUtil.sv(getUploading()));
		jo.put("totalSize", TCUtil.sv(getTotalSize()));
		double d = MathUtils.div(uploading, totalSize, 2);
		jo.put("progress", "%" + Math.round(d * 100));
		return jo.toString();
	}
}

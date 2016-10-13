package com.focustech.cief.filemanage.dataserver.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件块
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "com_file_block")
public class FileBlock {
	private long sn;
	private String name;
	private Long capacity;
	private Date createTime;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getSn() {
		return sn;
	}
	public void setSn(long sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

package com.focustech.cief.filemanage.dataserver.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 文件块与server关系对象
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "com_file_block_server")
public class FileBlockServer {
	private long sn;
	private FileBlock fileBlock;
	private Long serverSn;
	private long fileAmount;
	private long version;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getSn() {
		return sn;
	}
	public void setSn(long sn) {
		this.sn = sn;
	}
	@ManyToOne
	@JoinColumn(name = "block_sn")
	public FileBlock getFileBlock() {
		return fileBlock;
	}
	public void setFileBlock(FileBlock fileBlock) {
		this.fileBlock = fileBlock;
	}
	public Long getServerSn() {
		return serverSn;
	}
	public void setServerSn(Long serverSn) {
		this.serverSn = serverSn;
	}
	public long getFileAmount() {
		return fileAmount;
	}
	public void setFileAmount(long fileAmount) {
		this.fileAmount = fileAmount;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}

}

package com.focustech.cief.filemanage.dataserver.exception;
/**
 * 文件保存异常
 * *
 * @author lihaijun
 *
 */
public class FileSaveException extends Exception {
	private static final long serialVersionUID = 1L;

	public FileSaveException(){

	}

	public FileSaveException(String msg){
		super(msg);
	}
}

/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.focustech.cief.filemanage.dataserver.common.encrypter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.focustech.core.utils.StringUtils;
import com.focustech.utils.encrypt.EncryptHandler;

/**
 * @author sunwei
 */
public class DefaultEncryptComponentImpl implements EncryptComponent {
	public final static Log log = LogFactory
			.getLog(DefaultEncryptComponentImpl.class);
	// 正常ID加解密
	public EncryptHandler encryptHandler;
	// 图片ID加解密
	public EncryptHandler encryptHandler4Image;
	// 加密和解密处理类
	public String encryptHandlerClass;
	// ID最小长度
	public Integer unEncryptMinLen;
	// ID最大长度
	public Integer unEncryptMaxLen;
	// 加密后的长度
	public Integer encryptLen;
	// 待加密的值包含的值
	public String encryptPosMap;
	public String encryptWheelMap;
	public String encryptPosMap4Image;
	public String encryptWheelMap4Image;
	public static final String SEP_COMMA = ",";

	/**
	 * 解密
	 */
	public Long decode(String idEncrypt) {
		try {
			return ("".equals(StringUtils.trimToEmpty(idEncrypt))) ? null
					: encryptHandler.decode(idEncrypt);
		} catch (Exception e) {
			log.error("decode(String idEncrypt) error idEncrypt=" + idEncrypt
					+ " encryptHandler=" + encryptHandler + e);
			return null;
		}
	}

	/**
	 * 加密
	 */
	public String encode(Long id) {
		try {
			return (id == null) ? null : encryptHandler.encode(id);
		} catch (Exception e) {
			log.error("encode(Long id) error id=" + id + " encryptHandler="
					+ encryptHandler + e);
			return null;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public String encodeImageID(Long id) {
		try {
			return (id == null) ? null : encryptHandler4Image.encode(id);
		} catch (Exception e) {
			log.error("encodeImageID(Long id) error id=" + id
					+ " encryptHandler4Image=" + encryptHandler4Image + e);
			return null;
		}
	}

	/**
	 * @param idEncrypt
	 * @return
	 */
	public Long decodeImageID(String idEncrypt) {
		try {
			return ("".equals(StringUtils.trimToEmpty(idEncrypt))) ? null
					: encryptHandler4Image.decode(idEncrypt);
		} catch (Exception e) {
			log.error("decodeImageID(String idEncrypt) error idEncrypt="
					+ idEncrypt + " encryptHandler4Image="
					+ encryptHandler4Image + e);
			return null;
		}
	}

	/**
	 * 完成加密器和解密器的初始化
	 *
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		Class<?> clsHandler = Class.forName(getEncryptHandlerClass());
		encryptHandler = (EncryptHandler) clsHandler.newInstance();
		encryptHandler4Image = (EncryptHandler) clsHandler.newInstance();
		//
		encryptHandler.setPlainTextAndCipherTextLength(getUnEncryptMaxLen(),
				getEncryptLen());
		encryptHandler4Image.setPlainTextAndCipherTextLength(
				getUnEncryptMaxLen(), getEncryptLen());
		//
		encryptHandler.setPositionMangleTable(StringUtils.split(
				getEncryptPosMap(), SEP_COMMA));
		//
		encryptHandler.setEncryptionTable(getEncryptWheelMap());
		// ///////////////////////////////////////////////////
		// 初始化图片ID加密处理器
		encryptHandler4Image.setPositionMangleTable(StringUtils.split(
				getEncryptPosMap4Image(), SEP_COMMA));
		encryptHandler4Image.setEncryptionTable(getEncryptWheelMap4Image());
	}

	public String getEncryptHandlerClass() {
		return encryptHandlerClass;
	}

	public void setEncryptHandlerClass(String encryptHandlerClass) {
		this.encryptHandlerClass = encryptHandlerClass;
	}

	public Integer getUnEncryptMinLen() {
		return unEncryptMinLen;
	}

	public void setUnEncryptMinLen(Integer unEncryptMinLen) {
		this.unEncryptMinLen = unEncryptMinLen;
	}

	public Integer getUnEncryptMaxLen() {
		return unEncryptMaxLen;
	}

	public void setUnEncryptMaxLen(Integer unEncryptMaxLen) {
		this.unEncryptMaxLen = unEncryptMaxLen;
	}

	public Integer getEncryptLen() {
		return encryptLen;
	}

	public void setEncryptLen(Integer encryptLen) {
		this.encryptLen = encryptLen;
	}

	public String getEncryptPosMap() {
		return encryptPosMap;
	}

	public void setEncryptPosMap(String encryptPosMap) {
		this.encryptPosMap = encryptPosMap;
	}

	public String getEncryptWheelMap() {
		return encryptWheelMap;
	}

	public void setEncryptWheelMap(String encryptWheelMap) {
		this.encryptWheelMap = encryptWheelMap;
	}

	public String getEncryptPosMap4Image() {
		return encryptPosMap4Image;
	}

	public void setEncryptPosMap4Image(String encryptPosMap4Image) {
		this.encryptPosMap4Image = encryptPosMap4Image;
	}

	public String getEncryptWheelMap4Image() {
		return encryptWheelMap4Image;
	}

	public void setEncryptWheelMap4Image(String encryptWheelMap4Image) {
		this.encryptWheelMap4Image = encryptWheelMap4Image;
	}
}

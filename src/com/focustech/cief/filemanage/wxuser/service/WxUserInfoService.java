package com.focustech.cief.filemanage.wxuser.service;

import java.util.List;

import com.focustech.cief.filemanage.wxuser.model.WxUserInfo;

public interface WxUserInfoService<T extends WxUserInfo> {

	T exist(String mobile);

    void insertOrUpdate(T t);

   	List<T> list();
}

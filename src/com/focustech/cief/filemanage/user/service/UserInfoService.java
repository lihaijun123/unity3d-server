package com.focustech.cief.filemanage.user.service;

import java.util.List;

import com.focustech.cief.filemanage.user.model.UserInfo;

public interface UserInfoService<T extends UserInfo> {

	T exist(String mobile, String email, String appName);

    void insertOrUpdate(T t);

   	List<T> list();
}

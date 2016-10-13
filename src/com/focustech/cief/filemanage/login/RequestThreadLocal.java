package com.focustech.cief.filemanage.login;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class RequestThreadLocal {


	public static final ThreadLocal<LoginInfo> loginInfoThreadLocal = new ThreadLocal<LoginInfo>();


	public static LoginInfo getLoginInfo(){
		return loginInfoThreadLocal.get();
	}

	public static void setLoginInfo(Object loginInfo){
		if(loginInfo != null && loginInfo instanceof LoginInfo){
			loginInfoThreadLocal.set((LoginInfo)loginInfo);
		}
	}

	public static void cleanAll(){
		loginInfoThreadLocal.remove();
	}
}

package com.focustech.cief.filemanage.login;

import com.focustech.common.utils.StringUtils;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class LoginInfo {

	private String name;

	private String password;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean check(String loginUsers){
		boolean flag = false;
		if(StringUtils.isNotEmpty(loginUsers)){
			String[] split = loginUsers.split(";");
			if(split.length > 0){
				for (String info : split) {
					String[] split2 = info.split("#");
					if(split2.length == 2){
						String userName = split2[0];
						String password = split2[1];
						if(userName.equals(this.getName()) && password.equals(this.getPassword())){
							flag = true;
							break;
						}
					}
				}
			}
		}
		return flag;
	}
}

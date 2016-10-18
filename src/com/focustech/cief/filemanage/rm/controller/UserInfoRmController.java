package com.focustech.cief.filemanage.rm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.user.model.UserInfo;
import com.focustech.cief.filemanage.user.service.UserInfoService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.StringUtils;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/rm/user")
public class UserInfoRmController extends AbstractController {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserInfoRmController.class);
	@Autowired
	private UserInfoService<UserInfo> userInfoService;
	private Lock lock = new ReentrantLock();
	/**
	 * http://192.168.1.105/fs/rm/user/register?mobile=13451836990&email=sdfsafsafa&appName=dfsdf
	 * *
	 * @param xmlId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/register")
	public void register(String mobile, String email, String appName, HttpServletResponse response) throws IOException{
		log.debug("mobile:" + mobile + ",email:" + email + ", appName:" + appName);
		int status = 0;
		String msg = "";
		String userId = "";
		try {
			if(!StringUtils.isEmpty(appName)){
				if(StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)){
					status = 1;
					msg = "手机和邮箱不能都为空";
				} else {
					if(StringUtils.isNotEmpty(email)){
						/*String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
						Pattern regex = Pattern.compile(check);
						Matcher matcher = regex.matcher(email);
						if(!matcher.matches()){
							status = 2;
							msg = "邮箱格式不正确";
						}*/
					}
					if(StringUtils.isNotEmpty(mobile)){
						/*if(mobile.length() < 6 || mobile.length() > 11){
							status = 3;
							msg = "手机号码不正确";
						}*/
					}
					try {
						lock.lock();
						if(status == 0){
							UserInfo userInfo = userInfoService.exist(mobile, email, appName);
							if(userInfo == null){
								userInfo = new UserInfo();
								userInfo.setName(appName);
								userInfo.setEmail(email);
								userInfo.setMobile(mobile);
								userInfo.setAddTime(new Date());
								userInfoService.insertOrUpdate(userInfo);
								msg = "注册成功";
							} else {
								status = 4;
								msg = "用户已存在";
							}
							userId = EncryptUtil.encode(userInfo.getSn());
						}
					} catch (Exception e) {
						throw e;
					} finally {
						lock.unlock();
					}
				}
			} else {
				status = 5;
				msg = "appName不能为空";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		jo.put("message", msg);
		jo.put("userId", userId);
		String rv = jo.toString();
		log.debug(rv);
		ajaxOutput(response, rv);
	}
}

package com.focustech.cief.filemanage.rm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
import com.focustech.cief.filemanage.log.service.StatAppUseTimeLogService;
import com.focustech.common.utils.MathUtils;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/rm/usetime")
public class StatAppUseTimeLogRmController extends AbstractController{
	@Autowired
	private StatAppUseTimeLogService<StatAppUseTimeLog> appUseTimeLogService;
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * http://192.168.1.105/fs/rm/usetime?userId=NcAUyopKeoVN&appName=kuk&useTime=13.5
	 * *
	 * @param mobile
	 * @param email
	 * @param appName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void record(String userId, String appName, String useTime, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(appName) && StringUtils.isNotEmpty(useTime)){
			try {
				//Long decode = EncryptUtil.decode(userId);
				lock.lock();
				String userInfo = userId;
				StatAppUseTimeLog appUseTimeLog = appUseTimeLogService.select(appName, userInfo);
				if(appUseTimeLog == null){
					appUseTimeLog = new StatAppUseTimeLog();
					appUseTimeLog.setUserId(null);
					appUseTimeLog.setAppName(appName);
					appUseTimeLog.setUseTime(useTime);
					appUseTimeLog.setAddDate(new Date());
				} else {
					String ut = appUseTimeLog.getUseTime();
					appUseTimeLog.setUseTime(TCUtil.sv(MathUtils.add(TCUtil.dv(ut), TCUtil.dv(useTime))));
				}
				appUseTimeLogService.insertOrUpdate(appUseTimeLog);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
}

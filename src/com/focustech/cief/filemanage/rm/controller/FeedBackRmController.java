package com.focustech.cief.filemanage.rm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.log.model.FeedBack;
import com.focustech.cief.filemanage.log.service.FeedBackService;
import com.focustech.common.utils.StringUtils;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/rm/feedback")
public class FeedBackRmController extends AbstractController{
	@Autowired
	private FeedBackService<FeedBack> feedBackService;
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * http://127.0.0.1:8018/fs/rm/feedback?conent=意见反馈
	 * *
	 * @param mobile
	 * @param email
	 * @param appName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void record(String conent, String userInfo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(StringUtils.isNotEmpty(conent)){
			try {
				//Long decode = EncryptUtil.decode(userId);
				lock.lock();
				FeedBack feedBack = new FeedBack();
				feedBack.setContent(conent);
				feedBack.setAddTime(new Date());
				if(StringUtils.isNotEmpty(userInfo)){
					feedBack.setUserInfo(userInfo);
				}
				feedBackService.insertOrUpdate(feedBack);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap modelMap){
		List<FeedBack> list = feedBackService.list();
		modelMap.put("list", list);
		return "/feedback/list";
	}
}

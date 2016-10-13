package com.focustech.cief.filemanage.dataserver.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.focustech.common.constant.OverallConst;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;


public abstract class BaseController{
    protected Log log = LogFactory.getLog(BaseController.class);
    /**编码utf-8*/
    protected final static String  ENCODE_UTF8 = "UTF-8";
    /**response.contentType 类型*/
    protected final static String  CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
    /**ip地址匹配*/
    private static Pattern pattern = Pattern.compile("^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$");





    protected void validateNull(Object obj) {
        validateNull(obj, "试图对不存在的数据进行操作！");
    }

    /**
     * 验证给定的对象是否为空，如果为空，则跳转到错误页面，显示用户指定的信息
     *
     * @param obj 实体对象
     * @param message 用户指定的提示信息
     */
    protected void validateNull(Object obj, String message) {
        if (obj == null) {
            throw new RuntimeException(message);
        }
    }

    /**
     *
     * *
     * @param url
     * @param statusCode
     * @param req
     * @return
     */
    protected String forward(String url, HttpServletRequest req) {
    	String saveCode = TCUtil.sv(req.getSession().getAttribute("saveCode"));
		if(StringUtils.isNotEmpty(saveCode)){
			req.setAttribute("saveCode", saveCode);
			req.getSession().removeAttribute("saveCode");
		}
    	return url;
    }





    /**
     * ajax输出
     *
     * @param response HttpServletResponse
     * @param outputString 输出字符
     * @throws IOException 输出流异常
     */
    protected void ajaxOutput(HttpServletResponse response, String outputString) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(outputString);
        response.getWriter().flush();
    }

    /**
     * ajax输出
     *
     * @param response HttpServletResponse
     * @param outputString 输出字符
     * @throws IOException 输出流异常
     */
    protected void ajaxOutputNoCache(HttpServletResponse response, String outputString) throws IOException {
    	removeResqCache(response);
    	ajaxOutput(response, outputString);
    }



    /**
     * 用于forward到某个页面
     *
     * @param url 目标url
     * @return 增加redirect处理过的url
     */
    public String forwardTo(String url) {
        if (StringUtils.isEmpty(url) || url.startsWith("forward:")) {
            return url;
        }
        else {
            return "forward:" + url;
        }
    }
    /**
     * 重定向404页面
     * *
     * @param systemName
     * @return
     */
    public String redirectTo404(String systemName){
        /*
         * if(OverallConst.SYSTEM_VO.equals(systemName)){ return
         * redirect(MessageUtils.getInfoValue(OverallConst.VO_DOMAIN) + "/html/404.html"); } return
         * redirect(MessageUtils.getInfoValue(OverallConst.AVATAR_DOMAIN) + "/html/404.html");
         */
        return "/404";

    }

    /**
     * 设置会员中心域名
     * 设置Avatar域名
     * 推荐使用HttpTool直接在html里面调用
     * *
     * @param modelMap
     */
    @Deprecated
    public void setDomain(HttpServletRequest req){
    	req.setAttribute(OverallConst.AVATAR_DOMAIN, MessageUtils.getInfoValue(OverallConst.AVATAR_DOMAIN));
    	req.setAttribute(OverallConst.VO_DOMAIN, MessageUtils.getInfoValue(OverallConst.VO_DOMAIN));
    }
    /**
     * 加密
     * *
     * @param value
     * @return
     */
    protected String encrypt(String value){
    	Assert.notNull(value);
    	return EncryptUtil.encode(value);

    }

    protected String encrypt(Long value){
    	Assert.notNull(value);
    	return EncryptUtil.encode(value);
    }
    /**
     * 解密
     * *
     * @param value
     * @return
     */
    protected String decrypt(String value){
    	Assert.notNull(value);
		return EncryptUtil.decode2Str(value);
    }



    /**
     * 解密
     *
     * @param entcrySns
     * @return
     */
    protected List<Long> decryptSnBatch(String[] entcryStrs) {
        List<Long> selectedItems = new ArrayList<Long>();
        if (null != entcryStrs) {
            for (String snStr : entcryStrs) {
                selectedItems.add(TCUtil.lv(decrypt(snStr)));
            }
        }
        return selectedItems;
    }

    /**
     * 去掉请求缓存设置
     * *
     * @param resq
     */
    protected void removeResqCache(HttpServletResponse resq) {
		resq.setHeader("Pragma", "no-cache");
		resq.setHeader("Cache-Control", "no-cache");
		resq.setHeader("Expires", "0");
	}


    /**
     * 使用redirect跳转到指定的请求上
     *
     * @param URI 指定的请求链接
     * @return
     */
    public static String redirect(String URI) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("redirect:");
        stringBuilder.append(URI);
        return stringBuilder.toString();
    }

}

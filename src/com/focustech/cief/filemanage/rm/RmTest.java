package com.focustech.cief.filemanage.rm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpMethod;

public class RmTest {

	public static void main(String[] arg){
		FntRpc fntRpc = new FntRpc();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("userId", "xxd33"));
		qparams.add(new BasicNameValuePair("appName", "test"));
		qparams.add(new BasicNameValuePair("useTime", "10.1"));
		fntRpc.httpRequest("/fs/rm/usetime", qparams, HttpMethod.POST);
		/*FntRpc fntRpc = new FntRpc();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("conent", "意见反馈6666"));
		fntRpc.httpRequest("/fs/rm/feedback", qparams, HttpMethod.POST);*/
		/*FntRpc fntRpc = new FntRpc();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("contact", "13451836990"));
		qparams.add(new BasicNameValuePair("conent", "意见反馈6666"));
		fntRpc.httpRequest("/fs/rm/feedback", qparams, HttpMethod.POST);*/
	}
}

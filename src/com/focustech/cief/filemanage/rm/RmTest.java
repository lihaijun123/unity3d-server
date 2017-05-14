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
		qparams.add(new BasicNameValuePair("conent", "意见反馈33344"));
		fntRpc.httpRequest("/fs/rm/feedback", qparams, HttpMethod.POST);
	}
}

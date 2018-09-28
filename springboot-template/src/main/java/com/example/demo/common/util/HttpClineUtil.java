package com.example.demo.common.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
@Component
public class HttpClineUtil {
	
	public  String post(String url,String content) throws ClientProtocolException, IOException{
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-Type", "application/json; charset=utf-8");
		String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";
		StringEntity se = new StringEntity(textMsg, "utf-8");
		httppost.setEntity(se);
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return EntityUtils.toString(response.getEntity(), "utf-8");
		}
		return content;
	}

}

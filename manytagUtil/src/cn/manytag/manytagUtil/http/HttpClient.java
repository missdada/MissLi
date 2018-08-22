package cn.manytag.manytagUtil.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.manytag.manytagUtil.util.ResultSet;
import cn.manytag.manytagUtil.util.StringUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @deprecated 请使用 HttpUtil类代替
 *
 */
public class HttpClient {

	private String ip;
	private Integer port;
	private String serviceName;
	private String url;

	public HttpClient(String url) {
		this.url = url;
	}

	public HttpClient(String ip, Integer port, String serviceName) {
		this.ip = ip;
		this.port = port;
		this.serviceName = serviceName;
		url = "http://" + this.ip + (this.port == null ? "" : (":" + this.port)) + "/" + this.serviceName;
	}

	public ResultSet resultSet(Map<String, Object> param) {
		String str = post(param);
		ResultSet rs;
		try {
			rs = (ResultSet) JSONObject.toBean(JSONObject.fromObject(str), ResultSet.class);
		} catch (JSONException e) {
			rs = new ResultSet(ResultSet.Code.connError, "调用通信错误", null, str);
		}
		return rs;
	}

	public String post(Map<String, Object> param) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity uefEntity;

		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		if (param != null) {
			for (Entry<String, Object> entry : param.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), StringUtil.valueOfNull(entry.getValue())));
			}
		}

		try {
			uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
			httpPost.setEntity(uefEntity);

			CloseableHttpResponse response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String post(String cmd, String strJsonParam) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", cmd);
		param.put("strJsonParam", strJsonParam);
		return post((Map<String, Object>) param);
	}

}

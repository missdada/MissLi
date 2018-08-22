package cn.manytag.manytagUtil.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.SmartstoreDeviceIashelfFeedbackRequest;
import com.taobao.api.response.SmartstoreDeviceIashelfFeedbackResponse;

import net.sf.json.JSONObject;

public class TBUtil {

	private static final Logger log = Logger.getLogger(TBUtil.class);
	public String url;
	public String appkey;
	public String secret;
	public String sessionKey;

	/**
	 * 实体店.
	 * 
	 * @param url
	 * @param appkey
	 * @param secret
	 * @param sessionKey
	 */
	public TBUtil(String url, String appkey, String secret, String sessionKey) {
		this.url = url;
		this.appkey = appkey;
		this.secret = secret;
		this.sessionKey = sessionKey;
	}

	/**
	 * 快闪店.
	 * 
	 * @param url
	 * @param appkey
	 * @param secret
	 */
	public TBUtil(String url, String appkey, String secret) {
		this.url = url;
		this.appkey = appkey;
		this.secret = secret;
	}

	/**
	 * 互动云货架回流接口.
	 * 
	 * @param storeType 0-快闪店，1-实体店
	 * @param deviceCode 设备编号
	 * @param itemId 淘宝id
	 * @param action 动作
	 * @param time 时间
	 * @return
	 * @throws ApiException
	 */
	public Result iashelfFeedback(int storeType, String deviceCode, String itemId, String action, Date time) throws ApiException {
		if (storeType == 0) {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			SmartstoreDeviceIashelfFeedbackRequest req = new SmartstoreDeviceIashelfFeedbackRequest();
			req.setDeviceCode(deviceCode);
			req.setAction(action);
			req.setItemId(itemId);
			req.setOpTime(time);
			SmartstoreDeviceIashelfFeedbackResponse rsp = client.execute(req);
			JSONObject json = JSONObject.fromObject(rsp.getBody());
			if (json == null || json.isNullObject()) {
				return Result.error(2, "回流错误!");
			}
			json = json.getJSONObject("smartstore_device_iashelf_feedback_response");
			if (json == null || json.isNullObject()) {
				JSONObject j = JSONObject.fromObject(rsp.getBody());
				j = j.getJSONObject("error_response");
				String msg = j.getString("sub_msg");
				return Result.error(msg);
			}
			if (!Boolean.parseBoolean((String) json.get("result"))) {
				return Result.error(2, "回流错误!");
			}
		}
		return Result.SUCCESS;
	}
}

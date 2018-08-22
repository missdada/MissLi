package cn.manytag.manytagUtil.util;

import net.sf.json.JSONObject;

/**
 * 不建议使用，建议Result
 */
public class ResultSet {
	private Code code;
	private String message;
	private String json;
	private Object obj;

	public enum Code {
		/**
		 * 成功
		 */
		success(0),
		/**
		 * 版本有更新
		 */
		versionUpdate(1),
		/**
		 * 连接超时
		 */
		connOutTime(-3),
		/**
		 * 通信错误
		 */
		connError(-5),
		/**
		 * 登录拦截
		 */
		loginError(-6),
		/**
		 * 参数有误
		 */
		paramError(-1),
		/**
		 * 业务错误
		 */
		serviceError(-4),
		/**
		 * 程序错误
		 */
		error(-2);

		private final int value;

		Code(int val) {
			this.value = val;
		}

		public int getValue() {
			return value;
		}
	}

	public ResultSet() {
	}

	public ResultSet(Code code, String msg, String json) {
		this.setCode(code);
		this.setMessage(msg);
		this.setJson(json);
	}

	public ResultSet(Code code, String message, String json, Object obj) {
		super();
		this.code = code;
		this.message = message;
		this.json = json;
		this.obj = obj;
	}

	public ResultSet(Code code, JSONObject json) {
		this(code, null, json.toString());
	}

	public ResultSet(Code code, String msg) {
		this(code, msg, null);
	}

	public ResultSet(Code code) {
		this(code, null, null);
	}

	@Override
	public String toString() {
		return "ResultSet [code=" + code + ", message=" + message + ", json=" + json + ", obj="
				+ obj + "]";
	}

	public boolean isSuccess() {
		return Code.success == code;
	}

	public static ResultSet success() {
		return new ResultSet(Code.success);
	}

	public static ResultSet success(Object obj) {
		return new ResultSet(Code.success, null, null, obj);
	}

	public static ResultSet success(JSONObject json) {
		return new ResultSet(Code.success, null, json.toString());
	}

	public static ResultSet success(String json) {
		return new ResultSet(Code.success, null, json);
	}

	public static ResultSet paraError(String msg) {
		return new ResultSet(Code.paramError, msg);
	}

	public static ResultSet error(String msg) {
		return new ResultSet(Code.error, msg);
	}

	public static ResultSet loginError() {
		return new ResultSet(Code.loginError);
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}

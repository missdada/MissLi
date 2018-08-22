package cn.manytag.manytagUtil.util;

import net.sf.json.JSONObject;

public class Result {

	public static final Result SUCCESS = new Result(Code.success);
	/** 是否成功等信息 */
	private Code code;
	/** 信息标识 */
	private Integer status;
	/** 提示信息 */
	private String msg;
	/** 数据 */
	private Object data;

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

	public Result() {
	}

	public Result(Code code) {
		this.code = code;
	}

	public Result(Code code, String msg) {
		this(code, msg, null);
	}

	public Result(Code code, JSONObject json) {
		this.code = code;
		this.data = json;
	}

	public Result(Code code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Result(Code code, Integer status, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.status = status;
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", status" + status + ", msg=" + msg + ", data=" + data + "]";
	}

	public boolean isSuccess() {
		return Code.success == code;
	}

	public static Result success() {
		return new Result(Code.success);
	}

	public static Result success(JSONObject json) {
		return new Result(Code.success, null, json);
	}

	public static Result success(Object data) {
		return new Result(Code.success, null, data);
	}

	public static Result success(String data) {
		return new Result(Code.success, null, data);
	}

	public static Result success(String msg, String data) {
		return new Result(Code.success, msg, data);
	}

	public static Result paraError(String msg) {
		return new Result(Code.paramError, msg);
	}

	public static Result paraError(Integer status, String msg) {
		return new Result(Code.paramError, status, msg, null);
	}

	public static Result error(String msg) {
		return new Result(Code.error, msg);
	}

	public static Result error(Integer status, String msg) {
		return new Result(Code.paramError, status, msg, null);
	}

	public static Result serviceError(String msg) {
		return new Result(Code.serviceError, msg);
	}

	public static Result serviceError(int status, String msg) {
		return new Result(Code.serviceError, status, msg, null);
	}

	public static Result loginError() {
		return new Result(Code.loginError);
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

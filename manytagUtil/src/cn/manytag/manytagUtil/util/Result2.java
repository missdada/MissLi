package cn.manytag.manytagUtil.util;

public class Result2 {

	/** 成功状态 */
	public static final int STATUS_SUCCESS = 0;
	/** 默认失败状态 */
	public static final int STATUS_ERROR = -1;
	/** 成功的无其他数据的Result常量 */
	public static final Result2 SUCCESS = new Result2(STATUS_SUCCESS);

	/** 状态 */
	private int status;
	/** 说明 */
	private String msg;
	/** 数据 */
	private Object data;

	public Result2() {
		this.status = STATUS_ERROR;
	}

	public Result2(int status) {
		this.status = status;
	}

	public Result2(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Result2(Integer status, Object data) {
		this.status = status;
		this.data = data;
	}

	public Result2(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return STATUS_SUCCESS == status;
	}

	public String dataString() {
		return StringUtil.valueOfNull(data);
	}

	public static Result2 success(Object data) {
		return new Result2(STATUS_SUCCESS, data);
	}

	public static Result2 error() {
		return new Result2(STATUS_ERROR);
	}

	public static Result2 error(String msg, Object data) {
		return new Result2(STATUS_ERROR, msg, data);
	}

	@Override
	public String toString() {
		return JsonUtil.bean2json(this);
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

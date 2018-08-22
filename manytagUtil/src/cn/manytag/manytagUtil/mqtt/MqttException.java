package cn.manytag.manytagUtil.mqtt;

public class MqttException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4238937313849573571L;

	MqttException() {
	}

	public MqttException(String msg) {
		super(msg);
	}

	MqttException(Exception e) {
		super(e);
	}

}

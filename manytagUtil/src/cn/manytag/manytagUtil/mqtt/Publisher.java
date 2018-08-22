package cn.manytag.manytagUtil.mqtt;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import cn.manytag.manytagUtil.util.StringUtil;
import net.sf.json.JSONObject;

public class Publisher {
	private static final Logger log = Logger.getLogger(Publisher.class);
	private FutureConnection connection;
	private MQTT mqtt;

	public Publisher(String url) throws URISyntaxException, MqttException {
		mqtt = new MQTT();
		mqtt.setHost(url);
		getConnect();
	}

	public Publisher(String ip, int port) throws MqttException, URISyntaxException {
		mqtt = new MQTT();
		//设置服务端的ip 
		mqtt.setHost(ip, port);
		getConnect();
	}

	public void getConnect() throws MqttException {
		//使用Future创建连接
		connection = mqtt.futureConnection();
		try {
			//await()获取mqtt的连接对象BlockingConnection
			//超时时间5秒
			connection.connect().await(5L, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new MqttException(e);
		}
	}

	public void publish(String topic) throws MqttException {
		publish(topic, (String) null);
	}

	public void publish(String topic, JSONObject json) throws MqttException {
		publish(topic, json.toString());
	}

	/**
	 * 发送数据
	 * 
	 * @param topic
	 * @param body
	 * @return
	 * @return
	 * @throws MqttException
	 */
	public void publish(Map<String, Object> map) throws MqttException {
		Set<Entry<String, Object>> s = map.entrySet();

		final LinkedList<Future<Void>> queue = new LinkedList<Future<Void>>();
		for (Entry<String, Object> entry : s) {
			String topic = entry.getKey();
			String body = StringUtil.valueOfNull(entry.getValue());
			queue.add(connection.publish(new UTF8Buffer(topic), body == null ? null : new UTF8Buffer(body), QoS.AT_LEAST_ONCE, false));
		}
		try {
			while (!queue.isEmpty()) {
				queue.removeFirst().await();
			}
		} catch (Exception e) {
			throw new MqttException(e);
		}
	}

	public void publish(String topic, Object body) throws MqttException {
		publish(topic, StringUtil.valueOfNull(body));
	}

	public void publish(String topic, String body) throws MqttException {
		try {
			connection.publish(new UTF8Buffer(topic), body == null ? null : new UTF8Buffer(body), QoS.AT_LEAST_ONCE, false).await();
		} catch (Exception e) {
			throw new MqttException(e);
		}
	}

	/**
	 * 断开连接
	 * 
	 * @throws MqttException
	 */
	public void disconnect() throws MqttException {
		try {
			connection.disconnect().await();
		} catch (Exception e) {
			throw new MqttException(e);
		}
	}

	/**
	 * 发送并断开
	 * 
	 * @param topic
	 * @param body
	 * @throws MqttException
	 * @throws URISyntaxException
	 */
	public void sendMqttDisconnect(String topic, String body) throws MqttException {
		publish(topic, body);
		disconnect();
	}

	/**
	 * 发送并断开
	 * 
	 * @param topic
	 * @param body
	 * @throws MqttException
	 * @throws URISyntaxException
	 */
	public void sendMqttDisconnect(String topic, Object body) throws MqttException {
		sendMqttDisconnect(topic, StringUtil.valueOfNull(body));
	}
}

package com.example.demo.common.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	@JmsListener(destination = "my-destination")
	public void receivedMessage(String message) {
		System.out.println("接受到：" + message);
	}

}

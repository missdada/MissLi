package com.example.demo;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.sender.FanoutSender;
import com.example.demo.common.sender.HelloSender;
import com.example.demo.common.sender.Sender;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitApplicationTests {

		@Autowired
		private HelloSender helloSender;

//		@Test
		public void hello() throws Exception {
			System.out.println("==========发送消息！");
			helloSender.send();
		}

		@Autowired
		private Sender sender;
//		@Test
		public void sendTest() throws Exception {
			//while (true) {
				String msg = new Date().toString();
				sender.send(msg);
				//Thread.sleep(1000);
			//}
		}
		
		@Autowired
	    private FanoutSender fanoutSender;
		
//		@Test
	    public void setFanoutSender(){
//	        fanoutSender.send();
	    }
		
		@Test
		public void sender() throws IOException, TimeoutException {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			factory.setUsername("guest1");
	        factory.setPassword("guest1");
	        factory.setPort(5672);
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare("abc", true, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", "abc", null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");

			channel.close();
			connection.close();
		}

		@Test
		public void receiver() throws IOException, TimeoutException {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			factory.setUsername("guest");
	        factory.setPassword("guest");
	        factory.setPort(5672);
			Connection connection =  factory.newConnection();
			Channel channel =  connection.createChannel();

			channel.queueDeclare("abc", true, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
				}
			};
			channel.basicConsume("abc", true, consumer);
		}
		
		
}

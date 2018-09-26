package com.example.demo;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.sender.FanoutSender;
import com.example.demo.common.sender.HelloSender;
import com.example.demo.common.sender.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitApplicationTests {

	// @Test
		// public void contextLoads() {
		// }

		@Autowired
		private HelloSender helloSender;

		@Test
		public void hello() throws Exception {
			System.out.println("==========发送消息！");
			helloSender.send();
		}

		@Autowired
		private Sender sender;
		@Test
		public void sendTest() throws Exception {
			//while (true) {
				String msg = new Date().toString();
				sender.send(msg);
				//Thread.sleep(1000);
			//}
		}
		
		@Autowired
	    private FanoutSender fanoutSender;
		
		@Test
	    public void setFanoutSender(){
//	        fanoutSender.send();
	    }
}

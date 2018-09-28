package com.example.demo;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.bo.Message;
import com.example.demo.common.util.HttpClineUtil;
import com.example.demo.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTemplateApplicationTests {
	@Autowired
	private EmailService emailService;

//	@Test
	public void contextLoads() {
		// 验证发送FreeMarker模板邮件
		Message message = new Message();
		message.setMessageCode("MissingParameter");
		message.setMessageStatus("Failed");
		message.setCause("缺少参数,请确认");
		emailService.sendTemplateMail(message, "测试消息通知", "message.ftl");
	}
	
	@Autowired
	private HttpClineUtil httpClineUtil;
	@Value("${dingding.webhook}")
	private String WEBHOOK_TOKEN;//钉钉机器人
	
	@Test
	public void testDingDing() throws ClientProtocolException, IOException {
		httpClineUtil.post(WEBHOOK_TOKEN, "距离放假还有2天");
	}

}

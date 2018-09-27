package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.util.RedisUtil;
import com.example.demo.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private RedisService redisService;

	 @Test
	public void contextLoads() {
		System.out.println(redisUtil.get("key01"));
	}

//	@Test
	public void contextLoads2() throws InterruptedException {
		System.out.println("获取值：" + redisService.get(2l));
		Thread.sleep(1000);

		System.out.println("获取值：" + redisService.get(2l));
		Thread.sleep(1000);

		System.out.println("获取值：" + redisService.get(2l));
		Thread.sleep(1000);

		System.out.println("获取值：" + redisService.get(2l));
	}
}

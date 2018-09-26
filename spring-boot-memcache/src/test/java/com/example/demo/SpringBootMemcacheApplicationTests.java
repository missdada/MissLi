package com.example.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.config.MemcachedRunner;

import net.spy.memcached.MemcachedClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMemcacheApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Resource
	private MemcachedRunner memcachedRunner;

	@Test
	public void testSetGet() {
		MemcachedClient memcachedClient = memcachedRunner.getClient();
		memcachedClient.set("testkey", 1000, "666666");
		System.out.println("***********  " + memcachedClient.get("testkey").toString());
	}

}

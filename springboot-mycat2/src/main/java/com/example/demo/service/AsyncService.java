package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncService {
	 private Logger logger = LoggerFactory.getLogger(AsyncService.class);
	
	@Async("taskExecutor")
	public void dealData(String id) throws InterruptedException{
		logger.info(">>>>>>开始执行 dealData"+id);
		Thread.sleep(1000);
		logger.info("<<<<<<结束执行 dealData"+id);	
	}

}

package com.example.demo.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.AsyncService;

@Component
public class DataDealTask {
	 private Logger logger = LoggerFactory.getLogger(DataDealTask.class);
	 @Autowired
	 private AsyncService asyncService;
	 
//	 /**
//	  * 定时处理数据任务
//	  *  
//	  */
//	 @Async("taskExecutor")
//     @Scheduled(cron = "${data.deal.task}")
//     public void dataDealTask() {
//    	 logger.info("开始执行关键控制参数刷新接口 dataDealTask");
//     }
//	 @Async("taskExecutor")
//     @Scheduled(cron = "${data.deal.task}")
//     public void dataDealTask2() {
//    	 logger.info("开始执行关键控制参数刷新接口 dataDealTask2");
//     }
	 
	 
	 

	/**
	 * 定时启动几个异步任务
	 * @throws InterruptedException 
	 */
	@Scheduled(cron = "${data.deal.task}")
     public void dataDealTask3() throws InterruptedException {
    	 logger.info(">>>>>开始执行定时生成异步任务 dataDealTask3");
    	 for (int i = 0; i < 5; i++) {
    		 asyncService.dealData(i+"");
    	 }
    	 logger.info("<<<<<结束执行定时生成异步任务 dataDealTask3");
     }
}

package org.jeedevframework.springboot.web;

import org.jeedevframework.springboot.message.CacheRefreshRedisProducer;
import org.jeedevframework.springboot.message.redis.RedisMessageSender;
import org.jeedevframework.springboot.message.redis.queue.DataMessage;
import org.jeedevframework.springboot.message.redis.queue.ManualRedisQueueService;
import org.jeedevframework.springboot.message.redis.queue.MessageQueue;
import org.jeedevframework.springboot.message.redis.queue.QueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	private static MessageQueue<DataMessage> dataQueue = QueueManager.getQueue("DATA_QUEUE");
	
	@Autowired
	private RedisMessageSender redisMessageSender;
	
	@Autowired
	private CacheRefreshRedisProducer cacheRefreshRedisProducer;
	
	@Autowired
	private ManualRedisQueueService manualRedisQueueService;
	
	@RequestMapping("/send")
	public String send() {
		
		//基于RedisMessageListenerContainer实现演示队列 
		
		redisMessageSender.sendMessage("testChannel","hello world");
		redisMessageSender.sendMessage("testChannel","hello world");
		redisMessageSender.sendMessage("testChannel","hello world");
		redisMessageSender.sendMessage("testChannel","hello world");
		System.out.println("sended:hello world");
		cacheRefreshRedisProducer.clearCache();
		
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		redisMessageSender.sendMessage("testChannel","hello world-t");
		
		
		//基于redis实现自定义队列
		
		dataQueue.push(new DataMessage("hello"));
		dataQueue.push(new DataMessage("world"));
		dataQueue.push(new DataMessage("china"));
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dataQueue.push(new DataMessage("hello redis"));
		

		
		//手动 获取队列内容 示例
		
		manualRedisQueueService.push("MQueue1", "hello01");
		manualRedisQueueService.push("MQueue1", "hello02");
		

		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		manualRedisQueueService.push("MQueue1", "hello03");
		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		logger.info("MQueue1:"+manualRedisQueueService.pop("MQueue1"));
		return "success";
	}
}

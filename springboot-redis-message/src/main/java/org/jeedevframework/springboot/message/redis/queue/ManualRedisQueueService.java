package org.jeedevframework.springboot.message.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 基于redis 手动获取队列内容
 * */
@Component
public class ManualRedisQueueService {
	
	@Autowired
	private RedisTemplate redisTemplate;

	public synchronized void push(String queueName, String content) {
		redisTemplate.opsForList().rightPush(queueName, content);
	}

	public synchronized String pop(String queueName) {
		return (String) redisTemplate.opsForList().leftPop(queueName);
	}
}

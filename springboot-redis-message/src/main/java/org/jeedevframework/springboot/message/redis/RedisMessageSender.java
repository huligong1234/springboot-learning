package org.jeedevframework.springboot.message.redis;

import org.jeedevframework.springboot.message.IMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSender implements IMessageSender {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendMessage(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
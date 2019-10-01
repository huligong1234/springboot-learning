package org.jeedevframework.springboot.message;

import org.jeedevframework.springboot.message.redis.RedisMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheRefreshRedisProducer {

    private static String CACHE_CLEAR_TOPIC = "Topic:cacheRefresh";

    @Autowired
    private RedisMessageSender redisMessageSender;

    public void clearCache(){
        redisMessageSender.sendMessage(CACHE_CLEAR_TOPIC,"hello message");
    }

}
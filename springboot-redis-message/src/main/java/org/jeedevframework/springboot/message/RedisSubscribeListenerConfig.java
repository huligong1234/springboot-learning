package org.jeedevframework.springboot.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * ChannelTopic/PatternTopic
 * */
@Configuration
public class RedisSubscribeListenerConfig {

    @Autowired
    @Qualifier("cacheRefreshRedisReceiver")
    private CacheRefreshRedisReceiver cacheRefreshRedisReceiver;
    
    @Autowired
    @Qualifier("taskRedisReceiver")
    private TaskRedisReceiver taskRedisReceiver;
  
    @Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(cacheRefreshRedisReceiver, new ChannelTopic("Topic:cacheRefresh"));
		container.addMessageListener(taskRedisReceiver, new ChannelTopic("testChannel"));
		
		return container;
	}
}
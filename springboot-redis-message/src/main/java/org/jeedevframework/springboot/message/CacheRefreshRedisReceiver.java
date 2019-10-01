package org.jeedevframework.springboot.message;
import org.jeedevframework.springboot.message.redis.RedisMessageReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

@Service("cacheRefreshRedisReceiver")
public class CacheRefreshRedisReceiver extends RedisMessageReceiver {

    public CacheRefreshRedisReceiver() {
		super();
		this.setDelegate(this);
	}

    @Override
    public void receiveMessage(String message) {
        //TODO 这里是收到通道的消息之后执行的方法
        System.out.println("CacheRefreshRedisReceiver.receiveMessage:"+message);
    }


    //利用反射来创建监听到消息之后的执行方法
    /*@Bean//(name = "cacheRefreshRedisReceiver")
    MessageListenerAdapter listenerAdapter(CacheRefreshRedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, DEFAULT_LISTENER_METHOD);
    }*/
}
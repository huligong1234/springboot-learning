package org.jeedevframework.springboot.message.redis;

import org.jeedevframework.springboot.message.IMessageReceiver;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public abstract class RedisMessageReceiver extends MessageListenerAdapter implements IMessageReceiver {

	public RedisMessageReceiver() {
		super();
		this.setDefaultListenerMethod(DEFAULT_LISTENER_METHOD);
	}

}

package org.jeedevframework.springboot.message;
import org.jeedevframework.springboot.message.redis.RedisMessageReceiver;
import org.springframework.stereotype.Service;

@Service("taskRedisReceiver")
public class TaskRedisReceiver extends RedisMessageReceiver {

    public TaskRedisReceiver() {
		super();
		this.setDelegate(this);
	}


    @Override
    public void receiveMessage(String message) {
        System.out.println("TaskRedisReceiver.receiveMessage:"+message);
    }
}
package org.jeedevframework.springboot.message.redis.queue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class MyDataMessageConsumer extends BaseMessageConsumer<DataMessage> {
	
	private static MessageQueue<DataMessage> DATA_QUEUE = QueueManager.getQueue("DATA_QUEUE");
	
	private String queueConsumerToken = null;
	
    @PostConstruct
    public void postConstruct() {
        queueConsumerToken = QueueManager.register(this);
    }

    @PreDestroy
    public void preDestroy() {
        QueueManager.unregister(queueConsumerToken);
    }
    
	public MyDataMessageConsumer() {
        super(DATA_QUEUE, DataMessage.class, MyDataMessageConsumer.class.getSimpleName());
    }

	@Override
	protected void process(DataMessage message) {
		logger.info("MyDataMessageConsumer.process:"+message.getContent());
	}

}

package org.jeedevframework.springboot.message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 基于jdk BlockingQueue 实现队列示例
 *
 * */
@Component
public class BlockingQueueQueueService {

    Logger logger = LoggerFactory.getLogger(BlockingQueueQueueService.class);
    private final static Map<String,LinkedBlockingQueue<String>> BBLOCKING_QUEUE = new ConcurrentHashMap<>();

    public synchronized void put(String queueName, String value){
        if(StringUtils.isBlank(queueName) || StringUtils.isBlank(value)) {
            return;
        }

       if(!BBLOCKING_QUEUE.containsKey(queueName)) {
    	   BBLOCKING_QUEUE.put(queueName, new LinkedBlockingQueue<>());
       }

        try {
        	BBLOCKING_QUEUE.get(queueName).put(value);
        } catch (InterruptedException e) {
            logger.error("BlockingQueueQueueService.put,",e.getMessage());
        }
    }


    public synchronized String get(String queueName){
        if(StringUtils.isBlank(queueName)) {
            return null;
        }
        if(BBLOCKING_QUEUE.containsKey(queueName)) {
            return BBLOCKING_QUEUE.get(queueName).poll();
        }
        return null;
    }

    public static void main(String[] args) {
        BlockingQueueQueueService blockingQueueQueueService = new BlockingQueueQueueService();
        blockingQueueQueueService.put("d1","1111");
        blockingQueueQueueService.put("d1","2222");
        blockingQueueQueueService.put("d1","3333");
        blockingQueueQueueService.put("d1","4444");

        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
        System.out.println(blockingQueueQueueService.get("d1"));
    }
}

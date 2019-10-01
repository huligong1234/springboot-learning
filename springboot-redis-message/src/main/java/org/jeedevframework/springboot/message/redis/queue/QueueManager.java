package org.jeedevframework.springboot.message.redis.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jeedevframework.springboot.utils.IdGenUtils;

/**
 * MQ管理工具类
 *
 */
public class QueueManager {
    private static ExecutorService executor = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(10),
            new ThreadPoolExecutor.AbortPolicy());

    private static Map<String, BaseMessageConsumer> consumerMap = new HashMap<>();
    private static Map<String, List<BaseMessageConsumer>> queueConsumerMap = new HashMap<>();

    /**
     * 获取消息队列
     *
     * @param channel 队列名称
     * @param <T>     队列消息类型
     * @return
     */
    public static <T> MessageQueue<T> getQueue(String channel) {
        return new MessageQueue<>(channel);
    }

    /**
     * 注册消费者
     *
     * @param consumer
     * @return 消费者token，用于注销
     */
    public static String register(BaseMessageConsumer consumer) {
        executor.submit(consumer);

        String token = IdGenUtils.uuid();
        consumerMap.put(token, consumer);
        List<BaseMessageConsumer> consumers = queueConsumerMap.computeIfAbsent(consumer.getChannel(), k -> new ArrayList<>());
        consumers.add(consumer);

        return token;
    }

    /**
     * 注销消费者
     *
     * @param token 注册时获取的token
     */
    public static void unregister(String token) {
        BaseMessageConsumer consumer = consumerMap.get(token);
        if (consumer != null) {
            consumer.stop();
            List<BaseMessageConsumer> consumers = queueConsumerMap.get(consumer.getChannel());
            if (consumers != null) {
                consumers.remove(consumer);
            }
        }
        consumerMap.remove(token);
    }

    private QueueManager() {
    }
}

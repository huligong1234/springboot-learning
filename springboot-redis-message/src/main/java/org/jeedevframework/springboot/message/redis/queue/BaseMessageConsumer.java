package org.jeedevframework.springboot.message.redis.queue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.util.Assert;

import java.util.concurrent.CancellationException;

/**
 * 消息队列消费者
 */
public abstract class BaseMessageConsumer<T> implements Runnable {

    private static final String CONSUMER = "consumer: {}";

    @Autowired
    private RedisUtils redisUtils;

    protected Logger logger = LoggerFactory.getLogger(BaseMessageConsumer.class);
    /**
     * 监听的队列
     */
    private MessageQueue<T> queue;
    /**
     * 处理的类型
     */
    private Class<T> type;
    private String name;
    private boolean stopFlag = false;

    public BaseMessageConsumer(MessageQueue<T> queue, Class<T> type) {
        this(queue, type, null);
    }

    public BaseMessageConsumer(MessageQueue<T> queue, Class<T> type, String name) {
        Assert.notNull(queue, "queue can not be null");
        Assert.notNull(type, "type can not be null");

        this.queue = queue;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return queue.getChannel();
    }

    void stop() {
        this.stopFlag = true;
    }

    @Override
    public void run() {
        logger.debug("process start");
        logger.debug(CONSUMER, getName());
        logger.debug(" queue: {}", getChannel());
        while (!stopFlag) {
            try {
                T msg = RedisUtils.pop(type, new String[]{getChannel()});
                logger.debug(CONSUMER, getName());
                logger.debug("process message: {}", msg);
                if (msg != null) {
                    process(msg);
                }
            } catch (QueryTimeoutException e) {
                logger.error("get null", e);
                logger.debug(CONSUMER, getName());
            } catch (RedisSystemException e) {
                if (e.getCause() instanceof CancellationException
                        || StringUtils.endsWith(e.getMessage(), "Connection closed")) {
                    logger.warn("redis connection cancelled");
                } else {
                    logger.error("redis system exception: ", e);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.debug(CONSUMER, getName());
        logger.debug("process end");
    }

    /**
     * 业务处理方法
     *
     * @param message 待处理的消息
     */
    protected abstract void process(T message);
}

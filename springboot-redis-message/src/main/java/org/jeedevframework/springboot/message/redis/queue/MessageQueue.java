package org.jeedevframework.springboot.message.redis.queue;

import org.springframework.util.Assert;

/**
 * 消息队列
 *
 */
public class MessageQueue<T> {
    private static final String KEY_PREFIX = "QUEUE_";

    /**
     * 消息队列名称
     */
    private String channel;

    MessageQueue(String channel) {
        Assert.notNull(channel, "channel can not be null");

        this.channel = KEY_PREFIX + channel;
    }

    public void push(T msg) {
        RedisUtils.push(getChannel(), msg);
    }

    public Long size() {
        return RedisUtils.length(getChannel());
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "MessageQueue(" + getChannel() + ")";
    }
}

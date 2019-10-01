package org.jeedevframework.springboot.message.redis.queue;

import org.jeedevframework.springboot.utils.SerializerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Redis Hash和计数工具类
 */
@Component
public class RedisUtils {
    private static final int DEFAULT_BLOCK_TIME = 60;
    private static final String LOCK_KEY = "lock_";
    private static RedisTemplate redisTemplate;
    private static String redisPrefix;

    private RedisUtils() {
    }

    @Value("${custom.redis.prefix:jeedev_}")
    public void setRedisPrefix(String redisPrefix) {
        RedisUtils.redisPrefix = redisPrefix;
    }

    @Autowired
    @Qualifier("redisTemplate")
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * left push
     *
     * @param key
     * @param obj
     * @param <T>
     */
    public static <T> void push(String key, T obj) {
        key = redisPrefix + key;
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtils.serializeWithoutClass(obj);
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.lPush(bkey, bvalue);
            return true;
        });
    }

    /**
     * block right pop
     *
     * @param type
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T pop(Class<T> type, String[] keys) {
        final byte[][] bkeys = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            bkeys[i] = (redisPrefix + keys[i]).getBytes();
        }
        byte[] bvalue = (byte[]) redisTemplate.execute((RedisCallback<byte[]>) connection -> {
            List<byte[]> result = connection.bRPop(DEFAULT_BLOCK_TIME, bkeys);
            if (result != null && result.size() > 1) {
                return result.get(1);
            }
            return null;
        });
        return SerializerUtils.deserializeWithClass(bvalue, type);
    }

    /**
     * block right pop and left push
     *
     * @param sourceKey
     * @param destKey
     */
    public static void move(String sourceKey, String destKey) {
        sourceKey = redisPrefix + sourceKey;
        destKey = redisPrefix + destKey;
        final byte[] bskey = sourceKey.getBytes();
        final byte[] bdkey = destKey.getBytes();
        redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.bRPopLPush(0, bskey, bdkey));
    }

    /**
     * list length
     *
     * @param key
     * @return
     */
    public static Long length(String key) {
        key = redisPrefix + key;
        final byte[] bkey = key.getBytes();
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.lLen(bkey));
    }

    /**
     * 计数器
     *
     * @param key
     * @return
     */
    public static Long increase(String key) {
        key = redisPrefix + key;
        final byte[] bkey = key.getBytes();
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.incr(bkey));
    }

    /**
     * 获取简单分布式锁，不能防止有时间差的重复处理，主要用于防止重复数据插入
     *
     * @param lockKey
     * @return
     */
    public static Boolean fetchLock(String lockKey) {
        String key = redisPrefix + LOCK_KEY + lockKey;
        final byte[] bkey = key.getBytes();
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result = connection.setNX(bkey, bkey);
            if (result != null && result) {
                connection.expire(bkey, DEFAULT_BLOCK_TIME);
            }
            return result;
        });
    }

    /**
     * 获取乐观锁，可防止有时间差的重复处理
     *
     * @param lock
     * @return
     */
    public static Boolean fetchLock(Lock lock) {
        String key = redisPrefix + LOCK_KEY + lock.getKey();
        final byte[] bkey = key.getBytes();
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.watch(bkey);
            byte[] bvalue = connection.get(bkey);
            if (bvalue == null) {
                return false;
            }
            Long version = Long.valueOf(new String(bvalue));
            if (!Objects.equals(version, lock.getVersion())) {
                lock.setVersion(version);
                connection.unwatch();
                return false;
            }

            connection.multi();
            connection.incr(bkey);
            List<Object> result1 = connection.exec();
            if (result1.size() == 1) {
                lock.setVersion((Long) result1.get(0));
                return true;
            }

            bvalue = connection.get(bkey);
            if (bvalue != null) {
                version = Long.valueOf(new String(bvalue));
                lock.setVersion(version);
            }
            return false;
        });
    }

    /**
     * 初始化乐观锁
     *
     * @param lockKey
     * @return
     */
    public static Lock initLock(String lockKey) {
        String key = redisPrefix + LOCK_KEY + lockKey;
        final byte[] bkey = key.getBytes();
        Long version = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.watch(bkey);
            byte[] bversion = connection.get(bkey);
            if (bversion == null) {
                connection.multi();
                connection.incr(bkey);
                connection.exec();
                bversion = connection.get(bkey);
            }
            if (bversion == null) {
                return null;
            }
            return Long.valueOf(new String(bversion));
        });
        return new Lock(lockKey, version);
    }

    /**
     * 乐观锁对象，包含version
     */
    public static class Lock {
        private String key;
        private Long version;

        private Lock(String key, Long version) {
            this.key = key;
            this.version = version;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }
    }
}

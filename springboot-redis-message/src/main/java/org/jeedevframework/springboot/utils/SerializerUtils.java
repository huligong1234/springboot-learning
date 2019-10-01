package org.jeedevframework.springboot.utils;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 序列化工具，使用FST实现高效的Java对象序列化
 *
 */
public class SerializerUtils {
    private static Logger logger = LoggerFactory.getLogger(SerializerUtils.class);
    // FstConfiguration线程安全，出现性能瓶颈时，可考虑使用本地线程配置
    //    private static ThreadLocal<FSTConfiguration> conf = new ThreadLocal() {
    //        public FSTConfiguration initialValue() {
    //            return FSTConfiguration.createDefaultConfiguration();
    //        }
    //    };
    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    private SerializerUtils() {
    }

    /**
     * 序列化对象
     *
     * @param obj 需要序列化的对象
     * @return 序列化后的二进制数组
     */
    public static byte[] serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return conf.asByteArray(obj);
        } catch (Exception e) {
            logger.error("serialize: " + obj, e);
        }
        return null;
    }


    /**
     * 反序列化对象
     *
     * @param bytes 待反序列化二进制数组
     * @return 反序列化出来的对象
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return conf.asObject(bytes);
        } catch (Exception e) {
            logger.error("deserialize: " + new String(bytes), e);
        }
        return null;
    }

    /**
     * 序列化时去掉类型信息，获得的二进制数组更小，更高效
     *
     * @param obj 需要序列化的对象
     * @param <T> 序列化对象类型
     * @return 序列化后的二进制数组
     */
    public static <T> byte[] serializeWithoutClass(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FSTObjectOutput out = conf.getObjectOutput(baos);
            out.writeObject(obj, obj.getClass());
            out.flush();
            // DON'T out.close() when using factory method;
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("serializeWithoutClass: " + obj, e);
        }
        return null;
    }

    /**
     * 若序列化时去掉了类型信息，反序列化时必须传入原始类型
     *
     * @param bytes 待反序列化二进制数组
     * @param clazz 对象原始类型
     * @param <T>   对象类型泛型
     * @return 反序列化出来的对象
     */
    public static <T> T deserializeWithClass(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FSTObjectInput in = conf.getObjectInput(bais);
            T result = (T) in.readObject(clazz);
            // DON'T: in.close(); here prevents reuse and will result in an exception
            bais.close();
            return result;
        } catch (Exception e) {
            logger.error("deserializeWithClass " + clazz.getName() + " : " + new String(bytes), e);
        }
        return null;
    }
}

package org.jeedevframework.springboot.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理用户信息, 将用户信息放入到线程变量中,这样一次请求内部,所有地方都可以获取到用户信息。

 */
public class RequestContextProxy {
    //默认的放置用户名或者用户账号的key
    private static final String DEFAULT_USER_NAME_KEY = "request_username";

    public static transient ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    /**
     * 从 ThreadLocal中获取名值Map
     *
     * @return 名值Map
     */
    public static Map<String, String> getContextMap() {
        Map<String, String> map = threadLocal.get();
        return map;
    }

    /**
     * 向 ThreadLocal 设置名值Map
     * @param contextMap 名值Map
     */
    public static void setContextMap(Map<String, String> contextMap) {
        threadLocal.set(contextMap);
    }

    /**
     * 获取默认的用户名
     * @return String 用户名称(账号)
     */
    public static String getRequestUsername() {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            return null;
        }
        return contextMap.get(DEFAULT_USER_NAME_KEY);
    }

    /**
     * （获取键下的值.如果不存在，返回null；如果名值Map未初始化，也返回null） Get the value of key. Would
     * return null if context map hasn't been initialized.
     *
     * @param key 键
     * @return 键下的值
     */
    public static String get(String key) {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            return null;
        }
        return contextMap.get(key);
    }

    /**
     * （设置默认的DEFAULT_USER_NAME_KEY。如果Map之前为null，则会被初始化;
     *
     * @param value 值
     * @return 之前的值
     */
    public static String putRequestUsername(String value) {
       return put(DEFAULT_USER_NAME_KEY, value);
    }

    /**
     * （设置名值对。如果Map之前为null，则会被初始化） Put the key-value into the context map;
     *
     * @param key   键
     * @param value 值
     * @return 之前的值
     */
    public static String put(String key, String value) {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            contextMap = new HashMap();
            setContextMap(contextMap);
        }

        return contextMap.put(key, value);
    }

    /**
     * 请求完成后销毁threadLocal
     */
    public static void clear(){
        threadLocal.remove();
    }

}

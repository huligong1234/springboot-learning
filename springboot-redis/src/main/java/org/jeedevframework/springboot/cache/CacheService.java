package org.jeedevframework.springboot.cache;

public interface CacheService {

	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	public void setCache(String key,String value);
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public String getCache(String key);
	
	
	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	public Boolean deleteCache(String key);
}

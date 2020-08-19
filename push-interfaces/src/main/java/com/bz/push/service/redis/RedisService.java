package com.bz.push.service.redis;

import java.util.Map;

public interface RedisService {

	public void put(String key, Object value);
	
	public void putString(String key,String value);

	public void put(String key, int expiredTime, String value);

	public void delete(String key);

	public Object getObject(String key);

	public Map<String, Object> getList(final String pattern);
	
	public String getString(final String key);
}

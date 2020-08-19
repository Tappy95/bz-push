package com.bz.push.service.impl.redis;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bz.push.common.utils.StringUtil;
import com.bz.push.redis.CodisConfig;
import com.bz.push.redis.CodisPoolsUtil;
import com.bz.push.service.redis.RedisService;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Override
	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		if (!StringUtil.isEmpty(key) && value != null) {
			CodisPoolsUtil.setObject(key, value, CodisConfig.DEFAULT_EXPIRE);
			// TODO:考虑是否持久化到数据库
		}
	}

	@Override
	public void put(String key, int expiredTime, String value) {
		// TODO Auto-generated method stub
		if (StringUtil.notEmpty(key) && StringUtil.notEmpty(value)) {
			CodisPoolsUtil.setString(key, expiredTime, value);
			// TODO:考虑是否持久化到数据库
		}
	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		// 删除缓存会话
		CodisPoolsUtil.deleteKeyValue(key);
		// 删除数据库会话 TODO:考虑是否持久化到数据库
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		return CodisPoolsUtil.getObject(key);
	}

	@Override
	public Map<String, Object> getList(final String pattern) {
		if (StringUtil.isEmpty(pattern)) {
			return null;
		}
		Set<String> keys = CodisPoolsUtil.findKey(pattern);
		Map<String, Object> result = new TreeMap<>();
		for (String key : keys) {
			result.put(key, getObject(key));
		}

		return result;
	}
	
	public String getString(final String key) {
		if(StringUtils.isBlank(key)){
			return null;
		}
		return CodisPoolsUtil.getString(key);
	}

	@Override
	public void putString(String key, String value) {
		// TODO Auto-generated method stub
		if (!StringUtil.isEmpty(key) && value != null && !value.isEmpty()) {
			CodisPoolsUtil.setString(key, value);
			// TODO:考虑是否持久化到数据库
		}
	}
}

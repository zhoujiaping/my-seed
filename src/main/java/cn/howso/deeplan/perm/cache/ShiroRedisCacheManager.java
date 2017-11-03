package cn.howso.deeplan.perm.cache;

import java.util.Map;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class ShiroRedisCacheManager implements CacheManager{

	private Map<String,ShiroRedisCache> cacheMap;
	public void setCacheMap(Map<String, ShiroRedisCache> cacheMap) {
		this.cacheMap = cacheMap;
	}
	public Map<String, ShiroRedisCache> getCacheMap() {
		return cacheMap;
	}
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return (Cache<K, V>) cacheMap.get(name);
	}

}

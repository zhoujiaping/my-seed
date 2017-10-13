package cn.howso.deeplan.perm.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
public class RedisCacheManager implements CacheManager {
	
	
    private Map<String,RedisCache> cacheMap = new HashMap<>();
	
    public RedisCache getAuthenCache() {
        return cacheMap.get("authenCache");
    }
    
    public void setAuthenCache(RedisCache authenCache) {
        cacheMap.put("authenCache", authenCache);
    }
    
    public RedisCache getAuthorCache() {
        return cacheMap.get("authorCache");
    }
    public void setAuthorCache(RedisCache authorCache) {
        cacheMap.put("authorCache", authorCache);
    }
    @SuppressWarnings("unchecked")
    @Override
	public  RedisCache getCache(String name) throws CacheException {
	    return cacheMap.get(name);
	}
}

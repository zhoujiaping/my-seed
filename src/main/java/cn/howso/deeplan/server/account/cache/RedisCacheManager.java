package cn.howso.deeplan.server.account.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
public class RedisCacheManager implements CacheManager {
	
	
	private RedisCache authenCache;
	private RedisCache authorCache;
	
    public RedisCache getAuthenCache() {
        return authenCache;
    }
    
    public void setAuthenCache(RedisCache authenCache) {
        this.authenCache = authenCache;
    }
    
    public RedisCache getAuthorCache() {
        return authorCache;
    }
    
    public void setAuthorCache(RedisCache authorCache) {
        this.authorCache = authorCache;
    }
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if("authenCache".equals(name))
			return authenCache;
		else
			return authorCache;
	}

}

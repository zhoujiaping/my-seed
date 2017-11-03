package cn.howso.deeplan.perm.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class ShiroRedisCache implements Cache<Object,Object>{
	private String name;
	private RedisTemplate<String, Object> redisTemplate;
	private boolean usePrefix;
	public ShiroRedisCache(String name,RedisTemplate<String, Object> redisTemplate,boolean usePrefix){
		this.redisTemplate = redisTemplate;
		this.name = name;
		this.usePrefix = usePrefix;
	}
	private String toStringKey(Object key){
		if(usePrefix){
			return name+":"+key.toString();
		}else{
			return key.toString();
		}
	}
	@Override
	public Object get(Object key) throws CacheException {
		return redisTemplate.opsForValue().get(toStringKey(key));
	}

	@Override
	public Object put(Object key, Object value) throws CacheException {
		return redisTemplate.opsForValue().getAndSet(toStringKey(key), value);
	}

	@Override
	public Object remove(Object key) throws CacheException {
		String sk = toStringKey(key);
		Object value = redisTemplate.opsForValue().get(sk);
		redisTemplate.delete(sk);
		return value;
	}

	@Override
	public void clear() throws CacheException {
		Set<String> keys = redisTemplate.keys("*");
		redisTemplate.delete(keys);
	}

	@Override
	public int size() {
		Set<String> keys = redisTemplate.keys("*");
		return keys.size();
	}

	@Override
	public Set<Object> keys() {
		Set<?> keys = redisTemplate.keys("*");
		return (Set<Object>) keys;
	}

	@Override
	public Collection<Object> values() {
		Set<String> keys = redisTemplate.keys("*");
		return redisTemplate.opsForValue().multiGet(keys);
	}

	public String getName() {
		return name;
	}

}

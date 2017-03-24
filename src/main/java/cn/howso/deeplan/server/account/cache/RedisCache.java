package cn.howso.deeplan.server.account.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @ClassName RedisCache
 * @Description 
 * @author wzf
 * @Date 2017年2月20日 下午4:41:58
 * @version 1.0.0
 * @param <K>
 * @param <V>
 */
public class RedisCache<K,V> implements Cache<K, V> {
	
	private JedisPool jedisPool;
	private String keyPrefix="redis_cache";
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private Jedis getJedis() {
		return this.jedisPool.getResource();
	}
	@Override
	public void clear() throws CacheException {
	}
	private byte[] getByteKey(K key){
		if (key instanceof String) 
			return (this.keyPrefix+key).getBytes();
		else
			return SerializationUtils.serialize(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws CacheException {
		if(key == null) 
			return null;
		else{
			Jedis redis = this.getJedis();
			V v =null;
			try {
				v= (V)(SerializationUtils.deserialize(redis.get(this.getByteKey(key))));
				redis.expire(this.getByteKey(key), 1800);
			} finally{
				redis.close();
			}
			return v;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		Jedis redis = this.getJedis();
		Set<byte[]> ses = null;
		Set<K> sets = null;
		try {
			ses = redis.keys(this.keyPrefix.getBytes());
			sets = new HashSet<>();
			if(ses != null){
				for(byte[] tmp : ses){
					sets.add((K)(SerializationUtils.deserialize(tmp)));
				}
			}
		} finally{
			redis.close();
		}
				
		return sets;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		Jedis redis = this.getJedis();
		try {
			redis.set(this.getByteKey(key), SerializationUtils.serialize(value));
		} finally{
			redis.close();
		}
	
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		Jedis redis = this.getJedis();
		try {
			redis.del(this.getByteKey(key));
		} finally{
			redis.close();
		}
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<V> values() {
		return null;
	}
	

}

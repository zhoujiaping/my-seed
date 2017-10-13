package cn.howso.deeplan.perm.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * */
public class RedisCache implements Cache<Object,Object> {
	private JedisPool jedisPool;
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
	    //TODO
	    throw new RuntimeException("this method is not implemented.");
	}
	private byte[] tobytes(Object key){
        return SerializationUtils.serialize(key);
    }
	private Object frombytes(byte[] bytes){
	    return SerializationUtils.deserialize(bytes);
	}

	@Override
	public Object get(Object key) throws CacheException {
		if(key == null) 
			return null;
		else{
			Jedis redis = this.getJedis();
			Object v =null;
			try {
			    byte[] k = tobytes(key);
			    byte[] byteV = redis.get(k);
				v= frombytes(byteV);
				redis.expire(k, 1800);
			}finally{
				redis.close();
			}
			return v;
		}
	}

	@Override
	public Set<Object> keys() {
		Jedis redis = this.getJedis();
		Set<byte[]> bytekeys = null;
		Set<Object> keys = null;
		try {
		    bytekeys = redis.keys("*".getBytes());//这里不能用tobytes("*")因为该方法得到的bytes不仅仅是*的字节。
		    keys = new HashSet<>();
			if(bytekeys != null ){
				for(byte[] bytekey : bytekeys){
				    keys.add(frombytes(bytekey));
				}
			}
		} finally{
			redis.close();
		}
		return keys;
	}

	@Override
	public Object put(Object key, Object value) throws CacheException {
		Jedis redis = this.getJedis();
		try {
			redis.set(tobytes(key), tobytes(value));
		} finally{
			redis.close();
		}
		return value;
	}

	@Override
	public Object remove(Object key) throws CacheException {
		Jedis redis = this.getJedis();
		try {
			redis.del(tobytes(key));
		} finally{
			redis.close();
		}
		return null;
	}

	@Override
	public int size() {
	    Jedis redis = this.getJedis();
        Set<byte[]> bytekeys = null;
        try {
            bytekeys = redis.keys("*".getBytes());
            return bytekeys==null?0:bytekeys.size();
        } finally{
            redis.close();
        }
	}

	@Override
	public Collection<Object> values() {
	    //TODO
	    throw new RuntimeException("this method is not implemented.");
	}
	

}

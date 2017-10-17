package cn.howso.deeplan.perm.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import cn.howso.deeplan.util.ArrayUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * */
public class RedisCache implements Cache<Object,Object> {
	private JedisPool jedisPool;
	private String prefix;
	private byte[] prefixBytes;
	
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
        prefixBytes = prefix.getBytes();
    }
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
	private byte[] keytobytes(Object key){
	    if(key instanceof String){
	        return ArrayUtils.concat(prefixBytes, ((String) key).getBytes());
	    }
        byte[] bytes = SerializationUtils.serialize(key);
        return ArrayUtils.concat(prefixBytes, bytes);
    }
	private byte[] valuetobytes(Object key){
	    return SerializationUtils.serialize(key);
	}
	private Object keyfrombytes(byte[] bytes){
	    byte[] pre = new byte[prefixBytes.length];
	    System.arraycopy(bytes, 0, pre, 0, pre.length);
	    byte[] dest = new byte[bytes.length-prefixBytes.length];
	    System.arraycopy(bytes, prefixBytes.length, dest, 0, dest.length);
	    if(Objects.equals(new String(pre), prefix)){
	        return new String(dest);
	    }
	    return SerializationUtils.deserialize(bytes);
	}
	private Object valuefrombytes(byte[] bytes){
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
			    byte[] k = keytobytes(key);
			    byte[] byteV = redis.get(k);
				v= valuefrombytes(byteV);
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
		    bytekeys = redis.keys((prefix+"*").getBytes());//这里不能用tobytes("*")因为该方法得到的bytes不仅仅是*的字节。
		    keys = new HashSet<>();
			if(bytekeys != null ){
				for(byte[] bytekey : bytekeys){
				    keys.add(keyfrombytes(bytekey));
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
			redis.set(keytobytes(key), valuetobytes(value));
		} finally{
			redis.close();
		}
		return value;
	}

	@Override
	public Object remove(Object key) throws CacheException {
		Jedis redis = this.getJedis();
		try {
			redis.del(keytobytes(key));
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
            bytekeys = redis.keys((prefix+"*").getBytes());
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

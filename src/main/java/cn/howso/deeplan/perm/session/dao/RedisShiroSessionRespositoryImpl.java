package cn.howso.deeplan.perm.session.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
public class RedisShiroSessionRespositoryImpl implements MyShiroSessionRespository {
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
	private byte[] tobytes(Object key){
        return SerializationUtils.serialize(key);
    }
    private Object frombytes(byte[] bytes){
        return SerializationUtils.deserialize(bytes);
    }

	@Override
	public void delete(Serializable id) {
		if(id==null) return;
		Jedis jedis = this.getJedis();
		try {
			jedis.del(tobytes(id.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

	}

	@Override
	public Collection<Session> getAllSessions() {
		Jedis jedis = this.getJedis();
		Set<Session> sessions = new HashSet<Session>();  
		try {
		    Set<byte[]> keys = jedis.keys("*".getBytes());
			if(keys!=null && keys.size()>0){
			    List<byte[]> bytevalues = jedis.mget(keys.toArray(new byte[keys.size()][]));
				for(byte[] bytevalue:bytevalues){
					sessions.add((Session)(frombytes(bytevalue)));
				}
			}
		}finally {
			jedis.close();
		}
		return sessions;
	}

	@Override
	public void saveSession(Session session) {
		if(session==null) return;
		Jedis jedis = this.getJedis();
		try {
			byte[] key = tobytes(session.getId().toString());
			byte[] value = tobytes(session);
			jedis.set(key, value);
			jedis.expire(key, (int)(session.getTimeout()/1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	@Override
	public Session getSessioin(Serializable id) {
		if(id==null) return null;
		Jedis jedis = this.getJedis();
		Session session = null;
		try {
		    byte[] bytekey = tobytes(id.toString());
			byte[] bytevalue=jedis.get(bytekey);
			if(bytevalue==null || bytevalue.length==0) return session;
			session = ((Session) frombytes(bytevalue));
			jedis.expire(bytekey, (int)(session.getTimeout()/1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return session;
	}

}

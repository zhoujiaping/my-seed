package cn.howso.deeplan.perm.session.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.springframework.util.SerializationUtils;

import cn.howso.deeplan.util.ArrayUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Deprecated
public class RedisShiroSessionRespositoryImpl implements MyShiroSessionRespository {
	private JedisPool jedisPool;
	private String prefix;
	private static final String OBJECT_PREFIX="object-";
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getPrefix() {
        return prefix;
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
	/*private byte[] tobytes(Object key){
        return SerializationUtils.serialize(key);
    }
    private Object frombytes(byte[] bytes){
        return SerializationUtils.deserialize(bytes);
    }*/
	private byte[] keytobytes(Object key){
        if(key instanceof String){
            return ArrayUtils.concat(prefix.getBytes(), ((String) key).getBytes());
        }
        byte[] bytes = SerializationUtils.serialize(key);
        return ArrayUtils.concat((OBJECT_PREFIX+prefix).getBytes(), bytes);
    }
    private byte[] valuetobytes(Object key){
        return SerializationUtils.serialize(key);
    }
    private Object keyfrombytes(byte[] bytes){
        byte[] prefixBytes = prefix.getBytes();
        //if key is string
        if(Objects.equals(new String(bytes,0,prefixBytes.length), prefix)){
            byte[] dest = new byte[bytes.length-prefixBytes.length];
            return new String(dest);
        }
        //if key is not string
        byte[] dest = new byte[bytes.length-(prefixBytes.length+OBJECT_PREFIX.getBytes().length)];
        System.arraycopy(bytes, prefixBytes.length+OBJECT_PREFIX.getBytes().length, dest, 0, dest.length);
        return SerializationUtils.deserialize(dest);
    }
    private Object valuefrombytes(byte[] bytes){
        return SerializationUtils.deserialize(bytes);
    }

	@Override
	public void delete(Serializable id) {
		if(id==null) return;
		Jedis jedis = this.getJedis();
		try {
			jedis.del(keytobytes(id.toString()));
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
		    Set<byte[]> keys = jedis.keys((prefix+"*").getBytes());
			if(keys!=null && keys.size()>0){
			    List<byte[]> bytevalues = jedis.mget(keys.toArray(new byte[keys.size()][]));
				for(byte[] bytevalue:bytevalues){
					sessions.add((Session)(valuefrombytes(bytevalue)));
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
			byte[] key = keytobytes(session.getId().toString());
			byte[] value = valuetobytes(session);
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
		    byte[] bytekey = keytobytes(id.toString());
			byte[] bytevalue=jedis.get(bytekey);
			if(bytevalue==null || bytevalue.length==0) return session;
			session = ((Session) valuefrombytes(bytevalue));
			jedis.expire(bytekey, (int)(session.getTimeout()/1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return session;
	}
}

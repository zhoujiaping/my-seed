package cn.howso.deeplan.perm.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.shiro.session.Session;

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

	@Override
	public void delete(Serializable id) {
		if(id==null) return;
		Jedis jedis = this.getJedis();
		try {
			jedis.del(SerializationUtils.serialize(id));
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
			Set<byte[]> byteKeys=jedis.keys(SerializationUtils.serialize("*"));
			if(byteKeys!=null){
				for(byte[] key:byteKeys){
					sessions.add(((MySerializedSession) SerializationUtils.deserialize(key)).getSession());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			byte[] key = SerializationUtils.serialize(session.getId());
			byte[] value=SerializationUtils.serialize(new MySerializedSession(session));
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
			byte[] temSession=jedis.get(SerializationUtils.serialize(id));
			if(temSession==null) return session;
			session = ((MySerializedSession) SerializationUtils.deserialize(temSession)).getSession();
			jedis.expire(SerializationUtils.serialize(session.getId()), (int)(session.getTimeout()/1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return session;
	}


	static class MySerializedSession implements Serializable{
		private static final long serialVersionUID = 7763684293995775477L;
		private Session session;

		public MySerializedSession(Session session) {
			super();
			this.session = session;
		}

		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}
		
	}

}

package cn.howso.deeplan.perm.session.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSessionDao extends AbstractSessionDAO {
    private RedisTemplate<String, Object> redisTemplate;
    
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
    
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void update(Session session) throws UnknownSessionException {
        redisTemplate.opsForValue().set(session.getId().toString(), session);
    }

    @Override
    public void delete(Session session) {
        redisTemplate.delete(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
    	Set<String> keys = redisTemplate.keys("*");
    	Collection<?> sessions = redisTemplate.opsForValue().multiGet(keys);
    	return (Collection<Session>)sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable id=this.generateSessionId(session);
        this.assignSessionId(session, id);
        redisTemplate.opsForValue().set(id.toString(), session);
        return id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session) redisTemplate.opsForValue().get(sessionId.toString());
        return session;
    }

}

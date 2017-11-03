package cn.howso.deeplan.perm.session.dao;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

@Deprecated
public interface MyShiroSessionRespository {

	void delete(Serializable id);

	Collection<Session> getAllSessions();

	void saveSession(Session session);

	Session getSessioin(Serializable id);
	
}

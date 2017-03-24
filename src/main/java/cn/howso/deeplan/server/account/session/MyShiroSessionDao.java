package cn.howso.deeplan.server.account.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
public class MyShiroSessionDao extends AbstractSessionDAO {
    
	private MyShiroSessionRespository shiroSessionRespository;
	
    public MyShiroSessionRespository getShiroSessionRespository() {
        return shiroSessionRespository;
    }
    
    public void setShiroSessionRespository(MyShiroSessionRespository shiroSessionRespository) {
        this.shiroSessionRespository = shiroSessionRespository;
    }

	@Override
	public void delete(Session session) {
		if(session==null) return;
		Serializable id=session.getId();
		this.shiroSessionRespository.delete(id);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return this.shiroSessionRespository.getAllSessions();
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.shiroSessionRespository.saveSession(session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable id=this.generateSessionId(session);
		this.assignSessionId(session, id);
		this.shiroSessionRespository.saveSession(session);
		return id;
	}

	@Override
	protected Session doReadSession(Serializable id) {
		return this.shiroSessionRespository.getSessioin(id);
	}

}

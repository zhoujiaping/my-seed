package cn.howso.deeplan.test;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
/**
 * tomcat会扫描到继承了Endpoint的类，自动创建websocket服务，并不需要特殊的配置。
 * 但是要注意，servlet过滤器可能会拦截websocket，比如shiro，需要配置它不拦截。
 * 继承Endpoint方式有个缺点，当不需要的时候，没有简单的办法使其失效。
 * 更好的方式是用注解方式，不需要websocket的时候，将注解注释掉就可以了。
 * @author Administrator
 *
 */
/*public class TestEndpoint extends Endpoint{
	private Map<String,Session> sessionMap = new HashMap<>();
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		sessionMap.put(session.getId(), session);
		session.addMessageHandler(new TestMessageHanlerWhole());
		System.out.println("onOpen");
	}
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		sessionMap.remove(session.getId());
        // NO-OP by default
    }
	@Override
    public void onError(Session session, Throwable throwable) {
        // NO-OP by default
		throwable.printStackTrace();
    }

}*/

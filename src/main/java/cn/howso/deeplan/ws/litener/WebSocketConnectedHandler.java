package cn.howso.deeplan.ws.litener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;
@Component
public class WebSocketConnectedHandler implements ApplicationListener<SessionConnectedEvent>{
    
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompSubProtocolHandler handler = (StompSubProtocolHandler) event.getSource();
        System.out.println(event);
    }

}

package cn.howso.deeplan.ws.litener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
@Component
public class WebSocketConnectHandler implements ApplicationListener<SessionConnectEvent>{
    
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        
    }

}

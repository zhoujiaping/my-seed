package cn.howso.deeplan.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //允许连接的域,只能以http或https开头
        String[] allowsOrigins = {"*"};
        
       //WebIM WebSocket通道
        registry.addHandler(helloWebSocketHandler(),"/ws/hello")
        .addHandler(hiWebSocketHandler(),"/ws/hi")
        .setAllowedOrigins(allowsOrigins)
        .addInterceptors(myInterceptor());
        registry.addHandler(helloWebSocketHandler(), "/sockjs/hello")
        .setAllowedOrigins(allowsOrigins)
        .addInterceptors(myInterceptor()).withSockJS();
    }
    @Bean
    public HelloWebSocketHandler helloWebSocketHandler() {
        return new HelloWebSocketHandler();
    }
    @Bean
    public HiWebSocketHandler hiWebSocketHandler(){
        return new HiWebSocketHandler();
    }
    @Bean
    public HandshakeInterceptor[] myInterceptor(){
        return new HandshakeInterceptor[]{new WebSocketHandshakeInterceptor()};
    }
}
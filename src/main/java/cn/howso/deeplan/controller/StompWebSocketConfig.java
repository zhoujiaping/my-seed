package cn.howso.deeplan.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler;
/*public class StompWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    public StompWebSocketConfig(){
        System.out.println("init StompWebSocketConfig");
        System.out.println(this);
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/greeting");
        //registry.addEndpoint("/ws/greeting").withSockJS();
    }
}
*/
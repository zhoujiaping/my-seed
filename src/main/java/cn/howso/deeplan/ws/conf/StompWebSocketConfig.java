package cn.howso.deeplan.ws.conf;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

import cn.howso.deeplan.util.LogUtil;
import cn.howso.deeplan.ws.interceptor.HttpSessionIdHandshakeInterceptor;
import cn.howso.deeplan.ws.interceptor.InboundChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private final static Logger logger = LogUtil.getLogger();
    public StompWebSocketConfig(){
        logger.info("StmopWebSocketConfig已创建");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket.io/").addInterceptors(httpSessionIdHandshakeInterceptor()).setAllowedOrigins("*");
        //.withSockJS().setInterceptors(httpSessionIdHandshakeInterceptor()); 
        //registry.addEndpoint("/ws/greeting").withSockJS();
    }
    /** 
     * 消息传输参数配置 
     */  
    @Override  
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {  
        registry.setMessageSizeLimit(8192) //设置消息字节数大小  
                .setSendBufferSizeLimit(8192)//设置消息缓存大小  
                .setSendTimeLimit(10000); //设置消息发送时间限制毫秒  
    }  
    /** 
     * 输入通道参数设置 
     */  
    @Override  
    public void configureClientInboundChannel(ChannelRegistration registration) {  
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数  
                .maxPoolSize(8)//最大线程数  
                .keepAliveSeconds(60);//线程活动时间  
        registration.setInterceptors(inboundChannelInterceptor());  
    }  
    private ChannelInterceptor inboundChannelInterceptor() {
        return new InboundChannelInterceptor();  
    }
    private HandshakeInterceptor httpSessionIdHandshakeInterceptor() {
        return new HttpSessionIdHandshakeInterceptor();  
    }
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        
    }
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        
    }
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }
}

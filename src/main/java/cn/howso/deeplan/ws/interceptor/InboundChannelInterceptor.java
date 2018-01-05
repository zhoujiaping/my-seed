package cn.howso.deeplan.ws.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class InboundChannelInterceptor implements ChannelInterceptor{
    @Override  
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {  
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);  
        // ignore non-STOMP messages like heartbeat messages  
        if(sha.getCommand() == null) {  
            return;  
        }  
        //这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key  
        //String sessionId = sha.getSessionAttributes().get(Constants.SESSIONID).toString();  
        //String accountId = sha.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();  
        //判断客户端的连接状态  
        switch(sha.getCommand()) {  
            case CONNECT:  
                //connect(sessionId,accountId);  
                break;  
            case CONNECTED:  
                break;  
            case DISCONNECT:  
                //disconnect(sessionId,accountId,sha);  
                break;  
            default:  
                break;  
        }  
    }  
  
    //连接成功  
    private void connect(String sessionId,String accountId){  
        //logger.debug(" STOMP Connect [sessionId: " + sessionId + "]");  
        //存放至ehcache  
        //String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;  
        //若在多个浏览器登录，直接覆盖保存  
        //CacheManager.put(cacheName ,cacheName+accountId,sessionId);  
    }  
  
    //断开连接  
    private void disconnect(String sessionId,String accountId, StompHeaderAccessor sha){  
        //logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");  
        //sha.getSessionAttributes().remove(Constants.SESSIONID);  
        //sha.getSessionAttributes().remove(Constants.SKEY_ACCOUNT_ID);  
        //ehcache移除  
        //String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;  
        //if (CacheManager.containsKey(cacheName,cacheName+accountId) ){  
        //    CacheManager.remove(cacheName ,cacheName+accountId);  
        //}  
        System.out.println("disconnect");
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
    }  
}

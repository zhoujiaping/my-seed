package cn.howso.deeplan.ws.controller;

import javax.annotation.PostConstruct;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class GreetingController { 
    @PostConstruct
    public void init(){
        System.out.println("init GreetingController");
    }
    @MessageMapping("/hello") 
    //@SendTo("/topic/hi") //如果配置了@SendTo,则会将内容发送给配置的目标地址，否则，发送给默认的目标地址(配置的第一个broker)。
    //@SendTo({"/topic/hi","/queue/hello"})
    public Greeting greeting(HelloMessage message) throws Exception { 
        Thread.sleep(3000); // simulated delay 
        Greeting g = new Greeting();
        g.setGreeting("Hello, " + message.getName() + "!");
        return g;
    }
}
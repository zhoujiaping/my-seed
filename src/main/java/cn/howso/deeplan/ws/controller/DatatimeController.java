package cn.howso.deeplan.ws.controller;

import javax.annotation.Resource;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
/**
 * 轮询数据时间，如果数据时间有更新，推送数据时间到客户端
 * */
@Controller 
public class DatatimeController { 
    @Resource
    private SimpMessagingTemplate template;
   /* @Resource
    private BatchService batchService;
    private Batch prevBatch;
    @PostConstruct
    public void init(){
        prevBatch = batchService.query();
        polldatatime();
    }
    @MessageMapping("/lastdatatime") 
    //@SendTo("/topic/hi") //如果配置了@SendTo,则会将内容发送给配置的目标地址，否则，发送给默认的目标地址(配置的第一个broker)。
    //@SendTo({"/topic/hi","/queue/hello"})
    public String lastdatatime() throws Exception { 
        return prevBatch.getDatetime();
    } 
    public void polldatatime(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Batch batch = batchService.query();
                if(!Objects.equals(prevBatch.getDatetime(), batch.getDatetime())){
                    template.convertAndSend("/topic/lastdatatime", batch.getDatetime());
                    prevBatch = batch;
                }
            }
        }, 10*1000, 10*1000);
    }*/
}
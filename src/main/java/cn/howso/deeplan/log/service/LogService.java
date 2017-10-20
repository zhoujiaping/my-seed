package cn.howso.deeplan.log.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import cn.howso.deeplan.log.LogQueue;
import cn.howso.deeplan.log.mapper.LogMapper;
import cn.howso.deeplan.log.model.Log;
import cn.howso.deeplan.util.LogUtil;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;
    private List<Log> logCache;
    private static Logger logger = LogUtil.getLogger();

    /**
     * @Description 日志消费者，一个线程。从队列中取出日志。 综合考虑性能和实时性。如果日志很多，则线程不会睡眠，直到日志很少。而且保证日志很少时，也会在之后一小段时间内将日志持久化。
     *              如果考虑停服务造成的日志丢失问题，可以将日志队列放到另一个服务进程。
     */
    @PostConstruct
    public void init() {
        logCache = new ArrayList<>();
        new Thread(() -> {
            while (true) {
                try {
                    boolean needsleep = false;
                    for (int i = 0; i < 500; i++) {// 批量保存500条日志
                        Log log = LogQueue.QUEUE.poll();
                        if (log == null) {
                            needsleep = true;
                            break;
                        }
                        logCache.add(log);
                    }
                    if(!logCache.isEmpty()){
                        logMapper.batchInsertSelective(logCache);
                        logCache.clear();
                    }
                    if (needsleep) {// 如果日志不够500条，保存后，让线程睡眠10秒钟
                        Thread.sleep(10 * 1000);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }).start();
    }
}

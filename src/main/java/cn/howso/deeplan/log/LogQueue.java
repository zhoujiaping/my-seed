package cn.howso.deeplan.log;

import java.util.concurrent.ConcurrentLinkedQueue;

import cn.howso.deeplan.log.model.Log;
/**
 * 
 * @Description 使用ConcurrentLinkedQueue，不需要锁，具有性能优势。
 * @author zhoujiaping
 * @Date 2017年10月20日 下午3:41:28
 * @version 1.0.0
 */
public class LogQueue {
    public static final ConcurrentLinkedQueue<Log> QUEUE = new ConcurrentLinkedQueue<>();
}

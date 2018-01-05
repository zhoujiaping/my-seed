package cn.howso.deeplan.ws.litener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;
@Component
public class BrokerAvailabilityHandler implements ApplicationListener<BrokerAvailabilityEvent>{

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        
    }

}

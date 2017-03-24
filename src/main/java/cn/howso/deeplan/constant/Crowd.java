package cn.howso.deeplan.constant;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Crowd{
    private final String PREFIX = "crowd.";
    @Resource DynamicProperties dynamicProperties;
    private String get(String key){
        return dynamicProperties.get(PREFIX+key);
    }
    public float getRatio() {
        return Float.parseFloat(get("ratio"));
    }
}

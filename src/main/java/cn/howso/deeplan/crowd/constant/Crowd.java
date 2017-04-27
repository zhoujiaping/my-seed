package cn.howso.deeplan.crowd.constant;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.howso.deeplan.constant.DynamicProperties;

@Component
public class Crowd{
    private final String PREFIX = "crowd.";
    @Resource DynamicProperties dynamicProperties;
    private String get(String key){
        return dynamicProperties.get(PREFIX+key);
    }
    public float ratio() {
        return Float.parseFloat(get("ratio"));
    }
}

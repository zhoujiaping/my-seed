package cn.howso.deeplan.cache;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.howso.deeplan.util.ReflectHelper;

public class MyGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer{
    @PostConstruct
    public void init() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        ObjectMapper mapper = (ObjectMapper) ReflectHelper.getValueByFieldName(this, "mapper");
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

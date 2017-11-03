package seed.jedis;

import java.util.Set;

import org.springframework.util.SerializationUtils;

import cn.howso.deeplan.util.ReflectHelper;
import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379,1000);
        try{
            jedis.auth("123456");
            jedis.select(1);
            Object o = jedis.get("ea682328-7d9a-4f04-a4d3-4773697c4a3e");
            System.out.println(o);
            //{"@class":"org.apache.shiro.session.mgt.SimpleSession","id":"ea682328-7d9a-4f04-a4d3-4773697c4a3e","startTimestamp":["java.util.Date",1509694313687],"stopTimestamp":null,"lastAccessTime":["java.util.Date",1509694313687],"timeout":1800000,"expired":false,"host":"0:0:0:0:0:0:0:1","attributes":null,"valid":true,"attributeKeys":["java.util.Collections$EmptySet",[]]}
            Set<byte[]> keys = jedis.keys("*".getBytes());
            keys.forEach(key->{
                byte[] value = jedis.get(key);
                Object v = fromBytes(value);
                try {
                    Object perms = ReflectHelper.getValueByFieldName(v, "stringPermissions");
                    ReflectHelper.setValueByFieldName(v, "stringPermissions", null);
                    System.out.println(perms);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jedis.set(key, toBytes(v));
                //jedis.del(key);
            });
        }finally{
            jedis.close();
        }
    }
    private static byte[] toBytes(Object key){
        return SerializationUtils.serialize(key);
    }
    private static Object fromBytes(byte[] bytes){
        return SerializationUtils.deserialize(bytes);
    }
}

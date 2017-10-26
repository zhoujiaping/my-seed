package seed.jedis;

import java.util.Set;

import org.springframework.util.SerializationUtils;

import cn.howso.deeplan.util.ReflectHelper;
import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.0.116",6379,1000);
        try{
            jedis.auth("123456");
            jedis.select(2);
            Set<byte[]> keys = jedis.keys("*admin*".getBytes());
            keys.forEach(key->{
                System.out.println(key);
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

package seed.jedis;

import java.util.Set;

import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379,10);
        try{
            jedis.auth("123456");
            jedis.set("hello".getBytes(), toBytes("world"));
            Set<byte[]> keys = jedis.keys("*".getBytes());
            Set<String> strkeys = jedis.keys("hel*");
            System.out.println(keys);
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

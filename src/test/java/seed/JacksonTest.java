package seed;

import java.io.IOException;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class JacksonTest {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Fuck f1 = new Fuck(1,"f1");
        ObjectMapper mapper = new ObjectMapper(); 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GenericJackson2JsonRedisSerializer ser = new GenericJackson2JsonRedisSerializer(mapper);
        byte[] b = ser.serialize(f1);
        Fuck f2 = ser.deserialize(b, Fuck.class);
        System.out.println(f2);
        
    }

}
class Fuck{
    private Integer id;
    public Fuck(){
        
    }
    public Fuck(Integer id,String name){
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return "ff";
    }
}
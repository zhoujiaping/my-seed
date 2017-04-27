package seed.date;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class DateTest {
    @Test
    public void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("date",new Date(System.currentTimeMillis()));
        System.out.println(JSON.toJSONString(map));
        int i=1;
        Integer j=1;
        System.out.println(i==j);
    }
}

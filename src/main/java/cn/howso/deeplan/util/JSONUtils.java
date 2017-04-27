package cn.howso.deeplan.util;

import java.util.HashMap;
import java.util.Map;

public class JSONUtils {
    public static final String toJSONString(Object obj){
        return com.alibaba.fastjson.JSON.toJSONString(obj);
    }
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("hello", "world");
        String str = toJSONString(map);
        System.out.println(str);
    }
}

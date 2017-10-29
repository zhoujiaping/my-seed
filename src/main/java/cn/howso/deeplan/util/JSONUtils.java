package cn.howso.deeplan.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JSONUtils {
    public static final String toJSONString(Object obj){
        return JSON.toJSONString(obj);
    }
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("hello", "world");
        String str = toJSONString(map);
        System.out.println(str);
    }
}

package cn.howso.deeplan.util;

import java.util.Base64;

public class Base64Util {
    public static byte[] decode(String content){
        return Base64.getDecoder().decode(content);
    }
    public static byte[] encodeToBytes(byte[] bytes){
        return Base64.getEncoder().encode(bytes);
    }
    public static String encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
}
